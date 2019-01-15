package me.exrates.checker;

import me.exrates.checker.service.ExplorerBlocksCheckerService;
import me.exrates.checker.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"me.exrates.checker"})
@EnableScheduling
public class Application {


    @Autowired
    Client client;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}