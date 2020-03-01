package com.example.demo.service;

import com.example.demo.config.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManagerService {
    private final TelegramBot bot;

    void needBeer() {
        bot.sendMessage("Need Beer");
    }

    void needIngredient() {
        bot.sendMessage("Need Ingredient");
    }
}
