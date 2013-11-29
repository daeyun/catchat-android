package com.daeyunshin.catchat.android.networking;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by daeyun on 11/28/13.
 */
public class SSLSocketContext {
    public static SSLContext getContext() throws Exception {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                    }
                }};

        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, trustAllCerts, null);
        return context;
    }
}
