package jp.softrain.coinAPI.commands;

import jp.softrain.coinAPI.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Map;

public class CoinsCommand implements CommandExecutor {

    private Main instance;
    public CoinsCommand(Main instance){
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (args.length == 0){
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
               BigDecimal money = instance.getDatabaseManager().getMoney(player.getUniqueId());

               Bukkit.getScheduler().runTask(instance, () -> {
                  player.sendMessage(instance.getMessageManager().get("coins.self-coins", Map.of("%balance%",money.toString())));
               });
            });
            return true;
        }

        switch (args[0].toLowerCase()){
            case "add" -> {
                if(!player.hasPermission("coins.add")){
                    player.sendMessage(instance.getMessageManager().get("errors.no-permission", Map.of("%permission%", "coins.add")));
                    return true;
                }

                if (args.length != 3){
                    player.sendMessage(instance.getMessageManager().get("errors.wrong-args"));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);

                if (target == null){
                    player.sendMessage(instance.getMessageManager().get("errors.target-not-found"));
                    return true;
                }

                BigDecimal amount;
                try{
                    amount = new BigDecimal(args[2]);
                }catch (NumberFormatException e){
                    player.sendMessage(instance.getMessageManager().get("errors.use-numbers"));
                    return true;
                }

                if (amount.signum() < 0){
                    player.sendMessage(instance.getMessageManager().get("errors.under-zero"));
                    return true;
                }

                if (!hasMoreThanTwoDecimalPlaces(amount)){
                    player.sendMessage(instance.getMessageManager().get("errors.too-much-decimal-places"));
                    return true;
                }

                Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {

                   instance.getDatabaseManager().addMoney(target.getUniqueId(), amount);

                    BigDecimal newBalance = instance.getDatabaseManager().getMoney(target.getUniqueId());
                   Bukkit.getScheduler().runTask(instance, () -> {
                       player.sendMessage(instance.getMessageManager().get("coins.add.sender", Map.of("%amount%",amount.toString(), "%receiver%", target.getName())));
                       target.sendMessage(instance.getMessageManager().get("coins.add.receiver", Map.of("%sender%", player.getName(), "%amount%",amount.toString(),"%balance%", newBalance.toString())));
                   });
                });

            }

            case "remove" -> {
                if(!player.hasPermission("coins.remove")){
                    player.sendMessage(instance.getMessageManager().get("errors.no-permission", Map.of("%permission%", "coins.remove")));
                    return true;
                }

                if (args.length != 3){
                    player.sendMessage(instance.getMessageManager().get("errors.wrong-args"));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);

                if (target == null){
                    player.sendMessage(instance.getMessageManager().get("errors.target-not-found"));
                    return true;
                }

                BigDecimal amount;
                try{
                    amount = new BigDecimal(args[2]);
                }catch (NumberFormatException e){
                    player.sendMessage(instance.getMessageManager().get("errors.use-numbers"));
                    return true;
                }

                if (amount.signum() < 0){
                    player.sendMessage(instance.getMessageManager().get("errors.under-zero"));
                    return true;
                }

                if (!hasMoreThanTwoDecimalPlaces(amount)){
                    player.sendMessage(instance.getMessageManager().get("errors.too-much-decimal-places"));
                    return true;
                }

                Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                    instance.getDatabaseManager().removeMoney(target.getUniqueId(), amount);

                    BigDecimal newBalance = instance.getDatabaseManager().getMoney(target.getUniqueId());
                    Bukkit.getScheduler().runTask(instance, () -> {
                        player.sendMessage(instance.getMessageManager().get("coins.remove.sender", Map.of("%amount%",amount.toString(), "%receiver%", target.getName())));
                        target.sendMessage(instance.getMessageManager().get("coins.remove.receiver", Map.of("%sender%", player.getName(), "%amount%",amount.toString(),"%balance%", newBalance.toString())));
                    });
                });
            }
            case "get" -> {
                if(!player.hasPermission("coins.get")){
                    player.sendMessage(instance.getMessageManager().get("errors.no-permission", Map.of("%permission%", "coins.get")));
                    return true;
                }

                if (args.length != 2){
                    player.sendMessage(instance.getMessageManager().get("errors.wrong-args"));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);

                if (target == null){
                    player.sendMessage(instance.getMessageManager().get("errors.target-not-found"));
                    return true;
                }

                Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                    BigDecimal currentBalance = instance.getDatabaseManager().getMoney(player.getUniqueId());

                    Bukkit.getScheduler().runTask(instance, () -> {
                        player.sendMessage(instance.getMessageManager().get("coins.get", Map.of( "%target%", target.getName())));
                    });
                });

            }
            case "set" -> {
                if(!player.hasPermission("coins.set")){
                    player.sendMessage(instance.getMessageManager().get("errors.no-permission", Map.of("%permission%", "coins.set")));
                    return true;
                }

                if (args.length != 3){
                    player.sendMessage(instance.getMessageManager().get("errors.wrong-args"));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);

                if (target == null){
                    player.sendMessage(instance.getMessageManager().get("errors.target-not-found"));
                    return true;
                }

                BigDecimal amount;
                try{
                    amount = new BigDecimal(args[2]);
                }catch (NumberFormatException e){
                    player.sendMessage(instance.getMessageManager().get("errors.use-numbers"));
                    return true;
                }

                if (!hasMoreThanTwoDecimalPlaces(amount)){
                    player.sendMessage(instance.getMessageManager().get("errors.too-much-decimal-places"));
                    return true;
                }

                Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                    instance.getDatabaseManager().setMoney(target.getUniqueId(), amount);

                    BigDecimal newBalance = instance.getDatabaseManager().getMoney(target.getUniqueId());
                    Bukkit.getScheduler().runTask(instance, () -> {
                        player.sendMessage(instance.getMessageManager().get("coins.set.sender", Map.of("%amount%",amount.toString(), "%receiver%", target.getName())));
                        target.sendMessage(instance.getMessageManager().get("coins.set.receiver", Map.of("%sender%", player.getName(), "%amount%",amount.toString(),"%balance%", newBalance.toString())));
                    });
                });

            }
            case "reset" -> {
                if(!player.hasPermission("coins.reset")){
                    player.sendMessage(instance.getMessageManager().get("errors.no-permission", Map.of("%permission%", "coins.reset")));
                    return true;
                }

                if (args.length != 2){
                    player.sendMessage(instance.getMessageManager().get("errors.wrong-args"));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);

                if (target == null){
                    player.sendMessage(instance.getMessageManager().get("errors.target-not-found"));
                    return true;
                }

                Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                    instance.getDatabaseManager().resetMoney(target.getUniqueId());

                    BigDecimal newBalance = instance.getDatabaseManager().getMoney(target.getUniqueId());
                    Bukkit.getScheduler().runTask(instance, () -> {
                        player.sendMessage(instance.getMessageManager().get("coins.reset.sender", Map.of("%receiver%", target.getName())));
                        target.sendMessage(instance.getMessageManager().get("coins.reset.receiver", Map.of("%sender%", player.getName(), "%balance%", newBalance.toString())));
                    });
                });

            }
        }
        return true;
    }
    public boolean hasMoreThanTwoDecimalPlaces(BigDecimal value){
        return value.scale() < 3;
    }
}
