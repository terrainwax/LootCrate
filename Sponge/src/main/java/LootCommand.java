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
		Player player = args.<Player>getOne("player").get();
        String caseid = args.<String>getOne("case").get();
		if(caseid == "test"){
			int i = (int) Math.floor(Math.random() * 101);
			if(i < 90){
			src.sendMessages(Text.of("test"));
			}else if(i > 90){
				src.sendMessages(Text.of("bravo tu a eu 10% de chance"));
			}
			return CommandResult.success();
		}else{
			Text errorText = Text.builder("Cette case n'existe pas").color(TextColors.RED).build();
			src.sendMessages(errorText);
			return CommandResult.success();
		}
		
		
		
	}

}
