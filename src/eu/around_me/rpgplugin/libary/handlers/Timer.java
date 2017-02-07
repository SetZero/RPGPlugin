package eu.around_me.rpgplugin.libary.handlers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.HumanEntity;
import org.bukkit.scheduler.BukkitRunnable;

import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.libary.ShieldRegenTypes;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.OverTimeSkill;
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class Timer extends BukkitRunnable{

	Map<HumanEntity, RPGPlayerStat> playerStats;
	private boolean refreshSidebar = false;
	
	public Timer(Map<HumanEntity, RPGPlayerStat> playerStats) {
		 this.playerStats = playerStats;
	}

	@Override
	public void run() {
		//System.out.println("Called!");
		Iterator<Entry<HumanEntity, RPGPlayerStat>> it = playerStats.entrySet().iterator();
		 while (it.hasNext()) {
			 Entry<HumanEntity, RPGPlayerStat> pair = it.next();
			 RPGPlayerStat stat = pair.getValue();
			 HumanEntity player = pair.getKey();
			 //Mana regen
			 //If player in has mana regenerate it...
			 if(stat.getManatype() == Manatypes.MANA) {
				 if(stat.getMaxmana() >= stat.getMana() + stat.getManaregen()) {
					 stat.setMana(stat.getMana() + stat.getManaregen());
					 refreshSidebar = true;
				 } else if(stat.getMaxmana() != stat.getMana()) {
					 stat.setMana(stat.getMaxmana());
				 }
			 } else if(stat.getManatype() == Manatypes.AGGRO) { //IF Player has aggro loose it if out of combat
				 if(stat.getOutofcombattimer() >= 20){
					 if(stat.getMana() - stat.getManaloss() >= 0) {
						 stat.setMana(stat.getMana() - stat.getManaloss());
						 refreshSidebar = true;
					 } 
				 }
			 }
			 
			 //Shield Regen
			 if(stat.getHasShield()) {
				 if(stat.getOutofcombattimer() >= 4){
					 if(stat.getShieldRegenType() == ShieldRegenTypes.OFFBATTLE) {
						 if(stat.getShield() + stat.getShieldRegen() < stat.getMaxShield()) {
							 int shieldAdd = (int) (stat.getMaxShield() * (stat.getShieldRegen() / 100D));
							 stat.setShield(stat.getShield() + shieldAdd);
							 refreshSidebar = true;
						 } else if(stat.getShield() != stat.getMaxShield()) {
							 stat.setShield(stat.getMaxShield());
							 refreshSidebar = true;
						 }
					 }
				 }
			 }
			 
			 //Out of Combat Timer
			 if(stat.getOutofcombattimer() <= 100) {
				 stat.setOutofcombattimer(stat.getOutofcombattimer() + 1);
			 }
			 
			 //Cooldown reset
		     Map<Skill, Integer> s = stat.getCooldowns();
		     if(s == null) return;
		     Iterator<Entry<Skill, Integer>> innerit = s.entrySet().iterator();
		     while (innerit.hasNext()) {
		    	 Entry<Skill, Integer> innerpair = innerit.next(); 
		    	 int newvalue = innerpair.getValue() - 1;
		    	 s.put(innerpair.getKey(), newvalue);
		    	 if(newvalue <= 0) {
		    		 s.remove(innerpair.getKey());
		    	 }
		     }
		     
		     //Over Time Skills
		     if(!stat.getActiveOverTimeSkills().isEmpty()) {
		    	 for(OverTimeSkill ots: stat.getActiveOverTimeSkills()) {
		    		 if(ots.isActivated()) {
		    			 if(ots.getTickRemaining() <= 0) {
		    				 ots.setTickRemaining(ots.getTickTime());
		    				 if(ots.isManaCostOverTime()) {
				    			 if(stat.getMana() >= ots.getManacost()) {
				    				 stat.setMana((int) (stat.getMana() - ots.getManacost()*stat.getManaCostMulti()));
				    				 ots.executeActive(stat, player);
				    			 } else {
				    				 player.sendMessage(ChatColor.WHITE + "Your skill " + ots.getChatColor() + ots.getName() + ChatColor.WHITE + " ran out because of low mana");
				    				 stat.removeActiveOverTimeSkills(ots);
				    				 ots.setActivated(false);
				    				 ots.onDeactivation(stat, player);
				    			 }
		    				 } else {
		    					 ots.executeActive(stat, player);
		    				 }
		    			 } else {
		    				 ots.setTickRemaining(ots.getTickRemaining() - 1);
		    			 }
		    		 }
		    	 }
		     }
		     
		     if(refreshSidebar) {
		    	 stat.getSb().sidebarRefresh();
		    	 refreshSidebar = false;
		     }
		 }
	}

}
