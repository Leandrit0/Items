package ts.plebe.us.utils;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config extends YamlConfiguration {
  private File file;
  
  private String name;
  
  private String directory;
  
  public File getFile() {
    return this.file;
  }
  
  public void setFile(File file) {
    this.file = file;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getDirectory() {
    return this.directory;
  }
  
  public void setDirectory(String directory) {
    this.directory = directory;
  }
  
  public Config(JavaPlugin plugin, String name, String directory) {
    setName(name);
    setDirectory(directory);
    this.file = new File(directory, String.valueOf(name) + ".yml");
    if (!this.file.exists())
      plugin.saveResource(String.valueOf(name) + ".yml", false); 
    load();
    save();
  }
  
  public void load() {
    try {
      load(this.file);
    } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
      e.printStackTrace();
    } 
  }
  
  public void save() {
    try {
      save(this.file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public Configuration getConfiguration() {
    return (Configuration)this;
  }
  
  public void reloadConfiguration() {
    YamlConfiguration.loadConfiguration(this.file);
  }
}
