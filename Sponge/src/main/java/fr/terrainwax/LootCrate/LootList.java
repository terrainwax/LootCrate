package fr.terrainwax.LootCrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.service.pagination.PaginationService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextAction;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class LootList implements CommandExecutor {

	private CommentedConfigurationNode config;

	public LootList(CommentedConfigurationNode config) {
		this.config = ConfigManager.getInstance().getConfig();
	}

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		Set<Object> keySet = config.getNode("LootCrate","LootCrate").getChildrenMap()
				.keySet();
		Set<Object> keySett = config.getNode("LootCrate","LootKey").getChildrenMap()
            .keySet();
		List<Text> contents = new ArrayList<Text>();
		for (Object key : keySet) {
			String text = config.getNode("LootCrate","LootCrate", key, "description")
					.getString();
			contents.add(Text
					.builder(key.toString())
					.color(TextColors.AQUA)
					.onClick(
							TextActions.suggestCommand("/LC give "
									+ src.getName() + " "+"Crate " + key.toString()))
					.onHover(TextActions.showText(Text.of(text))).build());
		}
		contents.add(Text.builder("----------------------------------------------------")
            .build());
		for (Object key : keySett) {
          String text = config.getNode("LootCrate","LootKey", key)
                  .getString();
          contents.add(Text
                  .builder("Key of "+key.toString())
                  .color(TextColors.AQUA)
                  .onClick(
                          TextActions.suggestCommand("/LC give "
                                  + src.getName() + " "+"Key " + key.toString()))
                  .onHover(TextActions.showText(Text.of(text))).build());
      }
		PaginationService paginationService = Sponge.getServiceManager()
				.provide(PaginationService.class).get();

		paginationService
				.builder()
				.title(Text.builder("LootCrate").color(TextColors.YELLOW)
						.build()).contents(contents).padding(Text.of("-"))
				.sendTo(src);

		return CommandResult.success();
	}

}
