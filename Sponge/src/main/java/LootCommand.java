import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class LootCommand implements CommandExecutor {

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		Player player = args.<Player>getOne("player").get();
        ItemType item = ItemTypes.CHEST ;
        List<Text> textList = new ArrayList<Text>();
        textList.add(Text.of("LootCrate-PokemonShinyAleatoire"));
        player.sendMessage(Text.of(textList));


        ItemStack st = (ItemStack) Sponge.getRegistry().createBuilder(ItemStack.Builder.class).itemType(item).build();
        st.offer(Keys.DISPLAY_NAME, Text.of("LootCrate"));
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
			Text errorText = Text.builder("This crate doesn't exist").color(TextColors.RED).build();
			src.sendMessages(errorText);
			return CommandResult.success();
		}
		
		
	}

}
