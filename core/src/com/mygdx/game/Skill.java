package com.mygdx.game;

// Assessment 3 - Removed unused imports (change 6)

/**
 * An individual skill, instantiated and stored in the SkillManager.
 * Implements Json.Serializable to allow objects to be easily generated.
 */
public class Skill{

    private int ID = -1;
    private String name, description;
    private SkillType skillType;
    private int basePower, MPCost;


    public Skill(String name, String description, SkillType skillType, int basePower, int MPCost) {
        this.name = name;
        this.description = description;
        this.skillType = skillType;
        this.basePower = basePower;
        this.MPCost = MPCost;
    }

    /**
     * Empty constructor required for class to be generated by deserialising json.
     */
    public Skill(){}

    public void updateID(int id){
        this.ID = id;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", skillType=" + skillType +
                ", basePower=" + basePower +
                ", MPCost=" + MPCost +
                '}';
    }

    public enum SkillType{
        MELEE,RANGED,MAGIC,HEAL

    }

    public int getBasePower() {
        return basePower;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public int getMPCost() {
        return MPCost;
    }
}
