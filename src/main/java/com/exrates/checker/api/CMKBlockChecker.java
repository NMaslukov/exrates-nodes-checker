package com.exrates.checker.api;

import com.exrates.checker.APIExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service("cmkBlockChecker")
@PropertySource("classpath:/coins_api_endpoints.properties")
public class CMKBlockChecker extends APIExplorer {

    @Autowired
    Client client;

    public CMKBlockChecker(@Value("#{cmk.blocks.endpoint}") String endpoint, Client client) {
        super(endpoint, client);
    }
}