package me.exrates.checker.api;

import me.exrates.checker.BitcoinBlocksCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service("hsrBlockChecker")
@PropertySource("classpath:/coins_api_endpoints.properties")
public class HSRBlockChecker implements BitcoinBlocksCheckerService {

    @Autowired
    Client client;

    @Value("#{hsr.blocks.endpoint")
    private String endpoint;

    @Override
    public long getExplorerBlocksAmount() {
        String bestHtml = "<p id=\"best\" class=\"hidden\">";

        String html = client.target(endpoint).request().get().readEntity(String.class);
        String substring = html.substring(html.indexOf(bestHtml) + bestHtml.length());
        return Long.valueOf(substring.substring(0, substring.indexOf("</p>")));
    }

}