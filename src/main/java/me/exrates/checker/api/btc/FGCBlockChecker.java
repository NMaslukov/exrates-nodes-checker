package me.exrates.checker.api.btc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service

public class FGCBlockChecker extends APIExplorer {

    public FGCBlockChecker(@Value("${fgc.blocks.endpoint}") String endpoint, Client client) {
        super(endpoint, client);
    }

}
