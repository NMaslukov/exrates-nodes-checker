package me.exrates.checker.service;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface NodesChecker {
    void checkNodes() throws TelegramApiException;
}
