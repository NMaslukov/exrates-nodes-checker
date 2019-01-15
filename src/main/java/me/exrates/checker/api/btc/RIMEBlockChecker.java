package me.exrates.checker.api.btc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service
public class RIMEBlockChecker extends APIExplorer {
    public RIMEBlockChecker(@Value("${rime.blocks.endpoint}") String endpoint, Client client) {
        super(endpoint, client);
    }
}
