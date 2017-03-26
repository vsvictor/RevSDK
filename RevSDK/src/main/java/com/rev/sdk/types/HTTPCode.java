package com.rev.sdk.types;

import com.rev.sdk.R;
import com.rev.sdk.RevApplication;

/*
 * ************************************************************************
 *
 *
 * NUU:BIT CONFIDENTIAL
 * [2013] - [2017] NUU:BIT, INC.
 * All Rights Reserved.
 * NOTICE: All information contained herein is, and remains
 * the property of NUU:BIT, INC. and its suppliers,
 * if any. The intellectual and technical concepts contained
 * herein are proprietary to NUU:BIT, INC.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from NUU:BIT, INC.
 *
 * Victor D. Djurlyak, 2017
 *
 * /
 */

public enum HTTPCode {
    CONTINUE(100, R.string.continu),
    SWITCHING_PROTOCOL(101, R.string.switching_protocols),

    OK(200, R.string.ok),
    CREATED(201, R.string.created),
    ACCEPTED(200, R.string.accepted),
    NON_AUUTHORITATION_INFORMATION(203, R.string.non_authoritative_information),
    NO_CONTENT(204, R.string.no_content),
    RESET_CONTENT(205, R.string.reset_content),

    MULTIPLE_CHOICES(300, R.string.multiple_choices),
    MOVED_PERMANENTLY(301, R.string.moved_permanently),
    FOUND(302, R.string.found),
    SEE_OTHER(303, R.string.see_other),
    USE_PROXY(305, R.string.use_proxy),
    TEMPORARY_REDIRECT(307, R.string.temporary_redirect),

    BAD_REQUEST(400, R.string.bad_request),
    PAYMENT_REQUIRED(402, R.string.payment_required),
    FORBIDDEN(403, R.string.forbidden),
    NOT_FOUND(404, R.string.not_found),
    METHOD_NOT_ALLOWED(405, R.string.method_not_allowed),
    NOT_ACCEPTABLE(406, R.string.not_acceptable),
    REQUEST_TIMEOUT(408, R.string.request_timeout),
    CONFLICT(409, R.string.conflict),
    GONE(410, R.string.gone),
    LENGTH_REQUIRED(411, R.string.length_required),
    PAYLOAD_TO_LARGE(413, R.string.payload_to_large),
    URI_TO_LONG(414, R.string.uri_too_long),
    UNSUPPORTED_MEDIA_TYPE(415, R.string.unsupported_media_type),
    EXPECTION_FAILED(417, R.string.expectation_failed),
    UPGRADE_REQUIRED(426, R.string.upgrade_required),

    INTERNAL_SERVER_ERROR(500, R.string.internal_server_error),
    NOT_IMPLEMENTED(501, R.string.not_implemented),
    BAD_GATEWAY(502, R.string.bad_gateway),
    SERVICE_UNAVAILABLE(503, R.string.service_unavailable),
    GATEWAY_TIMEOUT(504, R.string.gateway_timeout),
    HTTP_VERSION_NOT_SUPPORTED(505, R.string.http_version_not_supported),
    UNDEFINED(10000, R.string.undefined);

    private int code;
    private String message;

    private HTTPCode(int code, int id_http) {
        this.code = code;
        this.message = RevApplication.getInstance().getApplicationContext().getResources().getString(id_http);
    }

    public static HTTPCode create(int code) {
        switch (code) {
            case 100:
                return CONTINUE;
            case 101:
                return SWITCHING_PROTOCOL;
            case 200:
                return OK;
            case 201:
                return CREATED;
            case 202:
                return ACCEPTED;
            case 203:
                return NON_AUUTHORITATION_INFORMATION;
            case 204:
                return NO_CONTENT;
            case 205:
                return RESET_CONTENT;
            case 300:
                return CONTINUE;
            case 301:
                return MOVED_PERMANENTLY;
            case 302:
                return FOUND;
            case 303:
                return SEE_OTHER;
            case 305:
                return USE_PROXY;
            case 307:
                return TEMPORARY_REDIRECT;
            case 400:
                return BAD_REQUEST;
            case 402:
                return PAYMENT_REQUIRED;
            case 403:
                return FORBIDDEN;
            case 404:
                return NOT_FOUND;
            case 405:
                return METHOD_NOT_ALLOWED;
            case 406:
                return NOT_ACCEPTABLE;
            case 408:
                return REQUEST_TIMEOUT;
            case 409:
                return CONFLICT;
            case 410:
                return GONE;
            case 411:
                return LENGTH_REQUIRED;
            case 413:
                return PAYLOAD_TO_LARGE;
            case 414:
                return URI_TO_LONG;
            case 415:
                return UNSUPPORTED_MEDIA_TYPE;
            case 417:
                return EXPECTION_FAILED;
            case 426:
                return UPGRADE_REQUIRED;
            case 500:
                return INTERNAL_SERVER_ERROR;
            case 501:
                return NOT_IMPLEMENTED;
            case 502:
                return BAD_GATEWAY;
            case 503:
                return SERVICE_UNAVAILABLE;
            case 504:
                return GATEWAY_TIMEOUT;
            case 505:
                return HTTP_VERSION_NOT_SUPPORTED;
            default:
                return UNDEFINED;
        }
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Type getType() {
        if (code < 200) return Type.INFORMATIONAL;
        else if (code >= 200 && code < 300) return Type.SUCCESSFULL;
        else if (code >= 300 && code < 400) return Type.REDIRECTION;
        else if (code >= 400 && code < 500) return Type.CLIENT_ERROR;
        else if (code >= 500 && code < 600) return Type.SERVER_ERROR;
        else return Type.UNDEFINED;
    }

    public static enum Type {INFORMATIONAL, SUCCESSFULL, REDIRECTION, CLIENT_ERROR, SERVER_ERROR, UNDEFINED}

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Code: ");
        builder.append(this.code);
        builder.append(" Message: ");
        builder.append(this.getMessage());
        return builder.toString();
    }
}
