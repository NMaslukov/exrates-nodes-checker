package me.exrates.checker.api.btc;

import me.exrates.checker.service.ExplorerBlocksCheckerService;

import javax.ws.rs.client.Client;

public abstract class APIExplorer implements ExplorerBlocksCheckerService {

    private final String endpoint;
    private final Client client;

    public APIExplorer(String endpoint, Client client) {
        this.endpoint = endpoint;
        this.client = client;
    }

    @Override
    public long getExplorerBlocksAmount() {
        try {
            return Long.valueOf(client.target(endpoint).request().get().readEntity(String.class));
        } catch (Exception e){
            System.out.println(this + " was failed");
            System.out.println(e);
            return 0;
        }
    }
}
