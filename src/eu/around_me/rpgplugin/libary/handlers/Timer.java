package eu.around_me.rpgplugin.libary.handlers;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.HumanEntity;
import org.bukkit.scheduler.BukkitRunnable;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.Skill;

public class Timer extends BukkitRunnable{

	Map<HumanEntity, RPGPlayerStat> playerStats;
	
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
			 //Mana regen
			 if(stat.getMaxmana() >= stat.getMana() + stat.getManaregen()) {
				 stat.setMana(stat.getMana() + stat.getManaregen());
				 stat.getSb().sidebarRefresh();
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
		 }
	}

}
