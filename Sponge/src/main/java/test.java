import java.util.Optional;

import javax.swing.text.Position;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.GameRegistry;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigManager;
import org.spongepowered.api.effect.particle.NoteParticle;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent.Join;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;

@Plugin(id = "example", name = "Example Project", version = "1.0")
public class test {
	@Inject
	private Logger logger;
	@Inject
	private Game game;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

        CommandSpec command2 = CommandSpec.builder()
            	    .description(Text.of("Hello World Command"))
            	    .permission("myplugin.command.helloworld")
            	    .executor(new command2())
            	    .build();

            	game.getCommandManager().register(this, command2, "ccc", "cc", "tesst");
        	
    }
    
}
