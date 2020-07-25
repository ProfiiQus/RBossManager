package eu.retrox.bossmanager.managers;

import eu.retrox.bossmanager.RBossManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationManager {

    private File configFile;
    private FileConfiguration config;
    private final RBossManager plugin;

    private static ConfigurationManager instance;

    private ConfigurationManager() {
        this.plugin = RBossManager.getPlugin();
    }

    public static ConfigurationManager getInstance() {
        if(instance == null) instance = new ConfigurationManager();
        return instance;
    }

    public void setup() {

        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), "config.yml");

        if(!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}