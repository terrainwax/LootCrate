package fr.terrainwax.LootCrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.custom.CustomInventory;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.item.inventory.property.SlotPos;
import org.spongepowered.api.item.inventory.type.Inventory2D;
import org.spongepowered.api.item.inventory.type.InventoryRow;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.chat.ChatTypes;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.translation.FixedTranslation;

import com.google.common.reflect.TypeToken;

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
												List<String> commandlist = null;
												try {
													commandlist = config
															.getNode("LootCrate","LootCrate",key, "list").getList(TypeToken.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												for (String command : commandlist) {
													Sponge.getGame()
															.getCommandManager()
															.process(
																	Sponge.getGame()
																			.getServer()
																			.getConsole(),
																	command.replaceAll(
																			"<player>",
																			firstPlayer
																					.get()
																					.getName()));
												}
											} else if (config.getNode(
													"LootCrate","LootCrate", key, "random")
													.getBoolean() == true) {
												List<String> commandlist = null;
												 RandomCollection<String> comandrandom = new RandomCollection<String>();
												try {
													commandlist = config
															.getNode(
																	"LootCrate","LootCrate",
																	key, "list")
															.getList(
																	TypeToken
																			.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												for (String command : commandlist) {
                                                  Double taille = (double) Integer.parseInt(command.substring(command.indexOf(";")
                                                         + 1, command.length()));
                                                  String resultat = command.substring(0, command.indexOf(";"));
                                                  comandrandom.add(taille, resultat);
                                                  }
												String command = comandrandom.next();
												Sponge.getGame()
														.getCommandManager()
														.process(
																Sponge.getGame()
																		.getServer()
																		.getConsole(),
																command.replaceAll(
																		"<player>",
																		firstPlayer
																				.get()
																				.getName()));
											}
										} else if (config
												.getNode("LootCrate","LootCrate", key,
														"system").getValue()
												.equals("item")) {
											if (config.getNode("LootCrate","LootCrate",
													key, "random").getBoolean() == false) {
												List<String> itemlist = null;
												try {
													itemlist = config
															.getNode(
																	"LootCrate","LootCrate",key, "list").getList(TypeToken.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												for (String item1 : itemlist) {
													// Optional<ItemType>
													// optionalItemType =
													// Sponge.getRegistry().getType(ItemType.class,
													// item1.substring(0,
													// item1.indexOf(" ")));
													// int quantity =
													// Integer.parseInt(item1.substring(item1.indexOf(" ")
													// + 1, item1.length()));
													// firstPlayer.get().getInventory().offer(Sponge.getRegistry().createBuilder(ItemStack.Builder.class).itemType(optionalItemType.get()).quantity(quantity).build());
													Sponge.getGame()
															.getCommandManager()
															.process(
																	Sponge.getGame()
																			.getServer()
																			.getConsole(),
																	"give "
																			+ firstPlayer
																					.get()
																					.getName()
																			+ " "
																			+ item1);
												}
											} else if (config.getNode(
													"LootCrate","LootCrate", key, "random")
													.getBoolean() == true) {
											  RandomCollection<String> itemlist = new RandomCollection<String>();
											  List<String> itemlist2 = null ;
											  try {
												  itemlist2 = config
															.getNode(
																	"LootCrate","LootCrate",
																	key, "list")
															.getList(
																	TypeToken
																			.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												for (String item3 : itemlist2) {
                                                  Double taille = (double) Integer.parseInt(item3.substring(item3.indexOf(";")
                                                         + 1, item3.length()));
                                                  String resultat = item3.substring(0, item3.indexOf(";"));
                                                    itemlist.add(taille, resultat);
                                                  }
												Sponge.getGame()
														.getCommandManager()
														.process(
																Sponge.getGame()
																		.getServer()
																		.getConsole(),
																"give "
																		+ firstPlayer
																				.get()
																				.getName()
																		+ " "
																		+ itemlist.next());
												 
												
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
										origStack.setQuantity(firstPlayer.get()
												.getItemInHand().get()
												.getQuantity() - 1);
										firstPlayer.get().setItemInHand(
												origStack);
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
												List<String> commandlist = null;
												try {
													commandlist = config
															.getNode(
																	"LootCrate","LootCrate",
																	key, "list")
															.getList(
																	TypeToken
																			.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												for (String command : commandlist) {
													Sponge.getGame()
															.getCommandManager()
															.process(
																	Sponge.getGame()
																			.getServer()
																			.getConsole(),
																	command.replaceAll(
																			"<player>",
																			firstPlayer
																					.get()
																					.getName()));
												}
											} else if (config.getNode(
													"LootCrate", key, "random")
													.getBoolean() == true) {
												List<String> commandlist = null;
												try {
													commandlist = config
															.getNode(
																	"LootCrate","LootCrate",
																	key, "list")
															.getList(
																	TypeToken
																			.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												int i = (int) Math.floor(Math
														.random()
														* commandlist.size());
												String command = commandlist
														.get(i);
												Sponge.getGame()
														.getCommandManager()
														.process(
																Sponge.getGame()
																		.getServer()
																		.getConsole(),
																command.replaceAll(
																		"<player>",
																		firstPlayer
																				.get()
																				.getName()));
											}
										} else if (config
												.getNode("LootCrate","LootCrate", key,
														"system").getValue()
												.equals("item")) {
											if (config.getNode("LootCrate","LootCrate",
													key, "random").getBoolean() == false) {
												List<String> itemlist = null;
												try {
													itemlist = config
															.getNode(
																	"LootCrate","LootCrate",
																	key, "list")
															.getList(
																	TypeToken
																			.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												for (String item1 : itemlist) {
													// Optional<ItemType>
													// optionalItemType =
													// Sponge.getRegistry().getType(ItemType.class,
													// item1.substring(0,
													// item1.indexOf(" ")));
													// int quantity =
													// Integer.parseInt(item1.substring(item1.indexOf(" ")
													// + 1, item1.length()));
													// firstPlayer.get().getInventory().offer(Sponge.getRegistry().createBuilder(ItemStack.Builder.class).itemType(optionalItemType.get()).quantity(quantity).build());
													Sponge.getGame()
															.getCommandManager()
															.process(
																	Sponge.getGame()
																			.getServer()
																			.getConsole(),
																	"give "
																			+ firstPlayer
																					.get()
																					.getName()
																			+ " "
																			+ item1);
												}
											} else if (config.getNode(
													"LootCrate","LootCrate", key, "random")
													.getBoolean() == true) {
												List<String> itemlist = null;
												try {
													itemlist = config
															.getNode(
																	"LootCrate","LootCrate",
																	key, "list")
															.getList(
																	TypeToken
																			.of(String.class));
												} catch (ObjectMappingException e) {
													e.printStackTrace();
												}
												int i = (int) Math.floor(Math
														.random()
														* itemlist.size());
												String item1 = itemlist.get(i);
												Sponge.getGame()
														.getCommandManager()
														.process(
																Sponge.getGame()
																		.getServer()
																		.getConsole(),
																"give "
																		+ firstPlayer
																				.get()
																				.getName()
																		+ " "
																		+ item1);
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
										origStack.setQuantity(firstPlayer.get()
												.getItemInHand().get()
												.getQuantity() - 1);
										firstPlayer.get().setItemInHand(
												origStack);
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
