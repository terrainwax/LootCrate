package fr.terrainwax.LootCrate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

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
	public CommentedConfigurationNode config;

	public LootCommand(CommentedConfigurationNode config) {
		this.config = config;
	}

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {

		Player player = args.<Player> getOne("player").get();
		Object CrKE = args.getOne("Crate/Key").get();
		Object caseid = args.getOne("ID").get();
		Object number = args.getOne("Number").get();
		if(CrKE.equals("Key")){
			if (config.getNode("LootCrate","LootKey").getChildrenMap().keySet()
					.contains(caseid)) {
				ItemType item = ItemTypes.TRIPWIRE_HOOK;
				List<Text> textList = new ArrayList<Text>();
				textList.add(Text.of("LootKey-" + caseid));
				ItemStack st = (ItemStack) Sponge.getRegistry()
						.createBuilder(ItemStack.Builder.class).itemType(item)
						.build();
				st.offer(Keys.DISPLAY_NAME, Text.of("LootKey"));
				st.offer(Keys.ITEM_LORE, textList);
				st.setQuantity((int)number);
				player.getInventory().offer(st);
				/*
				 * int i = (int) Math.floor(Math.random() * 101); if(i < 90){
				 * src.sendMessages(Text.of("test")); }else if(i > 90){
				 * src.sendMessages(Text.of("bravo tu a eu 10% de chance")); }
				 */
				return CommandResult.success();
			} else {
				Text errorText = Text.builder("This Key doesn't exist")
						.color(TextColors.RED).build();
				src.sendMessages(errorText);
				return CommandResult.success();
			}
			
		}else if(CrKE.equals("Crate")){
		if (config.getNode("LootCrate","LootCrate").getChildrenMap().keySet()
				.contains(caseid)) {
			ItemType item = ItemTypes.CHEST;
			List<Text> textList = new ArrayList<Text>();
			textList.add(Text.of("LootCrate-" + caseid));
			ItemStack st = (ItemStack) Sponge.getRegistry()
					.createBuilder(ItemStack.Builder.class).itemType(item)
					.build();
			st.offer(Keys.DISPLAY_NAME, Text.of("LootCrate"));
			st.offer(Keys.ITEM_LORE, textList);
			st.setQuantity((int)number);
			player.getInventory().offer(st);

			/*
			 * int i = (int) Math.floor(Math.random() * 101); if(i < 90){
			 * src.sendMessages(Text.of("test")); }else if(i > 90){
			 * src.sendMessages(Text.of("bravo tu a eu 10% de chance")); }
			 */
			return CommandResult.success();
		} else {
			Text errorText = Text.builder("This crate doesn't exist")
					.color(TextColors.RED).build();
			src.sendMessages(errorText);
			return CommandResult.success();
		}
		}else{
			Text errorText = Text.builder("Bad arguments")
					.color(TextColors.RED).build();
			src.sendMessages(errorText);
			return CommandResult.success();
		}
		

	}

}
