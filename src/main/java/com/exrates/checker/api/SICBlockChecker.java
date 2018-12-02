package com.exrates.checker.api;

import com.exrates.checker.APIExplorer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service("SICBlockChecker")
@PropertySource("classpath:/coins_api_endpoints.properties")
public class SICBlockChecker extends APIExplorer {
        public SICBlockChecker(@Value("#{sic.blocks.endpoint}") String endpoint, Client client) {
        super(endpoint, client);
    }
}
