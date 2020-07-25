package eu.retrox.bossmanager.commands;

import eu.retrox.bossmanager.managers.ConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossAPI;
import org.mineacademy.boss.api.BossSpawnReason;
import org.mineacademy.boss.api.SpawnedBoss;

import java.util.*;

public class SpawnBosses implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        FileConfiguration config = ConfigurationManager.getInstance().getConfig();
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(!player.hasPermission("rbossmanager.spawn")) {
                sendMessage(commandSender, config.getString("messages.not-enough-permissions"));
                return false;
            }
        }

        Set<String> bossNames = config.getConfigurationSection("bosses").getKeys(false);
        HashMap<String, Boolean> bosses = new HashMap<>();
        for(String bossName: bossNames) {
            bosses.put(bossName, false);
        }

        World world = Bukkit.getWorld(config.getString("world"));
        if(world == null) {
            sendMessage(commandSender, config.getString("messages.world-not-exist"));
            return false;
        }

        for(SpawnedBoss boss: BossAPI.getBosses(world)) {
            String bossName = boss.getBoss().getName();
            if(bosses.containsKey(bossName)) {
                bosses.remove(bossName);
                bosses.put(bossName, true);
            }
        }

        for(Map.Entry<String, Boolean> entry: bosses.entrySet()) {
            if(!entry.getValue()) {
                Boss boss = BossAPI.getBoss(entry.getKey());
                boss.spawn(new Location(world, config.getDouble("bosses." + boss.getName() + ".x"), config.getDouble("bosses." + boss.getName() + ".y"), config.getDouble("bosses." + boss.getName() + ".z")), BossSpawnReason.CUSTOM);
                sendMessage(commandSender, config.getString("messages.boss-spawned").replace("{BOSS}", boss.getName()));
            }
        }

        return false;
    }

    private void sendMessage(CommandSender sender, String message) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            sender.sendMessage(ChatColor.stripColor(message));
        }
    }
}
