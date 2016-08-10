package eu.around_me.rpgplugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGPluginMain extends JavaPlugin {
	FileConfiguration config = getConfig();
	
	@Override
    public void onEnable() {
	    //getServer().getPluginManager().registerEvents(,this);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
    
}
