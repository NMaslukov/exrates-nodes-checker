package me.exrates.checker.api.btc;

import me.exrates.checker.service.ExplorerBlocksCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.cert.X509Certificate;

@Service

public class NSRBlockChecker implements ExplorerBlocksCheckerService {

    @Autowired
    Client client;

    @Value("${nsr.blocks.endpoint}")
    private String endpoint;

    @Override
    public long getExplorerBlocksAmount() {
        String bestHtml = "<td class=\"height text-right\"><a href=\"/block/";

        String html = client.target(endpoint).request().get().readEntity(String.class);
        String substring = html.substring(html.indexOf(bestHtml) + bestHtml.length());
        return Long.valueOf(substring.substring(0, substring.indexOf("\">")));
    }

    public static void main(String[] args) throws Exception{
        SSLContext sslcontext = SSLContext.getInstance("TLS");

        sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) {}
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }}, new java.security.SecureRandom());

        Client client = ClientBuilder.newBuilder()
                .sslContext(sslcontext)
                .hostnameVerifier((s1, s2) -> true)
                .build();

        String bestHtml = "<td class=\"height text-right\"><a href=\"/block/";

        String html = client.target("https://explorer.nubits.com/").request().get().readEntity(String.class);
        String substring = html.substring(html.indexOf(bestHtml) + bestHtml.length());
        System.out.println(Long.valueOf(substring.substring(0, substring.indexOf("\">"))));
    }
}