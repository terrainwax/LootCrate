package fr.terrainwax.LootCrate;

import java.util.List;
import java.util.Optional;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.reflect.TypeToken;

public class CommandGive {

  public CommandGive(Object key, Optional<Player> firstPlayer, CommentedConfigurationNode config) {
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
  }

}
