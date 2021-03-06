package eu.around_me.rpgplugin.skillTrees;

import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.Manatypes;
import eu.around_me.rpgplugin.libary.ShieldRegenTypes;
import eu.around_me.rpgplugin.libary.SkillPoints;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skilleffects.active.overtime.points.ArrowFall;
import eu.around_me.rpgplugin.skilleffects.active.overtime.points.HealAllyOverTime;
import eu.around_me.rpgplugin.skilleffects.active.points.Shield;
import eu.around_me.rpgplugin.skilleffects.active.points.Taunt;
import eu.around_me.rpgplugin.skilleffects.active.points.Totem;
import eu.around_me.rpgplugin.skilleffects.passive.points.BraceFall;
import eu.around_me.rpgplugin.skilleffects.passive.points.CritChance;
import eu.around_me.rpgplugin.skilleffects.passive.points.EnergyShield;
import eu.around_me.rpgplugin.skilleffects.passive.points.EvasionChance;
import eu.around_me.rpgplugin.skilleffects.passive.points.HighCostEnergyShield;
import eu.around_me.rpgplugin.skilleffects.passive.points.ManaLeech;
import eu.around_me.rpgplugin.skilleffects.passive.points.ManaType;
import eu.around_me.rpgplugin.skilleffects.passive.points.StrongerArmor;
import eu.around_me.rpgplugin.skilleffects.passive.points.UndeadResistance;
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
			new PassiveSkillPoint("Brace Fall", "You take less fall damage",
					ChatColor.GOLD, new BraceFall(), SkillPoints.CUSTOM, null),															
			new PassiveSkillPoint("RAGE!!", "You have rage instead of Mana\n This regenerates while you are attacking enemies",			
					ChatColor.DARK_RED, new ManaType(Manatypes.AGGRO, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield", "You have an Energy Shield,\n which blocks up to 10 damage",
					ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 10, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield ENHANCED", "You increase your energy shield by 10",											
					ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 10, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield ENHANCED", "You increase your energy shield by 10",
					ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 10, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("EnergyShield ENHANCED", "You increase your energy shield by 10",
					ChatColor.DARK_PURPLE, new EnergyShield(ShieldRegenTypes.OFFBATTLE, 10, playerStats), SkillPoints.CUSTOM, null),
			new Totem(p),																												
			new PassiveSkillPoint("Into Battle!", "You regenerate your shield\n only by the damage you dealt",
					ChatColor.BLACK, new EnergyShield(ShieldRegenTypes.DAMAGE_DEALT, 5, playerStats), SkillPoints.CUSTOM, null), 		
			new PassiveSkillPoint("Risky Gamble", "You now have 1 Health,\n but you Energyshield is 20% more efficient\n and you get additional 20 Energy Shield",
					ChatColor.DARK_RED, new HighCostEnergyShield(playerStats, 0, 20), SkillPoints.CUSTOM, null),
			new HealAllyOverTime(),
			new ArrowFall(),
			new PassiveSkillPoint("Tough Armor", "You get 20% less total damage",
					ChatColor.AQUA, new StrongerArmor(p), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Undead Resistance", "You get 20% less damage from Undead Monsters",
					ChatColor.BLACK, new UndeadResistance(p), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Crit Master", "You get additional 1% Critical Hit Chance!",
					ChatColor.DARK_RED, new CritChance(0.05, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Crit Master", "You get additional 3% Critical Hit Chance!",
					ChatColor.DARK_RED, new CritChance(0.02, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Crit Master", "You get additional 3% Critical Hit Chance!",
					ChatColor.DARK_RED, new CritChance(0.02, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Evasion Master", "You get additional 1% Evasion Rating!",
					ChatColor.DARK_GREEN, new EvasionChance(0.05, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Evasion Master", "You get additional 3% Evasion Rating!",
					ChatColor.DARK_GREEN, new EvasionChance(0.02, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Evasion Master", "You get additional 3% Evasion Rating!",
					ChatColor.DARK_GREEN, new EvasionChance(0.02, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Mana Leech", "You get additional 10% Mana Leech!",
					ChatColor.AQUA, new ManaLeech(0.1, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Mana Leech", "You get additional 5% Mana Leech!",
					ChatColor.AQUA, new ManaLeech(0.05, playerStats), SkillPoints.CUSTOM, null),
			new PassiveSkillPoint("Mana Leech", "You get additional 5% Mana Leech!",
					ChatColor.AQUA, new ManaLeech(0.05, playerStats), SkillPoints.CUSTOM, null),	
			//new PassiveSkillPoint("Blood Magic", "Removes your Mana. You now spend Life instead",			//gliched af!
			//		ChatColor.RED, new ManaType(Manatypes.LIFE, playerStats), SkillPoints.CUSTOM, null),
		};
		
		//------------------// Skill Matrix //----------------//
		skillTree = new AdjacencyMatrix(skills.length);

		skillTree.addDblEdge(skills[0], skills[2]);
		skillTree.addDblEdge(skills[9], skills[0]);
		skillTree.addDblEdge(skills[10], skills[0]);
		skillTree.addDblEdge(skills[11], skills[0]);
		skillTree.addDblEdge(skills[12], skills[0]);
		skillTree.addDblEdge(skills[13], skills[0]);
		skillTree.addDblEdge(skills[17], skills[0]);
		skillTree.addDblEdge(skills[20], skills[0]);
		skillTree.addDblEdge(skills[21], skills[0]);
		skillTree.addDblEdge(skills[22], skills[0]);
		skillTree.addDblEdge(skills[23], skills[0]);
		skillTree.addDblEdge(skills[24], skills[0]);
		skillTree.addDblEdge(skills[25], skills[24]);
		skillTree.addDblEdge(skills[26], skills[24]);
		skillTree.addDblEdge(skills[27], skills[0]);
		skillTree.addDblEdge(skills[28], skills[27]);
		skillTree.addDblEdge(skills[29], skills[27]);
		skillTree.addDblEdge(skills[30], skills[0]);
		skillTree.addDblEdge(skills[31], skills[30]);
		skillTree.addDblEdge(skills[32], skills[30]);
		//skillTree.addDblEdge(skills[33], skills[0]);
		skillTree.addDblEdge(skills[14], skills[13]);
		skillTree.addDblEdge(skills[15], skills[13]);
		skillTree.addDblEdge(skills[16], skills[13]);
		skillTree.addDblEdge(skills[18], skills[13]);
		skillTree.addDblEdge(skills[19], skills[13]);
		skillTree.addDblEdge(skills[1], skills[0]);
		skillTree.addDblEdge(skills[2], skills[0]);
		skillTree.addDblEdge(skills[3], skills[0]);
		skillTree.addDblEdge(skills[3], skills[1]);
		skillTree.addDblEdge(skills[4], skills[2]);
		skillTree.addDblEdge(skills[4], skills[7]);
		skillTree.addDblEdge(skills[4], skills[6]);
		skillTree.addDblEdge(skills[4], skills[5]);
		skillTree.addDblEdge(skills[7], skills[8]);
		skillTree.addDblEdge(skills[8], skills[2]);
		skillTree.addDblEdge(skills[7], skills[4]);
		skillTree.addDblEdge(skills[6], skills[4]);
		skillTree.addDblEdge(skills[5], skills[4]);
	}
	
	public AdjacencyMatrix getSkillTree() {
		return skillTree;
	}
	
	public void setSkillTree(AdjacencyMatrix skillTree) {
		this.skillTree = skillTree;
	}

}