package org.example;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class Main {

    private static final String BOT_TOKEN = "6527811249:AAFrGIs6vd6GAinucLsyzJesiGtzPtXP--0";
    public static void main(String[] args) {
        // Регистрация нового бота
        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(BOT_TOKEN, new MedicineBot(BOT_TOKEN));
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}