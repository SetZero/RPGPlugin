package eu.around_me.rpgplugin.listeners;

import java.util.Map;
import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.libary.ShieldRegenTypes;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import net.md_5.bungee.api.ChatColor;

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

	public void damageCheck(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			HumanEntity p = (HumanEntity) event.getEntity();
			RPGPlayerStat stat = playerStats.get(p);
			
			//Check if player exists
			if (stat != null) {
				
				//sync life with mana
				if(stat.getManatype() == Manatypes.LIFE) {
					stat.setMana((int) p.getHealth());
				}
				
				//If it was done by a Entity (Player, Mob, etc), do a knockback
				if(event instanceof EntityDamageByEntityEvent) {
					
					//Evasion
					if(stat.getEvasionRating() > 0) {
						Random r = new Random();
						//Success!
						if(stat.getEvasionRating() >= r.nextDouble()) {
							p.sendMessage(ChatColor.DARK_GREEN + "Evaded!");
							p.getWorld().playSound(p.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 3.0F, 0.5F);
							event.setDamage(0);
						}
					}
					
					EntityDamageByEntityEvent newEvent = (EntityDamageByEntityEvent) event;
					double distance = 0.5;
					Vector knockbackVector = newEvent.getDamager().getLocation().getDirection().multiply(distance *1.2).setY(distance);
			        p.setVelocity(knockbackVector);
				}
				
				//Check if player has a shield
				if(stat.getHasShield()) {
					//Does the Player have an OmniShield?
					if(!stat.isOmniShield()) {
						//don't shield on this damage causes
						switch(event.getCause()) {
							case FALL:
							case DROWNING:
							case STARVATION:
							case SUFFOCATION:
							case SUICIDE:
							case VOID:
							case LAVA:
								return;
							default:
								break;
						}
					} else {
						switch(event.getCause()) {
							case VOID:
								return;
							default:
								break;
						}
					}
					//Check if shield is up
					if(stat.getShield() > 0 && (int)event.getDamage() > 0) {
						//Check if shield has enough to bounce of the whole attack
						if(stat.getShield() > (int)event.getDamage()) {
							stat.setShield((int) (stat.getShield() - event.getDamage()));
						} else { 
							//Else do remove the rest from the healthbar
							double damage = event.getFinalDamage() * ((event.getDamage() - stat.getShield()) / event.getDamage());
							stat.setShield(0);
							if(p.getHealth() - damage > 0) {
								p.setHealth(p.getHealth() - damage);
							} else {
								p.setHealth(0.0);
							}
						}
						//refresh sidebar
						stat.getSb().sidebarRefresh();
						//Play a Damage sound
				        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 3.0F, 0.5F);
						event.setCancelled(true);
					}
				}
				stat.setOutofcombattimer(0);
			}
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player){
			HumanEntity p = (HumanEntity) event.getDamager();
			RPGPlayerStat stat = playerStats.get(p);
			if (stat != null) {
				//Crits
				if(event.getEntity() instanceof LivingEntity) {
					if(stat.getCritChance() > 0) {
						Random r = new Random();
						//Success!
						if(stat.getCritChance() >= r.nextDouble()) {
							p.sendMessage(ChatColor.RED + "Critical Hit!");
							double dmg = event.getDamage() * stat.getCritMultiplier();
							event.setDamage(dmg);
						}
					}
					//Mana Leech
					if(stat.getManaLeech() > 0) {
						stat.setMana(stat.getMana() + (int) (event.getDamage() * stat.getManaLeech()));
					}
					//regenerate aggro if player is in combat
					if(stat.getManatype() == Manatypes.AGGRO) {
						if(stat.getMaxmana() >= stat.getMana() + stat.getManaregen()) {
							stat.setMana(stat.getMana() + stat.getManaregen());
						}
					}
					
					//Regenerate Shield during Battle
					if(stat.getShieldRegenType() == ShieldRegenTypes.BATTLE) {
						if(stat.getShield() + stat.getShieldRegen() <= stat.getMaxShield()) {
							stat.setShield(stat.getShield() + stat.getShieldRegen());
						} else {
							stat.setShield(stat.getMaxShield());
						}
					}
					if(stat.getShieldRegenType() == ShieldRegenTypes.DAMAGE_DEALT) {
						double dmg = event.getFinalDamage();
						int regen = (int)(dmg / 2 * stat.getShieldRegen());
						if(stat.getShield() + regen <= stat.getMaxShield()) {
							stat.setShield(stat.getShield() + regen);
						} else {
							stat.setShield(stat.getMaxShield());
						}
					}
					
					
					stat.setOutofcombattimer(0);
					stat.getSb().sidebarRefresh();
				}
			}
		} 
		damageCheck(event);
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		damageCheck(event);
	}
}
