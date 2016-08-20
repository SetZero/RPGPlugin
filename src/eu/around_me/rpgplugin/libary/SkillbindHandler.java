package eu.around_me.rpgplugin.libary;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import eu.around_me.rpgplugin.skills.Skill;


public class SkillbindHandler implements Listener{
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public SkillbindHandler(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}


	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		RPGPlayerStat stat = playerStats.get(p);
		
	    Action a = e.getAction();
	    if(e.getClickedBlock() == null) return;
		Location l = e.getClickedBlock().getLocation();
		
		if ((a == Action.PHYSICAL) || (e.getItem() == null)) return;
		if(a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
			Skill s = stat.getSkillbind(e.getItem().getType());
			if(s instanceof ActiveSkill) {
				ActiveSkill as = (ActiveSkill) s;
				as.executeActive(stat, (HumanEntity)p);
				p.sendMessage("Called Skill: " + as.getChatColor() + as.getName());
			}
		} 
	}
	
	
}
