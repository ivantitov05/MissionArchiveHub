package MephiPackage.objects;

import MephiPackage.enums.EnemyMobility;
import MephiPackage.enums.EscalationRisk;
import java.util.ArrayList;
import java.util.List;

public class EnemyActivity {
    private String behaviorType;
    private List<String> targetPriority;
    private List<String> attackPatterns;
    private EnemyMobility mobility;
    private EscalationRisk escalationRisk;
    private List<String> countermeasuresUsed;

    public EnemyActivity() {
        this.targetPriority = new ArrayList<>();
        this.attackPatterns = new ArrayList<>();
        this.countermeasuresUsed = new ArrayList<>();
    }

    public List<String> getTargetPriority() { return targetPriority; }

    public void setTargetPriority(String targetPriority) {
        if (this.targetPriority == null) {
            this.targetPriority = new ArrayList<>();
        }
        this.targetPriority.clear();
        if (targetPriority != null && !targetPriority.isEmpty()) {
            this.targetPriority.add(targetPriority);
        }
    }

    public List<String> getAttackPatterns() { return attackPatterns; }

    public void setAttackPatterns(String attackPattern) {
        if (this.attackPatterns == null) {
            this.attackPatterns = new ArrayList<>();
        }
        this.attackPatterns.clear();
        if (attackPattern != null && !attackPattern.isEmpty()) {
            this.attackPatterns.add(attackPattern);
        }
    }

    public List<String> getCountermeasuresUsed() { return countermeasuresUsed; }
    public void setCountermeasuresUsed(List<String> countermeasuresUsed) {
        this.countermeasuresUsed = countermeasuresUsed;
    }

    public void setCountermeasuresUsed(String countermeasure) {
        if (this.countermeasuresUsed == null) {
            this.countermeasuresUsed = new ArrayList<>();
        }
        this.countermeasuresUsed.clear();
        if (countermeasure != null && !countermeasure.isEmpty()) {
            this.countermeasuresUsed.add(countermeasure);
        }
    }

    public String getBehaviorType() { return behaviorType; }
    public void setBehaviorType(String behaviorType) { this.behaviorType = behaviorType; }

    public EnemyMobility getMobility() { return mobility; }
    public void setMobility(EnemyMobility mobility) { this.mobility = mobility; }
    public void setMobility(String mobility) { this.mobility = EnemyMobility.fromString(mobility); }

    public EscalationRisk getEscalationRisk() { return escalationRisk; }
    public void setEscalationRisk(EscalationRisk escalationRisk) { this.escalationRisk = escalationRisk; }
    public void setEscalationRisk(String escalationRisk) {
        this.escalationRisk = EscalationRisk.fromString(escalationRisk);
    }
}