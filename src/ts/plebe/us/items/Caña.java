package ts.plebe.us.items;

import java.util.ArrayList;
import java.util.HashMap;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import ts.plebe.us.Manager;
import ts.plebe.us.utils.ItemStackBuilder;

public class Caña implements Listener {
  HashMap<String, Long> cooldown = new HashMap<>();
  
  int cooldowntime = 15;
  
  public static Plugin plugin = (Plugin)Manager.getPlugin(Manager.class);
  
  public static ArrayList<String> lore = new ArrayList<>();
  
  public static ItemStack ca = ItemStackBuilder.get(Material.FISHING_ROD, 1, (short)0, "&eGrappling Hook.", lore);
  
  public boolean isPlayerOnCooldown(Player player) {
    return this.cooldown.containsKey(player);
  }
  
  public Long removeCooldown(Player player) {
    return this.cooldown.remove(player.getName());
  }
  
  public long getRemaining(Player player) {
    long secondsLeft = ((Long)this.cooldown.get(player.getName())).longValue() / 1000L + this.cooldowntime - System.currentTimeMillis() / 1000L;
    return secondsLeft;
  }
  
  @EventHandler
  public void onFish(PlayerFishEvent e) {
    Player player = e.getPlayer();
    ItemStack item = player.getItemInHand();
    if (!item.isSimilar(ca))
      return; 
    if (this.cooldown.containsKey(player.getUniqueId())) {
      player.sendMessage(ChatColor.BLUE + "You have cooldown for another " + getRemaining(player) + " seconds.");
      return;
    } 
    if (!e.getHook().isValid())
      return; 
    if (item.isSimilar(ca)) {
      player.getLocation().add(0.0D, 2.0D, 0.0D);
      Vector vector = new Vector(e.getHook().getLocation().getBlockX(), 0, e.getHook().getLocation().getBlockZ());
      player.setVelocity(vector);
      this.cooldown.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
    } 
  }
  
  public void giveItem(Player player) {
    player.getInventory().addItem(new ItemStack[] { ca });
  }
}
