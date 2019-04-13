package me.exrates.checker.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public interface NodesChecker {
    @Scheduled(fixedRate = 50000)
    void sendNodeStatusReport() throws IOException, TelegramApiException;
    long getChatId();
}
