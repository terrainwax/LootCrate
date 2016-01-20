package fr.terrainwax.LootCrate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class eventUse {

	public eventUse(Secondary event) {
		Cause cause = event.getCause();
    	Optional<Player> firstPlayer = cause.first(Player.class);
    	Optional<ItemStack> item = firstPlayer.get().getItemInHand();
    	Optional<Text> t = item.get().get(Keys.DISPLAY_NAME);
    	Optional<List<Text>> ist = item.get().get(Keys.ITEM_LORE);
    	List<Text> textList = new ArrayList<Text>();
        textList.add(Text.of("LootCrate-PokemonShinyAleatoire"));
    	if(t.isPresent()){
    		if(ist.isPresent()){
    		if(t.get().equals(Text.of("LootCrate"))){
    			if(ist.get().equals(textList)){
    			ItemStack origStack = firstPlayer.get().getItemInHand().get();
    			firstPlayer.get().sendMessage(Text.builder("Tu vient de Gagner un shiny aleatoire").color(TextColors.AQUA).build());
    			 Sponge.getGame().getCommandManager().process(Sponge.getGame().getServer().getConsole(), "prandom "+firstPlayer.get().getName()+" s" );
    			origStack.setQuantity(firstPlayer.get().getItemInHand().get().getQuantity()-1);
    			firstPlayer.get().setItemInHand(origStack);
    			 event.setCancelled(true);
    		}}
    	
    		
    
    		
    	}}
	}

}
