package fr.terrainwax.LootCrate;

import java.util.List;
import java.util.Optional;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import com.google.common.reflect.TypeToken;

public class ItemRandom {

  public ItemRandom(Object key, Optional<Player> firstPlayer, CommentedConfigurationNode config) {
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
