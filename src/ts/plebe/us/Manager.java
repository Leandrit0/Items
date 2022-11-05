package ts.plebe.us;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ts.plebe.us.commands.AbilityCommand;
import ts.plebe.us.items.Caña;
import ts.plebe.us.items.Fuerza;
import ts.plebe.us.items.Huevito;
import ts.plebe.us.items.Merca;
import ts.plebe.us.utils.Config;

public class Manager extends JavaPlugin {
  public Config config;
  
  public void onEnable() {
    Bukkit.getConsoleSender().sendMessage("Status: Activated.");
    this.config = new Config(this, "config", getDataFolder().getAbsolutePath());
    Bukkit.getPluginManager().registerEvents((Listener)new Fuerza(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new Merca(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new Huevito(), (Plugin)this);
    Bukkit.getPluginManager().registerEvents((Listener)new Caña(), (Plugin)this);
    getCommand("ability").setExecutor((CommandExecutor)new AbilityCommand());
  }
  
  public void onDisable() {
    Bukkit.getConsoleSender().sendMessage("Status: Desactived.");
  }
}
