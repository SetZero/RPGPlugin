package eu.around_me.rpgplugin;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import SkillTrees.DefaultSkillTree;
import listeners.ExpChange;
import listeners.MenuPlugin;
import listeners.PlayerJoin;
import playerstats.RPGPlayerStat;

public class RPGPluginMain extends JavaPlugin {
	FileConfiguration config = getConfig();
	Map<HumanEntity, RPGPlayerStat> playerStats = new HashMap<HumanEntity, RPGPlayerStat>();
	
	
	@Override
    public void onEnable() {
		DefaultSkillTree tree = new DefaultSkillTree();
		
		ExpChange exp = new ExpChange(playerStats);
		PlayerJoin join = new PlayerJoin(playerStats, tree.getSkillTree());
		
	    
		getServer().getPluginManager().registerEvents(exp, this);
		getServer().getPluginManager().registerEvents(join, this);
		

		MenuPlugin mainmenu = new MenuPlugin(playerStats, this);
		getServer().getPluginManager().registerEvents(mainmenu, this);
		
		for(Player p : Bukkit.getOnlinePlayers()){
			HumanEntity he = (HumanEntity)p;
			p.sendMessage(ChatColor.DARK_RED + p.getName() + ChatColor.YELLOW + " is now registered!");
			RPGPlayerStat stat = playerStats.get(he);

			System.out.println("Finished!");
			
			if(stat == null) {
				p.sendMessage(ChatColor.AQUA + "Welcome new Player!");
				playerStats.put(p, new RPGPlayerStat(0, 0, 0, tree.getSkillTree()));
			}
		}
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	
    }
    
}
