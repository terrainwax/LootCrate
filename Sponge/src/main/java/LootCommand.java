import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;


public class LootCommand implements CommandExecutor {

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException {
		Player p = args.<Player>getOne("Player").get();
		String caseid = args.<String>getOne("Case").get();
		if(caseid == "test"){
			
			
		}else{
			Text errorText = Text.builder("Cette case n'existe pas").color(TextColors.RED).build();
			src.sendMessages(errorText);
		}
		
		
		return null;
	}

}
