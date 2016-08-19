package eu.around_me.rpgplugin;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import eu.around_me.rpgplugin.libary.userfiles.FileHandler;
import eu.around_me.rpgplugin.listeners.ExpChange;
import eu.around_me.rpgplugin.listeners.MenuPlugin;
import eu.around_me.rpgplugin.listeners.PlayerJoin;
import eu.around_me.rpgplugin.listeners.PlayerQuit;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.scoreboard.Sidebar;
import eu.around_me.rpgplugin.skillTrees.DefaultSkillTree;

public class RPGPluginMain extends JavaPlugin {
	FileConfiguration config = getConfig();
	Map<HumanEntity, RPGPlayerStat> playerStats = new HashMap<HumanEntity, RPGPlayerStat>();
	
	
	@Override
    public void onEnable() {
		DefaultSkillTree tree = new DefaultSkillTree();
		
		ExpChange exp = new ExpChange(playerStats);
		PlayerJoin join = new PlayerJoin(playerStats, tree.getSkillTree(), this);
		PlayerQuit quit = new PlayerQuit(playerStats);
		
	    
		getServer().getPluginManager().registerEvents(exp, this);
		getServer().getPluginManager().registerEvents(join, this);
		getServer().getPluginManager().registerEvents(quit, this);
		

		MenuPlugin mainmenu = new MenuPlugin(playerStats, this);
		getServer().getPluginManager().registerEvents(mainmenu, this);
		
		for(Player p : Bukkit.getOnlinePlayers()){
			HumanEntity he = (HumanEntity)p;
			p.sendMessage(ChatColor.DARK_RED + p.getName() + ChatColor.YELLOW + " is now registered!");
			RPGPlayerStat stat = playerStats.get(he);

			System.out.println("Finished!");
			
			FileHandler fh = new FileHandler(playerStats.get(p), p, this);
			
			//Find Player (in Config Files)
			RPGPlayerStat ps;
			try {
				ps = fh.load(tree.getSkillTree());
			
				if(ps != null) {
					p.sendMessage(ChatColor.AQUA + "Welcome back, " + p.getName());
					playerStats.put(p, ps);
					
					Sidebar sb = new Sidebar(playerStats.get(p), p);
					playerStats.get(p).setSb(sb);
					return;
				}
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
			
			//Not found?
			if(stat == null) {
				p.sendMessage(ChatColor.AQUA + "Welcome new Player!");
				playerStats.put(p, new RPGPlayerStat(0, 0, 0, tree.getSkillTree(), fh));
			}
		}
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	
    }
    
}
