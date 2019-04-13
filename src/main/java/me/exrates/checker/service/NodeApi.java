package me.exrates.checker.service;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

public interface NodeApi {
    Response getBlocksCount(String coinName);

    List<String> getListOfCoins() throws IOException;
}
