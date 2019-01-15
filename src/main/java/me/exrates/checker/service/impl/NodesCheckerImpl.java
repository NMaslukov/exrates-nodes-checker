package me.exrates.checker.service.impl;

import me.exrates.checker.service.ExplorerBlocksCheckerService;
import me.exrates.checker.service.NodesChecker;
import me.exrates.checker.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.Map;

@Service
public class NodesCheckerImpl implements NodesChecker {

    final
    Client client;

    private final Map<String, ExplorerBlocksCheckerService> bitcoinBlocksCheckerServiceMap;

    @Value("${url.stock}")
    private String stockUrl;

    @Autowired
    public NodesCheckerImpl(Map<String, ExplorerBlocksCheckerService> bitcoinBlocksCheckerServiceMap, Client client) {
        this.bitcoinBlocksCheckerServiceMap = bitcoinBlocksCheckerServiceMap;
        this.client = client;
    }

    @Override
    @Scheduled(fixedRate = 50000)
    public void checkNodes() throws TelegramApiException {
        int workingNodesCount = 0;
        for (Map.Entry<String, ExplorerBlocksCheckerService> elem : bitcoinBlocksCheckerServiceMap.entrySet()) {
            String key = elem.getKey();
            String ticker = key.substring(0, key.indexOf("BlockChecker"));
            try {
                Response response = client.target(stockUrl + "/nodes/getBlocksCount?ticker=" + ticker.toUpperCase()).request(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("AUTH_TOKEN", "MOCK_TOKEN")
                        .get();
                if(response.getStatus() != 404) return;

                Long blocksFromNode = response.readEntity(Long.class);

                long blocksFromExplorer = elem.getValue().getExplorerBlocksAmount();
                if(blocksFromNode == null){
                    sendMessage("The " + ticker + " node is not responding!");
                } else
                if(blocksFromExplorer != blocksFromNode){
                    sendMessage("The " + ticker + " node is not synchronized!\nBlocks count in explorer: " + blocksFromExplorer + "\nBlocks count in node:" + blocksFromNode);
                } else {
                    workingNodesCount++;
                }
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Exception with " + key);
            }
        }
        sendMessage(workingNodesCount + " nodes works fine");
    }

    private void sendMessage(String message) throws TelegramApiException {
        TelegramBot bot = new TelegramBot();
        bot.getBotToken();
        bot.execute(new SendMessage(-339818592L, message));

    }
}
