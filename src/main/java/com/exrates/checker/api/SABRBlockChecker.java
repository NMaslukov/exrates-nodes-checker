package com.exrates.checker.api;

import com.exrates.checker.BitcoinBlocksCheckerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.cert.X509Certificate;
import java.util.*;

@Service("sabrBlockChecker")
@PropertySource("classpath:/coins_api_endpoints.properties")
public class SABRBlockChecker implements BitcoinBlocksCheckerService {

    @Autowired
    Client client;

    @Value("#{sabr.blocks.endpoint")
    private String endpoint;

    @Override
    public long getExplorerBlocksAmount() {
        String h = client.target("https://bchain.info/SABR/").request().get().readEntity(String.class);
        String var = "var blocks = ";
        String replace = h.replace("'", "\"");
        String cutted = replace.substring(replace.indexOf(var) + var.length());
        String blocks = cutted.substring(0, cutted.indexOf(";"));
        JSONObject jsonObject = new JSONObject(blocks);
        Set<Map.Entry<String, Object>> entries = jsonObject.toMap().entrySet();

        List<Long> list = new LinkedList<>();
        entries.forEach(a -> {
            list.add(Long.valueOf(a.getKey()));
        });

        return list.stream().mapToLong(a -> a).max().getAsLong();
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

        String h = client.target("https://bchain.info/SABR/").request().get().readEntity(String.class);
        String var = "var blocks = ";
        String replace = h.replace("'", "\"");
        String cutted = replace.substring(replace.indexOf(var) + var.length());
        String blocks = cutted.substring(0, cutted.indexOf(";"));
        JSONObject jsonObject = new JSONObject(blocks);
        Set<Map.Entry<String, Object>> entries = jsonObject.toMap().entrySet();

        List<Long> list = new LinkedList<>();
        entries.forEach(a -> {
            System.out.println(a);
            list.add(Long.valueOf(a.getKey()));
        });

        System.out.println(list.stream().mapToLong(a -> a).max().getAsLong());
    }
}

