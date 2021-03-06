package eu.around_me.rpgplugin.libary.handlers;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import eu.around_me.rpgplugin.skills.OverTimeSkill;
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;


public class SkillbindHandler implements Listener{
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public SkillbindHandler(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}


	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		RPGPlayerStat stat = playerStats.get(p);
		
	    Action a = e.getAction();
	    if(e.getClickedBlock() == null) return;
		//Location l = e.getClickedBlock().getLocation();
		
		if ((a == Action.PHYSICAL)  || (e.getItem() == null)) return;
		//if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
			Skill s = stat.getSkillbind(e.getItem().getType());
			if(s instanceof ActiveSkill) {
				//If over Time Skill add to the active ot skill list
				if(s instanceof OverTimeSkill) {
					OverTimeSkill as = (OverTimeSkill) s;
					if(stat.getCooldown(s) <= 0) {
						if(as.getManacost()*stat.getManaCostMulti() <= stat.getMana()) {
							if(!stat.getActiveOverTimeSkills().contains(s)) {
								p.sendMessage("Activated Skill " + s.getChatColor() + s.getName());
								stat.addActiveOverTimeSkills((OverTimeSkill)s);
								((OverTimeSkill) s).firstExecute(stat, p);
								((OverTimeSkill) s).setActivated(true);
							} else {
								p.sendMessage("Deactivated Skill " + s.getChatColor() + s.getName());
								stat.removeActiveOverTimeSkills((OverTimeSkill)s);
								((OverTimeSkill) s).setActivated(false);
								((OverTimeSkill) s).onDeactivation(stat, p);
							}
						} else {
							p.sendMessage("Not enough " + stat.getManabarcolor() + stat.getManaName());
						}
					}  else {
						p.sendMessage(as.getChatColor() + as.getName() + ChatColor.WHITE + " is not ready yet! (" + stat.getCooldown(as) + "s)");
					}
				} else {
					ActiveSkill as = (ActiveSkill) s;
					if(stat.getCooldown(s) <= 0) {
						if(as.getManacost()*stat.getManaCostMulti() <= stat.getMana()) {
							if(as.executeActive(stat, (HumanEntity)p)) {
								p.sendMessage("Called Skill: " + as.getChatColor() + as.getName());
								stat.setCooldowns(as, as.getCooldown());
								stat.setMana((int) (stat.getMana() - as.getManacost()*stat.getManaCostMulti()));
							} else {
								p.sendMessage(ChatColor.DARK_RED + "There was an error while executing this skill!");
							}
						} else {
							p.sendMessage("Not enough " + stat.getManabarcolor() + stat.getManaName());
						}
					} else {
						p.sendMessage(as.getChatColor() + as.getName() + ChatColor.WHITE + " is not ready yet! (" + stat.getCooldown(as) + "s)");
					}
				}
				if(stat.getManatype() == Manatypes.LIFE) {
					p.setHealth(stat.getMana());
				}
			}
		//} 
	}
	
	
}
