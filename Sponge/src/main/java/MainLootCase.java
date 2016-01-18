import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.item.ImmutableInventoryItemData;
import org.spongepowered.api.data.manipulator.mutable.entity.SizeData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.pagination.PaginationBuilder;
import org.spongepowered.api.service.pagination.PaginationService;
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
            	
                	CommandSpec lootcommand = CommandSpec.builder()
                	        .description(Text.of("Give a Loot case to a player"))
                	        .permission("lootcase.command.give")

                	        .arguments(
                	                GenericArguments.onlyOne(GenericArguments.player(Text.of("player"))),
                	                GenericArguments.remainingJoinedStrings(Text.of("id")))

                	        .executor(new CommandExecutor() {

								public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

                	                Player player = args.<Player>getOne("player").get();
                	                ItemType item = ItemTypes.CHEST ;
                	                List<Text> textList = new ArrayList<Text>();
                	                textList.add(Text.of("LootCase-PokemonShinyAleatoire"));
                	                player.sendMessage(Text.of(textList));


                	                ItemStack st = (ItemStack) Sponge.getRegistry().createBuilder(ItemStack.Builder.class).itemType(item).build();
                	                st.offer(Keys.DISPLAY_NAME, Text.of("LootCase"));
                	                st.offer(Keys.ITEM_LORE, textList);
                	                
                	              
                							
                	                Object caseid = args.getOne("id").get();
                	                src.sendMessages(Text.of(caseid));

                	                if(caseid.equals("1")){
                	                	player.getInventory().offer(st);
                	        			/*int i = (int) Math.floor(Math.random() * 101);
                	        			if(i < 90){
                	        			src.sendMessages(Text.of("test"));
                	        			}else if(i > 90){
                	        				src.sendMessages(Text.of("bravo tu a eu 10% de chance"));
                	        			}*/
                	        			return CommandResult.success();
                	        		}else{
                	        			Text errorText = Text.builder("Cette case n'existe pas").color(TextColors.RED).build();
                	        			src.sendMessages(errorText);
                	        			return CommandResult.success();
                	        		}
                	            }
                	        })
                	        .build();

                	game.getCommandManager().register(this, lootcommand, "LCgive", "Lootcommand give", "LC G");
                	
        	
    }
    @SuppressWarnings("static-access")
	@Listener
    public void onUse(InteractBlockEvent.Secondary event) {
    	Cause cause = event.getCause();
    	Optional<Player> firstPlayer = cause.first(Player.class);
    	Optional<ItemStack> item = firstPlayer.get().getItemInHand();
    	Optional<Text> t = item.get().get(Keys.DISPLAY_NAME);
    	Optional<List<Text>> ist = item.get().get(Keys.ITEM_LORE);
    	List<Text> textList = new ArrayList<Text>();
        textList.add(Text.of("LootCase-PokemonShinyAleatoire"));
    	if(t.isPresent()){
    		if(ist.isPresent()){
    		if(t.get().equals(Text.of("LootCase"))){
    			if(ist.get().equals(textList)){
    			ItemStack origStack = firstPlayer.get().getItemInHand().get();
    			firstPlayer.get().sendMessage(Text.builder("Tu vient de Gagner un shiny aleatoire").color(TextColors.AQUA).build());
    			origStack.setQuantity(firstPlayer.get().getItemInHand().get().getQuantity()-1);
    			firstPlayer.get().setItemInHand(origStack);
    			 event.setCancelled(true);
    		}}
    	
    		
    
    		
    	}}
    }
    
}
