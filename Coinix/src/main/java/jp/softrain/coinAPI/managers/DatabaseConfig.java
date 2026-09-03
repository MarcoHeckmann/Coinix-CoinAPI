package jp.softrain.coinAPI.managers;

import jp.softrain.coinAPI.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class DatabaseConfig {


    private Main instance;
    private File file;
    private FileConfiguration config;

    public DatabaseConfig(Main instance){
        this.instance = instance;
    }
    public void setup(){
        file = new File(instance.getDataFolder(), "database.yml");

        if (!file.exists()){
            instance.saveResource("database.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
