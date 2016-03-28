package fr.terrainwax.LootCrate;

import java.util.List;
import java.util.Optional;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import org.spongepowered.api.Sponge;


import org.spongepowered.api.entity.living.player.Player;

import com.google.common.reflect.TypeToken;

public class CommandRandom {
public  CommandRandom( Object key,Optional<Player> firstPlayer,CommentedConfigurationNode config){
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
}
