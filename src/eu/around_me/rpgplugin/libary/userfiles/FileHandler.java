package eu.around_me.rpgplugin.libary.userfiles;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.Skill;

public class FileHandler {

	//private HumanEntity p;
	private FileConfiguration playerData;
	private RPGPlayerStat stats;
	private File f;
	
	public FileHandler(RPGPlayerStat stats, HumanEntity p, Plugin plugin) {
		//this.p = p;
		this.stats = stats;
		
		File pluginDataFolder = Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder();
		File userdata = new File (pluginDataFolder, File.separator + "PlayerDatabase");
		System.out.println(userdata);
		File f = new File(userdata, File.separator + p.getName() + ".yml");
		FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
		this.playerData = playerData;
		try {
			if (!f.exists()) {
				playerData.createSection("skills");
	            playerData.createSection("skills.passive");
	            playerData.set("skills.passive.int", 0);
	            playerData.set("skills.passive.dex", 0);
	            playerData.set("skills.passive.str", 0);
	           
	            playerData.createSection("level");
	            playerData.set("level.level", 0);
	            playerData.set("level.next", 0);
	            playerData.set("level.exp", 0);
	            
	
	            playerData.createSection("learned");
	            playerData.save(f);
			 } 
		} catch (IOException exception) {
				 exception.printStackTrace();
             }

		this.f = f;
	}

	
	public void setStats(RPGPlayerStat stats) {
		this.stats = stats;
	}
	
	public void save() {
		//TODO: FIXME
        try {
			//playerData.set("skills.passive.int", 0);
            playerData.set("skills.passive.int", stats.getWis());
            playerData.set("skills.passive.dex", stats.getDex());
            playerData.set("skills.passive.str", stats.getStr());
           
            playerData.set("level.level", stats.getLevel());
            playerData.set("level.next", stats.getExpToLevelUp());
            playerData.set("level.exp", stats.getEXP());
            playerData.set("level.skillpoints", stats.getSkillpoints());
            
            for(Skill s: stats.getLearnedSkills()) {
            	playerData.set("learned." + s.getID(), s.getID());
            }
           
            playerData.save(f);
        } catch (IOException exception) {
        	System.out.println("IO error!");
        	//exception.printStackTrace();
        }
	}

	
	public RPGPlayerStat load(AdjacencyMatrix skillTree) throws InvalidConfigurationException {
		if (f.exists()) {
			try {
				playerData.load(f);
				int wis = playerData.getInt("skills.passive.int");
				int dex = playerData.getInt("skills.passive.dex");
				int str = playerData.getInt("skills.passive.str");
				
				int lvl = playerData.getInt("level.level");
				int next = playerData.getInt("level.next");
				int exp = playerData.getInt("level.exp");
				int skillpoints = playerData.getInt("level.skillpoints");
				
				List<Integer> l = playerData.getIntegerList("learned");
				
				
				RPGPlayerStat stat = new RPGPlayerStat(str, dex, wis, skillTree, this);
				stat.setEXP(exp);
				stat.setLevel(lvl);
				stat.setExpToLevelUp(next);
				stat.setSkillpoints(skillpoints);
				
				for(int i: l) {
					stat.learnByID(i);
				}
				
				return stat;
			} catch (IOException exception) {
				exception.printStackTrace();
	        }
		}
		return null;
	}
}
