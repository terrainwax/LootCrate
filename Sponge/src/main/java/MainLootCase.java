import java.io.IOException;
import java.nio.file.Path;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

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
            	    .description(Text.of("/LC give <player> <case>"))
            	    .permission("Loot.command.execute")
            	    .arguments(
                	                GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                	                GenericArguments.remainingJoinedStrings(Text.of("case")))
            	    .executor(new CommandExecutor() {
            	    	
            	    	public CommandResult execute(CommandSource src, CommandContext args)
            	    			throws CommandException {
            	    		Player player = args.<Player>getOne("player").get();
            	            String caseid = args.<String>getOne("case").get();
            	    		/*if(caseid == "test"){
            	    			int i = (int) Math.floor(Math.random() * 101);
            	    			if(i < 90){
            	    			src.sendMessages(Text.of("test"));
            	    			return CommandResult.success();
            	    			}else if(i > 90){
            	    				src.sendMessages(Text.of("bravo tu a eu 10% de chance"));
            	    				return CommandResult.success();
            	    			}
            	    			return CommandResult.success();
            	    		}else{
            	    			Text errorText = Text.builder("Cette case n'existe pas").color(TextColors.RED).build();
            	    			src.sendMessages(errorText);
            	    			return CommandResult.success();
            	    		}*/
            	    		src.sendMessages(Text.of("test"));
            	            return CommandResult.success();
            	    		
            	    	}
            	    	
            	    })
            	    .build();

            	game.getCommandManager().register(this, LootCommand, "LC give", "Lootcommand give", "LC G");
            	
                CommandSpec LootList = CommandSpec.builder()
                	    .description(Text.of(""))
                	    .permission("Loot.command.list")
                	    .executor(new LootList())
                	    .build();

                	game.getCommandManager().register(this, LootList, "LC list");
                	CommandSpec myCommandSpec = CommandSpec.builder()
                	        .description(Text.of("Send a message to a player"))
                	        .permission("myplugin.command.message")

                	        .arguments(
                	                GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                	                GenericArguments.remainingJoinedStrings(Text.of("id")))

                	        .executor(new CommandExecutor() {
                	            public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                	                Player player = args.<Player>getOne("player").get();
                	                String caseid = args.<String>getOne("id").get();
                	                src.sendMessages(Text.of(caseid));

                	                if(caseid == caseid){
                	        			int i = (int) Math.floor(Math.random() * 101);
                	        			if(i < 90){
                	        			src.sendMessages(Text.of("test"));
                	        			}else if(i > 90){
                	        				src.sendMessages(Text.of("bravo tu a eu 10% de chance"));
                	        			}
                	        			return CommandResult.success();
                	        		}else{
                	        			Text errorText = Text.builder("Cette case n'existe pas").color(TextColors.RED).build();
                	        			src.sendMessages(errorText);
                	        			return CommandResult.success();
                	        		}
                	            }
                	        })
                	        .build();

                	game.getCommandManager().register(this, myCommandSpec, "test", "msg", "m");
                	
        	
    }
    
}
