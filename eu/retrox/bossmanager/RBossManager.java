package eu.retrox.bossmanager;

import eu.retrox.bossmanager.commands.SpawnBosses;
import eu.retrox.bossmanager.managers.ConfigurationManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RBossManager extends JavaPlugin {

    private static RBossManager plugin;

    @Override
    public void onEnable() {
        RBossManager.plugin = this;
        setupConfig();
    }

    public static RBossManager getPlugin() {
        return plugin;
    }

    private void setupCommands() {
        this.getCommand("spawnbosses").setExecutor(new SpawnBosses());
    }

    public void setupConfig() {
        ConfigurationManager man = ConfigurationManager.getInstance();
        man.setup();
    }
}
