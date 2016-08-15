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
			
			//set the new Exp with:
			stat.setEXP(stat.getEXP() + (int) (event.getAmount()/2));
			
			//check for levelup
			if(stat.getEXP() >= stat.getExpToLevelUp()) {
				//reset exp and execute levelUp() -- without that fancy methods: exp%=expToLevelUp;
				stat.setEXP(stat.getEXP()%stat.getExpToLevelUp());
				stat.levelUp();
				
				//TODO: set new expToLevelUp cap!!!!
				//IDEAS: after executing levelUp() add a line (in levelUp()) to change the new expToLevelUp.
				//OR implement an algorithm which will be incremented every time setExpToLevelUp() is executed.
				//stat.setExpToLevelUp((stat.getExpToLevelUp()i*20)/3);
			}
		}
    }
}
