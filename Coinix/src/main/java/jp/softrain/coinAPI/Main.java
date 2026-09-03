package jp.softrain.coinAPI;

import jp.softrain.coinAPI.api.CoinAPI;
import jp.softrain.coinAPI.commands.CoinsCommand;
import jp.softrain.coinAPI.database.DatabaseManager;
import jp.softrain.coinAPI.listeners.JoinListener;
import jp.softrain.coinAPI.managers.DatabaseConfig;
import jp.softrain.coinAPI.managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private Main instance;
    private DatabaseConfig databaseConfig;
    private DatabaseManager databaseManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        instance = this;

        databaseConfig = new DatabaseConfig(this);
        databaseConfig.setup();
        databaseManager = new DatabaseManager(this);
        messageManager = new MessageManager(this);
        messageManager.setup();

        new CoinAPI(this); // Initialisiert die statische API-Instanz für andere Plugins

        if(instance.getDatabaseManager().getConnection() != null){
            Bukkit.getConsoleSender().sendMessage("§aDatabase Connected §b[✅]");
        }
        getCommand("coins").setExecutor(new CoinsCommand(this));

        Bukkit.getPluginManager().registerEvents(new JoinListener(this),this);
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public Main getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}