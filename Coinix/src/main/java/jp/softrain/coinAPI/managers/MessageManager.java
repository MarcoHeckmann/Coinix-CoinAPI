package jp.softrain.coinAPI.managers;

import jp.softrain.coinAPI.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

public class MessageManager {

    private File file;
    private FileConfiguration config;
    private String PREFIX;
    private Main instance;
    public MessageManager(Main instance){
        this.instance = instance;
    }

    public void setup(){
        file = new File(instance.getDataFolder(), "messages.yml");

        if(!file.exists()){
            instance.saveResource("messages.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        PREFIX = config.getString("prefix");
    }

    public String get(String path){
        return get(path, Map.of());
    }


    public String get(String path, Map<String, String> replacements){
        if (!config.contains(path)){
            return "§cNachricht nicht gefunden.";
        }

        String message = config.getString(path, "§cNachricht nicht gefunden: §e" + path);
        message = message.replace("%prefix%", PREFIX);

        for (Map.Entry<String, String> msg : replacements.entrySet()){
            message = message.replace(msg.getKey(), msg.getValue());
        }
        return message;
    }
}
