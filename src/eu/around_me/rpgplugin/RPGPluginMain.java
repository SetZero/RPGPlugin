package eu.around_me.rpgplugin;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import eu.around_me.rpgplugin.commands.AdminCMD;
import eu.around_me.rpgplugin.commands.ShowSkillMenu;
import eu.around_me.rpgplugin.commands.SidebarCommand;
import eu.around_me.rpgplugin.commands.SidebarDisplay;
import eu.around_me.rpgplugin.commands.Skillbind;
import eu.around_me.rpgplugin.libary.handlers.JoinHandler;
import eu.around_me.rpgplugin.libary.handlers.QuitHandler;
import eu.around_me.rpgplugin.libary.handlers.SkillbindHandler;
import eu.around_me.rpgplugin.libary.handlers.Timer;
import eu.around_me.rpgplugin.listeners.CombatCheck;
import eu.around_me.rpgplugin.listeners.ExpChange;
import eu.around_me.rpgplugin.listeners.MenuPlugin;
import eu.around_me.rpgplugin.listeners.PlayerDeath;
import eu.around_me.rpgplugin.listeners.PlayerJoin;
import eu.around_me.rpgplugin.listeners.PlayerQuit;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skillTrees.DefaultSkillTree;

public class RPGPluginMain extends JavaPlugin {
	FileConfiguration config = getConfig();
	Map<HumanEntity, RPGPlayerStat> playerStats = new HashMap<HumanEntity, RPGPlayerStat>();
	
	
	@Override
    public void onEnable() {
		DefaultSkillTree tree = new DefaultSkillTree(this, playerStats);
		
		ExpChange exp = new ExpChange(playerStats);
		PlayerJoin join = new PlayerJoin(playerStats, tree.getSkillTree(), this);
		PlayerQuit quit = new PlayerQuit(playerStats);
		
	    
		getServer().getPluginManager().registerEvents(exp, this);
		getServer().getPluginManager().registerEvents(join, this);
		getServer().getPluginManager().registerEvents(quit, this);
		

		this.getCommand("skillbind").setExecutor(new Skillbind(playerStats));
		this.getCommand("togglesidebar").setExecutor(new SidebarCommand(playerStats));
		this.getCommand("sidebardisplay").setExecutor(new SidebarDisplay(playerStats));
		this.getCommand("showskillmenu").setExecutor(new ShowSkillMenu(playerStats, this));
		this.getCommand("dbgcmd").setExecutor(new AdminCMD(playerStats));
		
		getServer().getPluginManager().registerEvents(new SkillbindHandler(playerStats), this);
		getServer().getPluginManager().registerEvents(new CombatCheck(playerStats), this);
		getServer().getPluginManager().registerEvents(new PlayerDeath(playerStats), this);
		

		MenuPlugin mainmenu = new MenuPlugin(playerStats, this);
		getServer().getPluginManager().registerEvents(mainmenu, this);
		
		new Timer(playerStats).runTaskTimer(this, 10, 20);
		
		for(Player p : Bukkit.getOnlinePlayers()){
			JoinHandler.registerPlayer(p, playerStats, tree.getSkillTree(), this);
		}
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
    	for(Player p : Bukkit.getOnlinePlayers()){
    		QuitHandler.playerQuit(playerStats, p);
    	}
    }
    
}
