package eu.around_me.rpgplugin.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import eu.around_me.rpgplugin.playerstats.RPGPlayerStat;
import eu.around_me.rpgplugin.skills.ActiveSkill;
import eu.around_me.rpgplugin.skills.Skill;
import net.md_5.bungee.api.ChatColor;

public class Skillbind implements CommandExecutor{
	private Map<HumanEntity, RPGPlayerStat> playerStats;
	
	public Skillbind(Map<HumanEntity, RPGPlayerStat> playerStats) {
		this.playerStats = playerStats;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			RPGPlayerStat stat = playerStats.get((HumanEntity)p);
			if(args == null || args.length <= 0) {
				Material m = p.getEquipment().getItemInMainHand().getType();
				Skill removeSkill = stat.getSkillbind(m);
				if(removeSkill == null) {
					p.sendMessage("There is no skill bound to this item!");
					return true;
				}
				p.sendMessage(ChatColor.WHITE + "You removed the Skill " + removeSkill.getChatColor() + 
						removeSkill.getName() + ChatColor.WHITE + " from this item!");
				stat.removeSkillbind(m);
				return true;
			}
			
			List<Skill> skillList = stat.getLearnedSkills();
			for(Skill skill : skillList) {
				if(skill.getName().equalsIgnoreCase(args[0]) && skill instanceof ActiveSkill) {
					if(stat.getLearnedSkills().contains(skill)) {
						Material m = p.getEquipment().getItemInMainHand().getType();
						stat.addSkillbind(m, skill);
						p.sendMessage("Skill successfully bound to: " + m.name());
						return true;
					} else {
						p.sendMessage("You haven't learned this Skill yet!");
						return true;
					}
				}
			}
			p.sendMessage("Unknown Skill with name: " + args[0]);
			return true;
		}
		return false;
	}

}
