package MephiPackage.dto;

import java.util.List;

public class EnemyActivityDto {
    private String behaviorType;
    private List<String> targetPriority;
    private List<String> attackPatterns;
    private String mobility;
    private String escalationRisk;
    private List<String> countermeasuresUsed;

    public String getBehaviorType() {
        return behaviorType;
    }

    public void setBehaviorType(String behaviorType) {
        this.behaviorType = behaviorType;
    }

    public List<String> getTargetPriority() {
        return targetPriority;
    }

    public void setTargetPriority(List<String> targetPriority) {
        this.targetPriority = targetPriority;
    }

    public List<String> getAttackPatterns() {
        return attackPatterns;
    }

    public void setAttackPatterns(List<String> attackPatterns) {
        this.attackPatterns = attackPatterns;
    }

    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public String getEscalationRisk() {
        return escalationRisk;
    }

    public void setEscalationRisk(String escalationRisk) {
        this.escalationRisk = escalationRisk;
    }

    public List<String> getCountermeasuresUsed() {
        return countermeasuresUsed;
    }

    public void setCountermeasuresUsed(List<String> countermeasuresUsed) {
        this.countermeasuresUsed = countermeasuresUsed;
    }
}