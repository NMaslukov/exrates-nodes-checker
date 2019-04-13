package me.exrates.checker.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.exrates.checker.service.NodeApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Service
public class NodeApiImpl implements NodeApi {
    private final Client client;
    @Value("${stock.url}")
    private String stockUrl;
    @Value("${stock.token}")
    private String stockToken;

    public NodeApiImpl(Client client) {
        this.client = client;
    }

    @Override
    public Response getBlocksCount(String coinName) {
        return client.target(stockUrl + "/nodes/getBlocksCount?ticker=" + coinName).request(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("AUTH_TOKEN", stockToken)
                .get();
    }

    @Override
    public List<String> getListOfCoins() throws IOException {
        Response response = client.target(stockUrl + "/nodes/listOfCoins").request(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("AUTH_TOKEN", stockToken)
                .get();
        System.out.println("status = " + response.getStatus());
        String listOfServicesString = response.readEntity(String.class);
        return new ObjectMapper().readValue(listOfServicesString, new TypeReference<List<String>>(){});
    }
}
