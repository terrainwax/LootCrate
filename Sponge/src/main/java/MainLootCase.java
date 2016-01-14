import java.io.IOException;
import java.nio.file.Path;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import com.google.inject.Inject;

@Plugin(id = "LootCase", name = "LootCase Project", version = "1.0")
public class MainLootCase {
	@Inject
	@DefaultConfig(sharedRoot = true)
	private Path defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	@Inject
    private PluginContainer instance;
	@Inject
	@ConfigDir(sharedRoot = false)
	private Path privateConfigDir;
	@Inject
	private Logger logger;
	@Inject
	private Game game;
	
	

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	
    	Path potentialFile = privateConfigDir;
    	ConfigurationLoader<CommentedConfigurationNode> loader = HoconConfigurationLoader.builder().setPath(potentialFile).build();
    	ConfigurationNode rootNode;
    	try {
    	    rootNode = loader.load();
    	} catch(IOException e) {
    	    // error
    	}
        CommandSpec LootCommand = CommandSpec.builder()
        		
            	    .description(Text.of(""))
            	    .permission("Loot.command.execute")
            	    .arguments(
                GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                GenericArguments.remainingJoinedStrings(Text.of("case")))
            	    .executor(new LootCommand())
            	    .build();

            	game.getCommandManager().register(this, LootCommand, "LC give");
            	
                CommandSpec LootList = CommandSpec.builder()
                	    .description(Text.of(""))
                	    .permission("Loot.command.list")
                	    .executor(new LootList())
                	    .build();

                	game.getCommandManager().register(this, LootList, "LC list");
        	
    }
    
}
