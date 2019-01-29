package me.exrates.checker.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class NodesCheckerImpl implements NodesChecker {

    private final Client client;

    private final Map<String, ExplorerBlocksCheckerService> tickerBlocksCheckerServiceMap = new HashMap<>();

    @Value("${stock.url}")
    private String stockUrl;

    @Value("${stock.token}")
    private String stockToken;

    private StringBuilder builder = new StringBuilder();

    @Autowired
    public NodesCheckerImpl(Map<String, ExplorerBlocksCheckerService> bitcoinBlocksCheckerServiceMap, Client client) {
        this.client = client;

        for (Map.Entry<String, ExplorerBlocksCheckerService> elem : bitcoinBlocksCheckerServiceMap.entrySet()) {
            String key = elem.getKey();
            String ticker = key.substring(0, key.indexOf("BlockChecker"));
            tickerBlocksCheckerServiceMap.put(ticker, elem.getValue());
        }
    }

    @Override
    @Scheduled(fixedRate = 50000)
    public void checkNodes() throws TelegramApiException {
//        int workingNodesCount = 0;
//        for (Map.Entry<String, ExplorerBlocksCheckerService> elem : bitcoinBlocksCheckerServiceMap.entrySet()) {
//            String key = elem.getKey();
//            String ticker = key.substring(0, key.indexOf("BlockChecker"));
//            try {
//                Response response = client.target(stockUrl + "/nodes/getBlocksCount?ticker=" + ticker.toUpperCase()).request(MediaType.APPLICATION_JSON_VALUE)
//                        .accept(MediaType.APPLICATION_JSON_VALUE)
//                        .header("AUTH_TOKEN",stockToken)
//                        .get();
//
//
//                String reponseValue = response.readEntity(String.class);
//                System.out.println(reponseValue);
//                if(response.getStatus() == 404) continue;
//
//                Long blocksFromNode = Long.valueOf(reponseValue);
//
//                long blocksFromExplorer = elem.getValue().getExplorerBlocksAmount();
//                if(blocksFromNode == null){
//                    sendMessage("The " + ticker + " node is not responding!");
//                } else
//                if(blocksFromExplorer != blocksFromNode){
//                    sendMessage("The " + ticker + " node is not synchronized!\nBlocks count in explorer: " + blocksFromExplorer + "\nBlocks count in node:" + blocksFromNode);
//                } else {
//                    workingNodesCount++;
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//                System.out.println("Exception with " + key);
//            }
//        }
//        sendMessage(workingNodesCount + " nodes works fine");
    }

    private void sendMessage(String message) throws TelegramApiException {
        TelegramBot bot = new TelegramBot();
        bot.getBotToken();
        bot.execute(new SendMessage(-339818592L, message));

    }

    @Override
    @Scheduled(fixedRate = 50000)
    public void checkAllNodes() throws IOException, TelegramApiException {
        builder = new StringBuilder();
        Integer workingNodesCount = 0;
        List<String> listOfWorkingNodes = new LinkedList<>();

        List<String> listOfCoins = getListOfCoins();
        for (String coin : listOfCoins) {
            ExplorerBlocksCheckerService service = tickerBlocksCheckerServiceMap.get(coin);
            if(service != null){
                workingNodesCount = checkThroughExplorer(workingNodesCount, listOfWorkingNodes, coin, service);
            } else {
                try {
                    Response response = client.target(stockUrl + "/nodes/getLastBlockTime?ticker=" + coin.toUpperCase()).request(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON_VALUE)
                            .header("AUTH_TOKEN", stockToken)
                            .get();
                    Date lastBlock = new Date(response.readEntity(Long.class));
                    if (lastBlock.before(Date.from(LocalDateTime.now().minusHours(2).toInstant(ZoneOffset.UTC)))) {
                        builder.append(coin).append(" not synchronized\n");
                    }
                } catch (Exception e){
                    builder.append(coin).append(" node not responding\n");
                }
            }
        }
        builder.append(workingNodesCount).append(" nodes works fine, names:\n ").append(String.join(", ", listOfWorkingNodes));
        sendMessage(builder.toString());
    }

    private List<String> getListOfCoins() throws IOException {
        String listOfServicesString = client.target(stockUrl + "/nodes/listOfCoins").request(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .header("AUTH_TOKEN", stockToken)
                .get().readEntity(String.class);
        return new ObjectMapper().readValue(listOfServicesString, new TypeReference<List<String>>(){});
    }

    private Integer checkThroughExplorer(Integer workingNodesCount, List<String> listOfWorkingNodes, String coin, ExplorerBlocksCheckerService service) {
        try {
            Response response = client.target(stockUrl + "/nodes/getBlocksCount?ticker=" + coin.toUpperCase()).request(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .header("AUTH_TOKEN",stockToken)
                    .get();


            String reponseValue = response.readEntity(String.class);
            System.out.println(reponseValue);
            if(response.getStatus() == 404) return workingNodesCount;

            Long blocksFromNode = Long.valueOf(reponseValue);

            long blocksFromExplorer = service.getExplorerBlocksAmount();
            if(blocksFromNode == null){
                builder.append("The " + coin + " node is not responding!\n");
            } else
            if(blocksFromExplorer != blocksFromNode){
                builder.append("The " + coin + " node is not synchronized!\nBlocks count in explorer: " + blocksFromExplorer + "\nBlocks count in node:" + blocksFromNode + "\n");
            } else {
                workingNodesCount++;
                listOfWorkingNodes.add(coin);
                builder.append(coin + " works fine\n");
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception with " + service.toString());
        }
        return workingNodesCount;
    }
}
