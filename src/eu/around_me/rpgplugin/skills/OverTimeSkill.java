package eu.around_me.rpgplugin.skills;

import org.bukkit.entity.HumanEntity;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;

public abstract class OverTimeSkill extends ActiveSkill {
	protected int tickTime = 10;
	protected int tickRemaining = tickTime;
	protected boolean manaCostOverTime = false;
	protected boolean activated = false;
	
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	public int getTickTime() {
		return tickTime;
	}
	public void setTickTime(int tick_time) {
		this.tickTime = tick_time;
	}
	public int getTickRemaining() {
		return tickRemaining;
	}
	public void setTickRemaining(int tick_remaining) {
		this.tickRemaining = tick_remaining;
	}
	public boolean isManaCostOverTime() {
		return manaCostOverTime;
	}
	public void setManaCostOverTime(boolean manaCostOverTime) {
		this.manaCostOverTime = manaCostOverTime;
	}
	
	public abstract void firstExecute(RPGPlayerStat stat, HumanEntity p);
	public abstract void onDeactivation(RPGPlayerStat stat, HumanEntity p);
	
}
