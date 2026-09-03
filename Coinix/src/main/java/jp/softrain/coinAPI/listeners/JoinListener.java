package jp.softrain.coinAPI.listeners;

import jp.softrain.coinAPI.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private Main instance;
    public JoinListener(Main instance){
        this.instance = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
           if (!instance.getDatabaseManager().isRegistered(uuid)) {
               instance.getDatabaseManager().initializePlayer(uuid);

               Bukkit.getScheduler().runTask(instance, () -> {
                   player.sendMessage("First Join! Welcome in Database.");
               });
           }
        });
    }
}
