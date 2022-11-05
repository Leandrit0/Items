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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ts.plebe.us.utils.ItemStackBuilder;

public class Fuerza implements Listener {
  HashMap<String, Long> cooldowns = new HashMap<>();
  
  int cooldownTime = 120;
  
  static List<String> fuerzaLore = new ArrayList<>();
  
  static {
    fuerzaLore.add(ChatColor.translateAlternateColorCodes('&', "&eStrength Portable"));
    fuerzaLore.add(ChatColor.translateAlternateColorCodes('&', "&eCooldown: 2m"));
  }
  
  public static ItemStack fuerza = ItemStackBuilder.get(Material.BLAZE_POWDER, 1, (short)0, "&6Strength Portable.", fuerzaLore);
  
  @EventHandler
  public void onInteract(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    Long left = this.cooldowns.get(p.getName());
    ItemStack stack = e.getItem();
    if (!e.getAction().equals(Action.PHYSICAL) && e.getItem() != null && e.getItem().isSimilar(fuerza))
      if (!this.cooldowns.containsKey(p.getName())) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
        p.sendMessage(ChatColor.BLUE + "You now have cooldown for 2m.");
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
    player.getInventory().addItem(new ItemStack[] { fuerza });
  }
}
