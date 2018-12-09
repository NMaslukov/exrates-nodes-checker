package me.exrates.checker.api;

import me.exrates.checker.APIExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service("qrkBlockChecker")
@PropertySource("classpath:/coins_api_endpoints.properties")
public class QRKBlockChecker extends APIExplorer {

    @Autowired
    Client client;

    public QRKBlockChecker(@Value("#{qrk.blocks.endpoint}") String endpoint, Client client) {
        super(endpoint, client);
    }
}