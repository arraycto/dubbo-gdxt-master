package com.iqilu.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * HTTPClient连接池
 *
 * @author zhangyicheng
 * @date 2020/05/20
 */
@SuppressWarnings("deprecation")
@Slf4j
public class HttpClientPool {

    /**
     * 池化管理
     */
    private static PoolingHttpClientConnectionManager poolConnManager = null;
    private static CloseableHttpClient httpClient;
    /**
     * 请求器配置
     */
    private static RequestConfig requestConfig;

    static {
        log.info("=====   HttpClent连接池初始化    =====");
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());

            // 配置同时支持HTTP和HTTPS协议
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslsf).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            // 设置最大连接数
            poolConnManager.setMaxTotal(200);
            // 设置路由
            poolConnManager.setDefaultMaxPerRoute(50);
            // 设置默认超时限制初始化requestConfig
            int socketTimeout = 60000;
            int connectTimeout = 60000;
            int connectionRequestTimeout = 60000;
            requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
            // 初始化HttpClient
            httpClient = getConnection();
            log.info("===== HttpClent连接池初始化结束  =====");
            log.info("---------------------------------");
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建HttpClient对象
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static CloseableHttpClient getConnection() {

        // 连接池管理
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
                // 设置请求配置
                .setDefaultRequestConfig(requestConfig)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                .build();

        return httpClient;
    }

    /**
     * HttpGet请求方法
     *
     * @author zhangyicheng
     * @date 2020/05/20
     */
    public static String httpGet(String url) {

        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        String result = "";
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
///			log.info("数据集结果" + result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
