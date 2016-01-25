package fr.terrainwax.LootCrate;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static ConfigManager instance = null;

    protected ConfigManager(){
 
    }

    public static ConfigManager getInstance(){
        if(instance == null){
            instance = new ConfigManager();
        }
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    public CommentedConfigurationNode config;
    private Logger logger;

    public void setup(Path configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader, Logger logger, DefaultConfigBuilder defaultConfigBuilder) {
        this.configLoader = configLoader;
        this.logger = logger;

        if (!Files.exists(configFile)) {
            try {
                if(configFile.toFile().createNewFile()){
                    loadConfig();
                    defaultConfigBuilder.build(config);
                    saveConfig();
                }
            } catch (IOException e) {
                logger.error("Error when attempting to create default config file! Error: " + e);
            }
        }
        else {
            loadConfig();
        }
    }

    public CommentedConfigurationNode getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            configLoader.save(config);
        }
        catch (IOException e) {
            logger.error("Error when attempting to save config file! Error: " + e);
        }
    }

    public void loadConfig() {
        try {
            config = configLoader.load();
        }
        catch (IOException e) {
            logger.error("Error when attempting to load config file! Error: " + e);
        }
    }

    public interface DefaultConfigBuilder{
        void build(CommentedConfigurationNode config);
    }
}
