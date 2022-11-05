package ts.plebe.us.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ts.plebe.us.Manager;
import ts.plebe.us.utils.ItemStackBuilder;

public class Merca implements Listener {
  HashMap<String, Long> cooldowns = new HashMap<>();
  
  int cooldownTime = 120;
  
  private static Plugin plugin = (Plugin)Manager.getPlugin(Manager.class);
  
  public static List<String> mercaLore = new ArrayList<>();
  
  public static ItemStack merca = ItemStackBuilder.get(Material.SUGAR, 1, (short)0, "&aMerca", mercaLore);
  
  static {
    mercaLore.add(ChatColor.BLUE + "Effectes:");
    mercaLore.add(ChatColor.BLUE + " Speed 3, 6 seconds");
    mercaLore.add(ChatColor.BLUE + " Regeneration 2, 5 seconds");
    mercaLore.add(ChatColor.BLUE + " Resistance 2, 5 seconds");
    mercaLore.add(ChatColor.BLUE + " Blindness 2, 5 seconds");
    mercaLore.add(ChatColor.BLUE + " Cooldown: 2 minutes");
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    Long left = this.cooldowns.get(p.getName());
    ItemStack stack = e.getItem();
    if (!e.getAction().equals(Action.PHYSICAL) && e.getItem() != null && e.getItem().isSimilar(merca))
      if (!this.cooldowns.containsKey(p.getName())) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 2));
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
        p.sendMessage(ChatColor.BLUE + "You have used cocaine and now have cooldown.");
        this.cooldowns.put(p.getName(), Long.valueOf(System.currentTimeMillis()));
        if (stack.getAmount() > 1) {
          stack.setAmount(stack.getAmount() - 1);
        } else {
          p.setItemInHand(new ItemStack(Material.AIR, 1));
        } 
      } else {
        long secondsLeft = ((Long)this.cooldowns.get(p.getName())).longValue() / 1000L + this.cooldownTime - System.currentTimeMillis() / 1000L;
        if (secondsLeft > 0L)
          p.sendMessage(ChatColor.YELLOW + "You have cooldown for another " + secondsLeft + " seconds!"); 
      }  
  }
  
  public void giveItem(Player player) {
    player.getInventory().addItem(new ItemStack[] { merca });
  }
}
