package fr.terrainwax.LootCrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;

public class eventUse {

	public eventUse(Secondary event, CommentedConfigurationNode config) {
		Cause cause = event.getCause();
		Set<Object> keySet = config.getNode("LootCrate","LootCrate").getChildrenMap()
				.keySet();
		Optional<Player> firstPlayer = cause.first(Player.class);
		Optional<ItemStack> item = firstPlayer.get().getItemInHand();
		List<Text> textList = new ArrayList<Text>();
		if (firstPlayer.get().getItemInHand().isPresent()) {
			if (item.get().get(Keys.DISPLAY_NAME).isPresent()
					&& item.get().get(Keys.ITEM_LORE).isPresent()) {
				Optional<Text> t = item.get().get(Keys.DISPLAY_NAME);
				Optional<List<Text>> ist = item.get().get(Keys.ITEM_LORE);

				if (t.isPresent()) {
					if (ist.isPresent()) {
						if (t.get().equals(Text.of("LootCrate"))) {

							for (Object key : keySet) {
								textList.add(Text.of("LootCrate-" + key));

								if (ist.get().equals(textList)) {
									if (config.getNode("LootCrate","LootCrate", key, "key")
											.getBoolean() == true) {
										ItemType item2 = ItemTypes.TRIPWIRE_HOOK;
										List<Text> textList3 = new ArrayList<Text>();
										textList3.clear();
										textList3.add(Text.of("LootKey-" + key));
										ItemStack st = (ItemStack) Sponge.getRegistry()
												.createBuilder(ItemStack.Builder.class).itemType(item2)
												.build();
										st.offer(Keys.DISPLAY_NAME, Text.of("LootKey"));
										st.offer(Keys.ITEM_LORE, textList3);
										Inventory items = firstPlayer.get().getInventory().query(st);
										if (items.peek(1).filter(stack -> stack.getQuantity() >= 1).isPresent()) {
										    items.poll(1);
										ItemStack origStack = firstPlayer.get()
												.getItemInHand().get();
										if (config
												.getNode("LootCrate","LootCrate", key,"system").getValue().equals("command")) {
											if (config.getNode("LootCrate","LootCrate",
													key, "random").getBoolean() == false) {
												new CommandGive(key,firstPlayer,config);
											} else if (config.getNode(
													"LootCrate","LootCrate", key, "random")
													.getBoolean() == true) {
												new CommandRandom(key,firstPlayer,config);
											}
										} else if (config
												.getNode("LootCrate","LootCrate", key,
														"system").getValue()
												.equals("item")) {
											if (config.getNode("LootCrate","LootCrate",
													key, "random").getBoolean() == false) {
											  new ItemGive(key,firstPlayer,config);
											} else if (config.getNode(
													"LootCrate","LootCrate", key, "random")
													.getBoolean() == true) {
											  new ItemRandom(key,firstPlayer,config);
												 
												
											}
										}
										
									
										firstPlayer
												.get()
												.sendMessage(
														ChatTypes.ACTION_BAR,
														Text.builder(
																"You opened ")
																.color(TextColors.BLUE)
																.append(Text
																		.builder(
																				key.toString())
																		.color(TextColors.GOLD)
																		.build())
																.build());
										firstPlayer.get().getInventory().query(origStack).poll(1);
										/*origStack.setQuantity(firstPlayer.get()
												.getItemInHand().get()
												.getQuantity() - 1);
										firstPlayer.get().setItemInHand(
												origStack);*/
										event.setCancelled(true);
										}else {
											firstPlayer.get().sendMessage(Text.builder("You don't have the key").color(TextColors.RED).build());
											}
									} else {
									  
										ItemStack origStack = firstPlayer.get()
												.getItemInHand().get();
										if (config
												.getNode("LootCrate","LootCrate", key,
														"system").getValue()
												.equals("command")) {
											if (config.getNode("LootCrate","LootCrate",
													key, "random").getBoolean() == false) {
											  new CommandGive(key,firstPlayer,config);
											} else if (config.getNode(
													"LootCrate","LootCrate", key, "random")
													.getBoolean() == true) {
											 
											  new CommandRandom(key,firstPlayer,config);
											}
										} else if (config
												.getNode("LootCrate","LootCrate", key,
														"system").getValue()
												.equals("item")) {
											if (config.getNode("LootCrate","LootCrate",
													key, "random").getBoolean() == false) {
												new ItemGive(key,firstPlayer,config);
											} else if (config.getNode(
													"LootCrate","LootCrate", key, "random")
													.getBoolean() == true) {
											  new ItemRandom(key,firstPlayer,config);
                                                
											}
										}
										firstPlayer
												.get()
												.sendMessage(
														ChatTypes.ACTION_BAR,
														Text.builder(
																"You opened ")
																.color(TextColors.BLUE)
																.append(Text
																		.builder(
																				key.toString())
																		.color(TextColors.GOLD)
																		.build())
																.build());
										firstPlayer.get().getInventory().query(origStack).poll(1);
										/*origStack.setQuantity(firstPlayer.get()
												.getItemInHand().get()
												.getQuantity() - 1);
										firstPlayer.get().setItemInHand(
												origStack);*/
										event.setCancelled(true);
									}
								} else {
									textList.remove(Text.of("LootCrate-" + key));
								}
							}
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

}
