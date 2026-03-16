package com.vendor.rat.network;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.*;

/**
 * HttpClient 单元测试
 */
public class HttpClientTest {

    private MockWebServer server;
    private HttpClient httpClient;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        httpClient = new HttpClient(
            server.url("/").toString(), "test-device-id");
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testPostRequest_success() throws Exception {
        server.enqueue(new MockResponse()
            .setBody("{\"code\":200,\"message\":\"ok\"}")
            .setResponseCode(200));

        final boolean[] success = {false};
        httpClient.post("/api/test", "{\"key\":\"value\"}", new HttpCallback() {
            @Override
            public void onSuccess(String response) {
                success[0] = true;
                assertTrue(response.contains("200"));
            }

            @Override
            public void onFailure(Exception e) {
                fail("Should not fail");
            }
        });

        // 等待异步完成
        Thread.sleep(1000);
    }

    @Test
    public void testGetRequest_success() throws Exception {
        server.enqueue(new MockResponse()
            .setBody("{\"data\":\"test\"}")
            .setResponseCode(200));

        final boolean[] success = {false};
        httpClient.get("/api/data", new HttpCallback() {
            @Override
            public void onSuccess(String response) {
                success[0] = true;
            }

            @Override
            public void onFailure(Exception e) {
                fail("Should not fail");
            }
        });

        Thread.sleep(1000);
    }
}
