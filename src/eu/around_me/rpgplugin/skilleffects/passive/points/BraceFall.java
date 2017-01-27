package eu.around_me.rpgplugin.skilleffects.passive.points;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class BraceFall extends PassiveSkillEffects implements Listener{

	Plugin plugin;
	HumanEntity p;
	
	private double chance = 100;
	private double percentage = 50;
	private double soak = 8;
	
	public BraceFall() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		this.plugin = plugin;
		this.p = p;
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public void unloadEffect(HumanEntity p) {
		HandlerList.unregisterAll(this);
	}
	
	@EventHandler
	public void doDmg(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			if (event.getCause() != DamageCause.FALL) { return; }
			//Player p = (Player) event.getEntity();
			
			// load skill options

			double chance = ((Double) this.chance) / 100.0D;
			if (Math.random() <= chance) {

				double percent = ((Double) percentage) / 100.0D;
				
				double dmg = event.getDamage();
				
				dmg = dmg - soak;
				dmg = dmg * (1.0-percent);
				if (dmg<0) {
					event.setCancelled(true);
					dmg=0;
				}
				event.setDamage(dmg);				
			}
		}
	}

}
