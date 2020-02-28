package com.example.demo.service;

import com.example.demo.config.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerService {
    private final TelegramBot bot;

    void setNeedBeer(boolean needBeer) {
        bot.sendMessage("needBeer");
    }

    void setNeedIngredient(boolean needIngredient) {
        bot.sendMessage("needIngredient");
    }
}
