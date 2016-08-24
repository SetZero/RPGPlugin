package eu.around_me.rpgplugin.listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.libary.ShieldRegenTypes;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

/**
 * Combat Check (for AGGRO and Shield)
 * 
 * @author Sebastian
 *
 */
public class CombatCheck implements Listener {

	private Map<HumanEntity, RPGPlayerStat> playerStats;

	public CombatCheck(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}

	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player){
			HumanEntity p = (HumanEntity) event.getDamager();
			RPGPlayerStat stat = playerStats.get(p);
			if (stat != null) {
				//regenerate aggro if player is in combat
				if(stat.getManatype() == Manatypes.AGGRO) {
					if(stat.getMaxmana() >= stat.getMana() + stat.getManaregen()) {
						stat.setMana(stat.getMana() + stat.getManaregen());
						stat.getSb().sidebarRefresh();
					}
				}
				
				//Regenerate Shield during Battle
				if(stat.getShieldRegenType() == ShieldRegenTypes.BATTLE) {
					stat.setShield(stat.getShield() + stat.getShieldRegen());
				}
				
				//Shield event
				if(stat.getHasShield()) {
					if(stat.getShield() > 0) {
						if(stat.getShield() > event.getDamage()) {
							stat.setShield((int) (stat.getShield() - event.getDamage()));
						} else {
							double damage = event.getDamage() - stat.getShield();
							stat.setShield(0);
							p.setHealth(p.getHealth() - damage);
						}
						event.setCancelled(true);
						stat.getSb().sidebarRefresh();
					}
				}
				stat.setOutofcombattimer(0);
			}
		}
	}
}
