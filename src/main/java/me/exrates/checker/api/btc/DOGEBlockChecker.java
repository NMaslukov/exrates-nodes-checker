package me.exrates.checker.api.btc;

import me.exrates.checker.service.ExplorerBlocksCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service

public class DOGEBlockChecker implements ExplorerBlocksCheckerService {

    @Autowired
    Client client;

    @Value("${doge.blocks.endpoint}")
    private String endpoint;

    @Override
    public long getExplorerBlocksAmount() {
        String bestHtml = "<td><a href=\"/block/";

        String html = client.target(endpoint).request().get().readEntity(String.class);
        String substring = html.substring(html.indexOf(bestHtml) + bestHtml.length());
        return Long.valueOf(substring.substring(0, substring.indexOf("\">")));
    }

}