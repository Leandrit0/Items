package ts.plebe.us.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ts.plebe.us.Manager;
import ts.plebe.us.utils.ItemStackBuilder;

public class Huevito implements Listener {
  private Plugin plugin = (Plugin)Manager.getPlugin(Manager.class);
  
  HashMap<String, Long> cooldowns = new HashMap<>();
  
  int cooldownTime = 60;
  
  private List<String> lore;
  
  static List<String> huevitoLore = new ArrayList<>();
  
  static {
    huevitoLore.add(ChatColor.translateAlternateColorCodes('&', "&eEggPort"));
    huevitoLore.add(ChatColor.translateAlternateColorCodes('&', "&eCooldown: 1m"));
  }
  
  public static ItemStack huevito = ItemStackBuilder.get(Material.EGG, 1, (short)0, "&6Egg Port.", huevitoLore);
  
  @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
  public void onLaunch(ProjectileLaunchEvent event) {
    if (event.getEntity().getShooter() instanceof Player) {
      Player player = (Player)event.getEntity().getShooter();
      ItemStack stack = player.getItemInHand();
      if (stack != null && 
        stack.isSimilar(huevito))
        if (!this.cooldowns.containsKey(player.getName())) {
          player.sendMessage(ChatColor.BLUE + "You have used the egg port.");
          this.cooldowns.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
        } else {
          long secondsLeft = ((Long)this.cooldowns.get(player.getName())).longValue() / 1000L + this.cooldownTime - System.currentTimeMillis() / 1000L;
          if (secondsLeft > 0L) {
            player.sendMessage(ChatColor.YELLOW + "You have cooldown for another " + secondsLeft + " seconds!");
            event.setCancelled(true);
          } 
        }  
    } 
  }
  
  @EventHandler
  public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    if (event.getDamager() instanceof Egg) {
      Projectile projectile = (Projectile)event.getDamager();
      if (!(projectile.getShooter() instanceof Player))
        return; 
      if (event.getEntity() instanceof Player) {
        Egg egg = (Egg)event.getDamager();
        Player eggshooter = (Player)egg.getShooter();
        Location shooterLoc = eggshooter.getLocation();
        Player eggdamagedplayer = (Player)event.getEntity();
        if ((int)eggdamagedplayer.getLocation().distance(eggshooter.getLocation()) <= this.plugin.getConfig().getInt("Switcher-Egg.Range-of-Blocks")) {
          eggshooter.teleport(event.getEntity().getLocation());
          eggdamagedplayer.teleport(shooterLoc);
          eggdamagedplayer.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Switcher-Egg.DamagedPlayer-Message")).replace("%shooter%", eggshooter.getName()));
          eggshooter.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Switcher-Egg.Shooter-Message")).replace("%damagedplayer%", eggdamagedplayer.getName()));
          if (eggdamagedplayer.getLocation().distance(eggshooter.getLocation()) <= 2.0D)
            eggshooter.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Switcher-Egg.No-Range-Message")).replace("%damagedplayer%", eggdamagedplayer.getName())); 
        } else {
          eggshooter.sendMessage(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("Switcher-Egg.No-Range-Message")).replace("%damagedplayer%", eggdamagedplayer.getName()));
        } 
      } 
    } 
  }
  
  public void giveItem(Player player) {
    player.getInventory().addItem(new ItemStack[] { huevito });
  }
}
