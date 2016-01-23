package fr.terrainwax.LootCrate;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

@Plugin(id = "LootCrate", name = "LootCrate Project", version = "1.1")
public class MainLootCrate {
	

	@Inject
	@DefaultConfig(sharedRoot = true)
	private Path defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configLoader;

	public ConfigManager configManager;
	@Inject
	private Logger logger;
	@Inject
	private Game game;
	public Logger getLogger() {
		return logger;
	}

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	
    	// Grab Instance of Config Manager
    			configManager = ConfigManager.getInstance();

    			// Setup the config, passing in the default config format builder
    			configManager.setup(defaultConfig, configLoader, getLogger(),
    					new ConfigManager.DefaultConfigBuilder() {
    				
    						@Override
    						public void build(CommentedConfigurationNode config) {
    							List<String> itemList = new LinkedList<>();
    			    			itemList.add("minecraft:stone 10 5");
    			    			itemList.add("minecraft:stone 10");
    			    			List<String> commandlist = new LinkedList<>();
    			    			commandlist.add("give <player> stone 10 5");
    			    			commandlist.add("give <player> stone 10");
    			    			config.getNode("LootCrate", "Crate1","system").setComment("You can choose between item or command for the value").setValue("command");
        						config.getNode("LootCrate", "Crate1","list").setComment("you can use <player> to replace by the player who use the crate").setValue(commandlist);
        						config.getNode("LootCrate", "Crate1","random").setValue(true);
        						config.getNode("LootCrate", "Crate1","description").setValue("its the description of the Crate");
        						config.getNode("LootCrate", "Crate2","system").setComment("You can choose between item or command for the value").setValue("item");
        						config.getNode("LootCrate", "Crate2","list").setComment("syntax of for the item is :item quantity data").setValue(itemList);
        						config.getNode("LootCrate", "Crate2","random").setValue(false);
        						config.getNode("LootCrate", "Crate2","description").setValue("its the description of the Crate");
    						}
    					});

    			List<String> itemList = new LinkedList<>();
    			itemList.add("minecraft:stone 10 5");
    			itemList.add("minecraft:stone 10");
    			List<String> commandlist = new LinkedList<>();
    			commandlist.add("give <player> stone 10 5");
    			commandlist.add("give <player> stone 10");
    			configManager.setup(
    					defaultConfig,
    					configLoader,
    					getLogger(),
    					config -> {
    						config.getNode("LootCrate", "Crate1","system").setComment("You can choose between item or command for the value").setValue("command");
    						config.getNode("LootCrate", "Crate1","list").setComment("you can use <player> to replace by the player who use the crate").setValue(commandlist);
    						config.getNode("LootCrate", "Crate1","random").setValue(true);
    						config.getNode("LootCrate", "Crate1","description").setValue("its the description of the Crate");
    						config.getNode("LootCrate", "Crate2","system").setComment("You can choose between item or command for the value").setValue("item");
    						config.getNode("LootCrate", "Crate2","list").setComment("syntax of for the item is :item quantity data").setValue(itemList);
    						config.getNode("LootCrate", "Crate2","random").setValue(false);
    						config.getNode("LootCrate", "Crate2","description").setValue("its the description of the Crate");
    					});

    			// Get the config file!
    			@SuppressWarnings("unused")
				CommentedConfigurationNode config = configManager.getConfig();
    			
    			CommandSpec List = CommandSpec.builder()
            	        .description(Text.of("List all Crate of the plugin"))
            	        .permission("lootcrate.command.list")
            	        .executor(new LootList(config))
            	        .build();
            	CommandSpec Give = CommandSpec.builder()
            	        .description(Text.of("Give a Loot Crate to the player specified"))
            	        .permission("lootcrate.command.give")
            	        .arguments(
            	                GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
            	                GenericArguments.remainingJoinedStrings(Text.of("id")))
            	        .executor(new LootCommand(config))
            	        .build();
            	CommandSpec Reload = CommandSpec.builder()
            	        .description(Text.of("Reload config of LootCrate"))
            	        .permission("lootcrate.command.Reload")
            	        .executor(new ReloadCommand(configManager))
            	        .build();
                	CommandSpec lootcommand = CommandSpec.builder()
                	        .description(Text.of("Parent command of LootCrate plugin"))
                	        .permission("lootcrate.command")
                	        .child(Give, "give")
                	        .child(List, "list")
                	        .child(Reload, "reload")
                	        .build();
                	game.getCommandManager().register(this, lootcommand, "LC");
                	
                	
                	
    }
    @Listener
    public void onUse(InteractBlockEvent.Secondary event) {
    	CommentedConfigurationNode config = configManager.getConfig();
    	new eventUse(event, config);
    }
    
}