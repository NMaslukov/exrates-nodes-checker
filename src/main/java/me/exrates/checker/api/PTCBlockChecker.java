package me.exrates.checker.api;

import me.exrates.checker.APIExplorer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;

@Service
@PropertySource("classpath:/coins_api_endpoints.properties")
public class PTCBlockChecker extends APIExplorer {
        public PTCBlockChecker(@Value("#{ptc.blocks.endpoint}") String endpoint, Client client) {
        super(endpoint, client);
    }
}
