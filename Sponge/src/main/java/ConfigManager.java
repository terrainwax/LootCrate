/*
 * Copyright (C) 2015  Zerthick
 *
 * This file is part of SpongeConfigManager.
 *
 * SpongeConfigManager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * SpongeConfigManager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SpongeConfigManager.  If not, see <http://www.gnu.org/licenses/>.
 */

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static ConfigManager instance = null;

    protected ConfigManager(){
        //Singleton Design Pattern
    }

    public static ConfigManager getInstance(){
        if(instance == null){
            instance = new ConfigManager();
        }
        return instance;
    }

    private ConfigurationLoader<CommentedConfigurationNode> configLoader;
    private CommentedConfigurationNode config;
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
