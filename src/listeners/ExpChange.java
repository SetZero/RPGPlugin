package listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import net.md_5.bungee.api.ChatColor;
import playerstats.RPGPlayerStat;

/**
 * EXP Change listener, so that MC EXP and other EXP are indipendent
 * 
 * @author Sebastian
 *
 */
public class ExpChange implements Listener {

	private Map<HumanEntity, RPGPlayerStat> playerStats;
	// variable which increments after an levelup -- used to adjust the
	// expToLevelUp variable
	private int scale = 1;
	// variable to calculate the new expToLevelUp variable, after executing
	// levelUp()
	private int puffer = 7;

	public ExpChange(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}

	@EventHandler
	public void onPlayerExpChange(PlayerExpChangeEvent event) {
		HumanEntity p = (HumanEntity) event.getPlayer();
		RPGPlayerStat stat = playerStats.get(p);
		if (stat != null) {
			if (event.getAmount() > 0) {

				// set the new Exp with:
				stat.setEXP(stat.getEXP() + (int) (event.getAmount() / 2));

				// check for levelup:
				if (stat.getEXP() >= stat.getExpToLevelUp()) {
					event.getPlayer().sendMessage(ChatColor.GOLD + "Level Up!");
					// reset current exp and execute levelUp()
					if (stat.getExpToLevelUp() == 0)
						stat.setEXP(0);
					else
						stat.setEXP((stat.getEXP() % stat.getExpToLevelUp()));
					stat.levelUp();
					// set new expToLevelUp cap
					stat.setExpToLevelUp((int) ((scale * 32) * ((double) puffer / 8)
							- ((scale - 1) * 32) * (((double) puffer - 1) / 8)));
					scale++;

				}
			}
		}
	}
}
