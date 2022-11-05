package ts.plebe.us.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ts.plebe.us.Manager;
import ts.plebe.us.items.Caña;
import ts.plebe.us.items.Fuerza;
import ts.plebe.us.items.Huevito;
import ts.plebe.us.items.Merca;

public class AbilityCommand implements CommandExecutor {
  private Plugin plugin = (Plugin)Manager.getPlugin(Manager.class);
  
  private String usage = "/ability <type>";
  
  private Fuerza fuerza;
  
  private Huevito huevito;
  
  private Merca merca;
  
  private Caña caña;
  
  public AbilityCommand() {
    this.fuerza = new Fuerza();
    this.huevito = new Huevito();
    this.merca = new Merca();
    this.caña = new Caña();
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(ChatColor.GREEN + "You need be a player to execute this command.");
      return false;
    } 
    if (args.length == 0) {
      sender.sendMessage(ChatColor.GRAY + "------------------");
      sender.sendMessage(ChatColor.BLUE + "Ability Items" + ChatColor.GRAY + " || " + ChatColor.WHITE + "Help Command");
      sender.sendMessage(ChatColor.GRAY + "------------------");
      sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.BLUE + " EggPort " + ChatColor.WHITE + "/ability eggport");
      sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.RED + " Strength " + ChatColor.WHITE + "/ability fuerza");
      sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.YELLOW + " Cocaine " + ChatColor.WHITE + "/ability cocaine");
      sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.GREEN + " Grappling " + ChatColor.WHITE + "/ability grappling");
      return false;
    } 
    Player player = (Player)sender;
    if (!args[0].isEmpty()) {
      if (args[0].equalsIgnoreCase("list")) {
        sender.sendMessage(ChatColor.GRAY + "------------------");
        sender.sendMessage(ChatColor.BLUE + "Ability Items" + ChatColor.GRAY + " || " + ChatColor.WHITE + "Help Command");
        sender.sendMessage(ChatColor.GRAY + "------------------");
        sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.BLUE + " EggPort " + ChatColor.WHITE + "/ability eggport");
        sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.RED + " Strength " + ChatColor.WHITE + "/ability strength");
        sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.YELLOW + " Cocaine " + ChatColor.WHITE + "/ability cocaine");
        sender.sendMessage(ChatColor.WHITE + "*" + ChatColor.GREEN + " Grappling " + ChatColor.WHITE + "/ability grappling");
      } 
      if (args[0].equalsIgnoreCase("eggport")) {
        if (!(sender instanceof Player)) {
          sender.sendMessage(ChatColor.GREEN + "You need be a player to execute this command.");
          return false;
        } 
        this.huevito.giveItem(player);
        sender.sendMessage(ChatColor.GREEN + "You now have the ability.");
        return false;
      } 
      if (args[0].equalsIgnoreCase("grappling")) {
        if (!(sender instanceof Player)) {
          sender.sendMessage(ChatColor.GREEN + "You need be a player to execute this command.");
          return false;
        } 
        this.caña.giveItem(player);
        sender.sendMessage(ChatColor.GREEN + "You now have the ability.");
        return false;
      } 
      if (args[0].equalsIgnoreCase("cocaine")) {
        if (!(sender instanceof Player)) {
          sender.sendMessage(ChatColor.GREEN + "You need be a player to execute this command.");
          return false;
        } 
        this.merca.giveItem(player);
        sender.sendMessage(ChatColor.GREEN + "You now have the ability");
        return false;
      } 
      if (args[0].equalsIgnoreCase("strength")) {
        if (!(sender instanceof Player)) {
          sender.sendMessage(ChatColor.GREEN + "You need be a player to execute this command.");
          return false;
        } 
        this.fuerza.giveItem(player);
        sender.sendMessage(ChatColor.GREEN + "You now have the ability");
        return false;
      } 
      sender.sendMessage(ChatColor.RED + "Ups, that ability is not wroken. /Ability List");
      return false;
    } 
    return false;
  }
}
