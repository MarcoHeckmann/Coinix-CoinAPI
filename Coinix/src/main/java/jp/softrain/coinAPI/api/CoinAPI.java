package jp.softrain.coinAPI.api;

import jp.softrain.coinAPI.Main;

import java.math.BigDecimal;
import java.util.UUID;

public class CoinAPI {

        private static Main instance;
        public CoinAPI(Main instance){
            this.instance = instance;
        }

        public static boolean isRegistered(UUID uuid){
            return instance.getDatabaseManager().isRegistered(uuid);
        }

        public void initializePlayer(UUID uuid){
            instance.getDatabaseManager().initializePlayer(uuid);
        }

        public static BigDecimal getBalance(UUID uuid){
            return instance.getDatabaseManager().getMoney(uuid);
        }

        public static void addCoins(UUID uuid, BigDecimal amount){
            instance.getDatabaseManager().addMoney(uuid, amount);
        }

        public static void removeCoins(UUID uuid, BigDecimal amount){
            instance.getDatabaseManager().removeMoney(uuid, amount);
        }

        public static void setBalance(UUID uuid, BigDecimal amount){
            instance.getDatabaseManager().setMoney(uuid, amount);
        }
        public static void resetBalance(UUID uuid){
            instance.getDatabaseManager().resetMoney(uuid);
        }
    }

