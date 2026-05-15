package MephiPackage.objects;

import MephiPackage.entities.CurseEntity;
import MephiPackage.enums.ThreatLevel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurseEntity> curses = new ArrayList<>();

    public List<CurseEntity> getCurses() { return curses; }
    public void setCurses(List<CurseEntity> curses) { this.curses = curses; }


}