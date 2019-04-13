package me.exrates.checker.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public interface NodesChecker {
    void checkNodes() throws TelegramApiException;

    @Scheduled(fixedRate = 50000)
    void checkAllNodeForWorking() throws IOException, TelegramApiException;

    @Scheduled(fixedRate = 50000)
    void checkAllNodes() throws IOException, TelegramApiException;
}
