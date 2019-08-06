package com.sri.googleimagesearch.util;

import android.net.Uri;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClientFacade {

    private static HttpClientFacade instance;

    private OkHttpClient okHttpClient;

    private HttpClientFacade() {
        okHttpClient = new OkHttpClient();
    }

    public static HttpClientFacade getInstance() {
        if(instance == null) {
            instance = new HttpClientFacade();
        }
        return instance;
    }

    public String makeGetRequest(final String url, final Map<String, String> params) throws URISyntaxException, IOException {
        final Uri.Builder builder = Uri.parse(url).buildUpon();
        for(Map.Entry<String, String > entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), URLDecoder.decode(entry.getValue(), "UTF-8"));
        }
        System.out.println("URL : " + builder.toString());

        final Request request = new Request.Builder().url(builder.toString()).build();
        final Response response = okHttpClient.newCall(request).execute();

        return response.body().string();

    }

}
