package eu.around_me.rpgplugin.skillTrees;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.SkillPoints;
import eu.around_me.rpgplugin.skills.PassiveSkillPoint;
import eu.around_me.rpgplugin.skills.Skill;

public class DefaultSkillTree
{
	private AdjacencyMatrix skillTree;
	
	public DefaultSkillTree()
	{
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
		};
		skillTree = new AdjacencyMatrix(skills.length);

		skillTree.addDblEdge(skills[0], skills[2].getID());
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