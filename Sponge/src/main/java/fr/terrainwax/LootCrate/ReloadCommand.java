package fr.terrainwax.LootCrate;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class ReloadCommand implements CommandExecutor {

	private ConfigManager configManager;

	public ReloadCommand(ConfigManager configManager) {
		this.configManager = configManager;
	}

	@SuppressWarnings("static-access")
	@Override
	public CommandResult execute(CommandSource arg0, CommandContext arg1)
			throws CommandException {
		ConfigManager.getInstance().loadConfig();
		arg0.sendMessage(Text.of(TextColors.GREEN, "Success: ", TextColors.YELLOW, "The config was reloaded."));
		return CommandResult.success();
	}

}
