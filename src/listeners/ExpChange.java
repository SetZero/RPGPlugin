package listeners;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;

import playerstats.RPGPlayerStat;

/**
 * EXP Change listener, so that MC EXP and other EXP are indipendent
 * @author Sebastian
 *
 */
public class ExpChange implements Listener{

	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public ExpChange(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}
	 
	@EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event)
    {
		HumanEntity p = (HumanEntity)event.getPlayer();
		RPGPlayerStat stat = playerStats.get(p);
		if(stat != null) {
			if(event.getAmount() > 0) {
				stat.setEXP(stat.getEXP() + event.getAmount());
			}
		}
    }
}
