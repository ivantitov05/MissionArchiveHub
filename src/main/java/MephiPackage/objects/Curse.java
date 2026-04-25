package MephiPackage.objects;

import MephiPackage.enums.ThreatLevel;

public class Curse {
    private String name;
    private ThreatLevel threatLevel;

    public Curse() {}

    public Curse(String name, ThreatLevel threatLevel) {
        this.name = name;
        this.threatLevel = threatLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThreatLevel getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(ThreatLevel threatLevel) {
        this.threatLevel = threatLevel;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = ThreatLevel.fromString(threatLevel);
    }
}