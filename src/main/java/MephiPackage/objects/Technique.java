package MephiPackage.objects;

import MephiPackage.enums.TechniquesType;

public class Technique {
    private String name;
    private TechniquesType type;
    private long damage;
    private String owner;

    public Technique() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TechniquesType getType() {
        return type;
    }

    public void setType(TechniquesType type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = TechniquesType.fromString(type);
    }

    public long getDamage() {
        return damage;
    }

    public void setDamage(long damage) {
        this.damage = damage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}