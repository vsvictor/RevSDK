package com.nuubit.sdk;

import android.content.Intent;
import android.test.ServiceTestCase;
import com.nuubit.sdk.services.Statist;
import com.nuubit.sdk.statistic.Statistic;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StatService extends ServiceTestCase<Statist> {
    private final static String TAG = StatService.class.getName();



    public StatService() {
        super(Statist.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    @Test
    public void startStat() {
        String st = "{\"app_name\":\"Android Demo Application\",\"sdk_key\":\"5f0cbda0-108c-434d-9ebf-d4ecd88e6d6c\",\"sdk_version\":\"1\",\"version\":\"1.0\",\"IP\":\"192.168.0.1\",\"HITS\":0,\"start_ts\":1234567891000,\"end_ts\":1234567991000,\"LEVEL\":\"debug\",\"carrier\":{\"country_code\":\"UA\",\"device_id\":\"867982021877361\",\"mcc\":\"255\",\"mnc\":\"03\",\"net_operator\":\"DJUICE\",\"network_type\":\"_\",\"RSSI\":\"-10000\",\"rssi_avg\":\"-10000\",\"rssi_best\":\"-10000\",\"signal_type\":\"WiFi\",\"sim_operator\":\"DJUICE\",\"tower_cell_id_l\":\"_\",\"tower_cell_id_s\":\"_\"},\"device\":{\"batt_cap\":76.0,\"batt_status\":\"Charging\",\"batt_tech\":\"Li-ion\",\"batt_temp\":\"290\",\"batt_volt\":\"4073\",\"brand\":\"google\",\"cpu\":\"arm64-v8a\",\"cpu_cores\":\"8\",\"cpu_freq\":\"1555200\",\"cpu_number\":\"8\",\"cpu_sub\":\"0\",\"device\":\"Nexus 6P\",\"hight\":\"2392\",\"iccid\":\"8938003991653939132\",\"imei\":\"867982021877361\",\"imsi\":\"255030894020913\",\"manufacture\":\"Huawei\",\"meis\":\"867982021877361\",\"os\":\"Android 7.1.2\",\"phone_number\":\"+380989420913\",\"radio_serial\":\"angler-03.81\",\"serial_number\":\"CVH7N16411000010\",\"uuid\":\"95e6f6c1-818d-376e-b64a-cffbadd3c346\",\"width\":\"1440\",\"os_name\":\"Android\",\"os_version\":\"7.1.2\",\"model\":\"Nexus 6P\"},\"log_events\":[],\"location\":{\"direction\":0.0,\"latitude\":48.486486486486484,\"longitude\":35.122806372799396,\"speed\":0.0},\"network\":{\"cellular_ip_external\":\"_\",\"cellular_ip_internal\":\"_\",\"dns1\":\"8.8.8.8\",\"dns2\":\"8.8.4.4\",\"ip_reassemblies\":\"0\",\"ip_total_bytes_in\":\"0\",\"ip_total_bytes_out\":\"0\",\"ip_total_packets_in\":\"0\",\"ip_total_packets_out\":\"0\",\"rtt\":\"0\",\"tcp_bytes_in\":\"0\",\"tcp_bytes_out\":\"_\",\"tcp_retransmits\":\"_\",\"transport_protocol\":\"_\",\"udp_bytes_in\":\"_\",\"udp_bytes_out\":\"_\",\"wifi_dhcp\":\"_\",\"wifi_extip\":\"_\",\"wifi_gw\":\"_\",\"wifi_ip\":\"_\",\"wifi_mask\":\"_\"},\"requests\":[{\"conn_id\":-1,\"cont_encoding\":\"ISO-8859-1\",\"cont_type\":\"text/html\",\"start_ts\":1498222170553,\"sent_ts\":1498222170848,\"end_ts\":1498222171128,\"first_byte_ts\":1498222171128,\"keepalive_status\":1,\"local_cache_status\":\"\",\"method\":\"POST\",\"network\":\"NETWORK\",\"protocol\":\"https\",\"received_bytes\":116,\"sent_bytes\":2480,\"status_code\":200,\"success_status\":1,\"transport_protocol\":\"standard\",\"url\":\"https://stats-api.revsw.net/v1/stats/apps\",\"destination\":\"rev_edge\",\"x-rev-cache\":\"_\",\"domain\":\"stats-api.revsw.net\",\"edge_transport\":\"standard\"},{\"conn_id\":-1,\"cont_encoding\":\"ISO-8859-1\",\"cont_type\":\"text/html\",\"start_ts\":1498222171473,\"sent_ts\":1498222171475,\"end_ts\":1498222171538,\"first_byte_ts\":1498222171538,\"keepalive_status\":1,\"local_cache_status\":\"public, max-age\\u003d3\",\"method\":\"GET\",\"network\":\"NETWORK\",\"protocol\":\"https\",\"received_bytes\":812,\"sent_bytes\":24,\"status_code\":200,\"success_status\":1,\"transport_protocol\":\"standard\",\"url\":\"https://sdk-config-api.revapm.net/v1/sdk/config/5f0cbda0-108c-434d-9ebf-d4ecd88e6d6c\",\"destination\":\"rev_edge\",\"x-rev-cache\":\"HIT\",\"domain\":\"sdk-config-api.revapm.net\",\"edge_transport\":\"standard\"}],\"wifi\":{\"mac\":\"02:00:00:00:00:00\",\"ssid\":\"02:00:00:00:00:00\",\"wifi_enc\":\"02:00:00:00:00:00\",\"wifi_freq\":\"02:00:00:00:00:00\",\"wifi_rssi\":\"02:00:00:00:00:00\",\"wifi_rssibest\":\"02:00:00:00:00:00\",\"wifi_sig\":\"02:00:00:00:00:00\",\"wifi_speed\":\"02:00:00:00:00:00\"}}";
        Intent intent = new Intent(getSystemContext(), Statist.class);
        intent.putExtra(NuubitConstants.TIMEOUT, 10);
        intent.putExtra(NuubitConstants.STATISTIC, "http://httpbin.org/status/400");
        intent.putExtra("stat",st);
        getSystemContext().startService(intent);
    }
}