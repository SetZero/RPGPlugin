package eu.around_me.rpgplugin.skilleffects.passive.points;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.skills.PassiveSkillEffects;

public class StrongerArmor extends PassiveSkillEffects implements Listener{

	private HumanEntity player;
	private double modificator = 0.8;
	
	public StrongerArmor(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public StrongerArmor(Plugin plugin, double modificator) {
		this(plugin);
		this.modificator = modificator;
	}
	
	@Override
	public void executeEffect(HumanEntity p, Plugin plugin) {
		this.player = p;
	}

	@Override
	public void unloadEffect(HumanEntity p) {
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if(player != null) {
			if(event.getEntity() == player) {
				double dmg = event.getDamage();
				dmg *= modificator;
				event.setDamage(dmg);
			}
		}
	}
	
}
