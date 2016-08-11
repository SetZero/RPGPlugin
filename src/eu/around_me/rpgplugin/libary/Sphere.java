package eu.around_me.rpgplugin.libary;
import java.util.List;

public class Sphere
{
	public static void main(String[] args) throws Exception
	{
		Skill[] skills = {
			new PassiveSkill(SkillPoints.INT),
			new PassiveSkill(SkillPoints.INT),
			new PassiveSkill(SkillPoints.INT),
			new PassiveSkill(SkillPoints.DEX),
		};
		AdjacencyMatrix am = new AdjacencyMatrix(skills.length);
		
		am.addDblEdge(skills[1], skills[0].getID());
		am.addDblEdge(skills[2], skills[0].getID());
		am.addDblEdge(skills[3], skills[0].getID());

		System.out.println("Connected Nodes to " + skills[0].getName() + ": ");
    	List<Skill> connectedNodes = am.inEdgesSkills(skills[0].getID());
    	for (Skill temp : connectedNodes) {
			System.out.println(temp.getName());
		}
	}
}