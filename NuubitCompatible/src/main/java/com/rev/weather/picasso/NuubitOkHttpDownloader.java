package com.rev.weather.picasso;

import android.content.Context;
import android.net.Uri;
import android.os.StatFs;

import com.nuubit.sdk.NuubitConstants;
import com.nuubit.sdk.NuubitSDK;
import com.nuubit.sdk.types.HTTPCode;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Created by victor on 01.04.17.
 */

public class NuubitOkHttpDownloader implements Downloader {
    private static final String PICASSO_CACHE = "rev-picasso-cache";
    private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private static File defaultCacheDir(Context context) {
        File cache = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
        if (!cache.exists()) {
            cache.mkdirs();
        }
        return cache;
    }

    private static long calculateDiskCacheSize(File dir) {
        long size = MIN_DISK_CACHE_SIZE;
        try {
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }
        return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
    }

    public static Cache createDefaultCache(Context context) {
        File dir = defaultCacheDir(context);
        return new Cache(dir, calculateDiskCacheSize(dir));
    }

    private static OkHttpClient createOkHttpClient(File cacheDir, long maxSize) {
        return NuubitSDK.OkHttpCreate(NuubitConstants.DEFAULT_TIMEOUT_SEC, false, false);
    }

    private final Call.Factory client;
    private final Cache cache;

    public NuubitOkHttpDownloader(Context context) {
        this(defaultCacheDir(context));
    }

    public NuubitOkHttpDownloader(File cacheDir) {
        this(cacheDir, calculateDiskCacheSize(cacheDir));
    }

    public NuubitOkHttpDownloader(final Context context, final long maxSize) {
        this(defaultCacheDir(context), maxSize);
    }

    public NuubitOkHttpDownloader(File cacheDir, long maxSize) {
        this(createOkHttpClient(cacheDir, maxSize));
    }

    public NuubitOkHttpDownloader(OkHttpClient client) {
        this.client = client;
        this.cache = client.cache();
    }

    public NuubitOkHttpDownloader(Call.Factory client) {
        this.client = client;
        this.cache = null;
    }

    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException {
        CacheControl cacheControl = null;
        if (networkPolicy != 0) {
            if (NetworkPolicy.isOfflineOnly(networkPolicy)) {
                cacheControl = CacheControl.FORCE_CACHE;
            } else {
                CacheControl.Builder builder = new CacheControl.Builder();
                if (!NetworkPolicy.shouldReadFromDiskCache(networkPolicy)) {
                    builder.noCache();
                }
                if (!NetworkPolicy.shouldWriteToDiskCache(networkPolicy)) {
                    builder.noStore();
                }
                cacheControl = builder.build();
            }
        }


        Request.Builder builder = new Request.Builder().url(uri.toString());
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }
        okhttp3.Response response = client.newCall(builder.build()).execute();
        HTTPCode responseCode = HTTPCode.create(response.code());
        while (responseCode.getType() == HTTPCode.Type.REDIRECTION) {
            String sURL = response.header("location");
            if (sURL == null) break;
            builder.url(sURL);
            response = client.newCall(builder.build()).execute();
        }
        if (responseCode.getType() != HTTPCode.Type.SUCCESSFULL) {
            response.body().close();
            throw new ResponseException(responseCode.getCode() + " " + responseCode.getMessage(), networkPolicy,
                    responseCode.getCode());
        }


        boolean fromCache = response.cacheResponse() != null;
        ResponseBody responseBody = response.body();
        return new Response(responseBody.byteStream(), fromCache, responseBody.contentLength());
    }

    @Override
    public void shutdown() {
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException ignored) {
            }
        }
    }
}
