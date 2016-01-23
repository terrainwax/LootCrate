package fr.terrainwax.LootCrate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import com.google.common.reflect.TypeToken;




public class eventUse {

	public eventUse(Secondary event, CommentedConfigurationNode config) {
		Cause cause = event.getCause();
		Set<Object> keySet = config.getNode("LootCrate").getChildrenMap().keySet();
    	Optional<Player> firstPlayer = cause.first(Player.class);
    	Optional<ItemStack> item = firstPlayer.get().getItemInHand();
    	List<Text> textList = new ArrayList<Text>();
    	if(firstPlayer.get().getItemInHand().isPresent()){
    	if(item.get().get(Keys.DISPLAY_NAME).isPresent() && item.get().get(Keys.ITEM_LORE).isPresent()){
    	Optional<Text> t = item.get().get(Keys.DISPLAY_NAME);
        Optional<List<Text>> ist = item.get().get(Keys.ITEM_LORE);
    	

    	if(t.isPresent()){
    		if(ist.isPresent()){
    		if(t.get().equals(Text.of("LootCrate"))){
    			
    			
    			
    			for (Object key : keySet) {
    			textList.add(Text.of("LootCrate-"+key));
    			
    			
    			if(ist.get().equals(textList)){
    				
    				
    				
    			ItemStack origStack = firstPlayer.get().getItemInHand().get();
    			
    			
    			
    			if(config.getNode("LootCrate",key,"system").getValue().equals("command")){
    				
    				
    				
    			if(config.getNode("LootCrate",key,"random").getBoolean() == false){
    				
    				
    				
    			 List<String> commandlist = null;
				try {
					commandlist = config.getNode("LootCrate",key,"list").getList(TypeToken.of(String.class));
				} catch (ObjectMappingException e) {
					e.printStackTrace();
				}
    				for(String command : commandlist){
    					Sponge.getGame().getCommandManager().process(Sponge.getGame().getServer().getConsole(), command.replaceAll("<player>", firstPlayer.get().getName()));
    				}
    				
    				
    			}else if(config.getNode("LootCrate",key,"random").getBoolean() == true ) {
    				
    				
    				List<String> commandlist = null;
    				
    				try {
    					commandlist = config.getNode("LootCrate",key,"list").getList(TypeToken.of(String.class));
    				} catch (ObjectMappingException e) {
    					e.printStackTrace();
    				}
    				int i = (int) Math.floor(Math.random() * commandlist.size());
    				String command = commandlist.get(i);
    				Sponge.getGame().getCommandManager().process(Sponge.getGame().getServer().getConsole(), command.replaceAll("<player>", firstPlayer.get().getName()));
				}
    			
    			
    			}else if(config.getNode("LootCrate",key,"system").getValue().equals("item")){
    				
    				
    				if(config.getNode("LootCrate",key,"random").getBoolean() == false){
    					
    					
    					
    				List<String> itemlist = null;
    				try {
    					itemlist = config.getNode("LootCrate",key,"list").getList(TypeToken.of(String.class));
    				} catch (ObjectMappingException e) {
    					e.printStackTrace();
    				}
        				for(String item1 : itemlist){
        					//Optional<ItemType> optionalItemType = Sponge.getRegistry().getType(ItemType.class, item1.substring(0, item1.indexOf(" ")));
        					//int quantity = Integer.parseInt(item1.substring(item1.indexOf(" ") + 1, item1.length()));
        					//firstPlayer.get().getInventory().offer(Sponge.getRegistry().createBuilder(ItemStack.Builder.class).itemType(optionalItemType.get()).quantity(quantity).build());
        					 Sponge.getGame().getCommandManager().process(Sponge.getGame().getServer().getConsole(), "give "+firstPlayer.get().getName()+" "+item1);
        					 
        					 
        				}}else if(config.getNode("LootCrate",key,"random").getBoolean() == true ) {
        					
        					List<String> itemlist = null;
            				try {
            					itemlist = config.getNode("LootCrate",key,"list").getList(TypeToken.of(String.class));
            				} catch (ObjectMappingException e) {
            					e.printStackTrace();
            				}
            				int i = (int) Math.floor(Math.random() * itemlist.size());
            				String item1 = itemlist.get(i);
            				Sponge.getGame().getCommandManager().process(Sponge.getGame().getServer().getConsole(), "give "+firstPlayer.get().getName()+" "+item1);
        					
        					
        				}
    			}
    			
    			
    			firstPlayer.get().getInventory().offer(Sponge.getRegistry().createBuilder(ItemStack.Builder.class).itemType(ItemTypes.COAL_BLOCK).build());
    			origStack.setQuantity(firstPlayer.get().getItemInHand().get().getQuantity()-1);
    			firstPlayer.get().setItemInHand(origStack);
    			event.setCancelled(true);
    			}else{
    			textList.remove(Text.of("LootCrate-"+key));
    			}
    			}}
    	
    		
    
    		
    	}}}}
	}

}
