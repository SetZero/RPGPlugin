package eu.around_me.rpgplugin.skillTrees;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.libary.ShieldRegenTypes;
import eu.around_me.rpgplugin.libary.SkillPoints;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skilleffects.active.points.Shield;
import eu.around_me.rpgplugin.skilleffects.active.points.Taunt;
import eu.around_me.rpgplugin.skilleffects.active.points.Totem;
import eu.around_me.rpgplugin.skilleffects.passive.points.BraceFall;
import eu.around_me.rpgplugin.skilleffects.passive.points.EnergyShield;
import eu.around_me.rpgplugin.skilleffects.passive.points.ManaType;
import eu.around_me.rpgplugin.skills.PassiveSkillPoint;
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class DefaultSkillTree
{
	private AdjacencyMatrix skillTree;
	Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public DefaultSkillTree(Plugin p, Map<HumanEntity, RPGPlayerStat> playerStats)
	{
		this.playerStats = playerStats;
		//------------------// Passive Skills //----------------//
		
		//Brace Fall
		//PassiveSkillPoint BraceFall = ;
		//BraceFall.setName("Brace Fall");
		//BraceFall.setDescription("You don't take damage from Falls");
		//BraceFall.setChatColor(ChatColor.GOLD);
		
		//-----------------------------------------------------//
		
		
		//-----------------// Skill Array // -----------------//
		Skill[] skills = {
			new PassiveSkillPoint(SkillPoints.INT, null),
			new PassiveSkillPoint(SkillPoints.INT, null),
			new PassiveSkillPoint(SkillPoints.INT, null),
			new PassiveSkillPoint(SkillPoints.DEX, null),
			new PassiveSkillPoint(SkillPoints.STR, null),
			new PassiveSkillPoint(SkillPoints.STR, null),
			new PassiveSkillPoint(SkillPoints.STR, null),
			new PassiveSkillPoint(SkillPoints.DEX, null),
			new PassiveSkillPoint(SkillPoints.DEX, null),
			new Shield(p),
			new Taunt(p),
			new PassiveSkillPoint("Brace Fall", "You don't take damage from Falls", ChatColor.GOLD, new BraceFall(), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("RAGE!!", "You have Rage instead of Mana", ChatColor.DARK_RED, new ManaType(Manatypes.AGGRO, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield", "You can defend yourself with a energy shield", ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 2, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield ENHANCED", "You become better with the shield", ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 5, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield ENHANCED", "You become better with the shield", ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 5, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield ENHANCED", "You become better with the shield", ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 5, playerStats), SkillPoints.CUSTOM, null),
			new Totem(p),
			new PassiveSkillPoint("Into Battle!", "You regenerate your shield now by the damage you dealt", ChatColor.BLACK, new EnergyShield(ShieldRegenTypes.DAMAGE_DEALT, 5, playerStats), SkillPoints.CUSTOM, null),
		};
		
		//------------------// Skill Matrix //----------------//
		skillTree = new AdjacencyMatrix(skills.length);

		skillTree.addDblEdge(skills[0], skills[2].getID());
		skillTree.addDblEdge(skills[9], skills[0].getID());
		skillTree.addDblEdge(skills[10], skills[0].getID());
		skillTree.addDblEdge(skills[11], skills[0].getID());
		skillTree.addDblEdge(skills[12], skills[0].getID());
		skillTree.addDblEdge(skills[13], skills[0].getID());
		skillTree.addDblEdge(skills[17], skills[0].getID());
		skillTree.addDblEdge(skills[14], skills[13].getID());
		skillTree.addDblEdge(skills[15], skills[13].getID());
		skillTree.addDblEdge(skills[16], skills[13].getID());
		skillTree.addDblEdge(skills[18], skills[13].getID());
		skillTree.addDblEdge(skills[1], skills[0].getID());
		skillTree.addDblEdge(skills[2], skills[0].getID());
		skillTree.addDblEdge(skills[3], skills[0].getID());
		skillTree.addDblEdge(skills[3], skills[1].getID());
		skillTree.addDblEdge(skills[4], skills[2].getID());
		skillTree.addDblEdge(skills[4], skills[7].getID());
		skillTree.addDblEdge(skills[4], skills[6].getID());
		skillTree.addDblEdge(skills[4], skills[5].getID());
		skillTree.addDblEdge(skills[7], skills[8].getID());
		skillTree.addDblEdge(skills[8], skills[2].getID());
		skillTree.addDblEdge(skills[7], skills[4].getID());
		skillTree.addDblEdge(skills[6], skills[4].getID());
		skillTree.addDblEdge(skills[5], skills[4].getID());
	}
	
	public AdjacencyMatrix getSkillTree() {
		return skillTree;
	}
	
	public void setSkillTree(AdjacencyMatrix skillTree) {
		this.skillTree = skillTree;
	}

}