package com.lcc.util.sdk.webservice;


import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author :    zhangjun
 * @version :   1.0
 * @date :      Created in  2020/11/11 15:57:38
 */
public class HttpClientUtil {

    private static final Logger Log = LoggerFactory.getLogger(HttpClientUtil.class);

    public static OkHttpClient simpleClient() {
        return new OkHttpClient.Builder()
                //连接、读取、写入的超时时长
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                //缓存目录、缓存大小
//                .cache(new Cache(new File(""), 500 * 1024 * 1024))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        String url = request.url().toString();
                        Log.info("intercept: proceed start: url" + url + ", at " + System.currentTimeMillis());
                        //真正请求,阻塞操作
                        Response response = chain.proceed(request);
                        ResponseBody body = response.body();
                        Log.info("intercept: proceed end: url" + url + ", at " + System.currentTimeMillis());
                        return response;
                    }
                })
                .build();
    }

}
