package SkillTrees;

import eu.around_me.rpgplugin.libary.AdjacencyMatrix;
import eu.around_me.rpgplugin.libary.SkillPoints;
import skills.PassiveSkillPoint;
import skills.Skill;

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
		};
		skillTree = new AdjacencyMatrix(skills.length);
		
		skillTree.addDblEdge(skills[1], skills[0].getID());
		skillTree.addDblEdge(skills[2], skills[0].getID());
		skillTree.addDblEdge(skills[3], skills[0].getID());
		skillTree.addDblEdge(skills[3], skills[1].getID());
		skillTree.addDblEdge(skills[4], skills[2].getID());
	}
	
	public AdjacencyMatrix getSkillTree() {
		return skillTree;
	}
	
	public void setSkillTree(AdjacencyMatrix skillTree) {
		this.skillTree = skillTree;
	}

}