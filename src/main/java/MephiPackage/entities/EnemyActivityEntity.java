package MephiPackage.entities;

import MephiPackage.enums.EnemyMobility;
import MephiPackage.enums.EscalationRisk;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enemy_activities")
public class EnemyActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // ← исправлено: Id → id

    @Column(name = "behavior_type")
    private String behaviorType;

    @ElementCollection
    @CollectionTable(name = "enemy_target_priorities",
            joinColumns = @JoinColumn(name = "enemy_activity_id"))
    @Column(name = "target_priority")
    private List<String> targetPriority = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "enemy_attack_patterns",
            joinColumns = @JoinColumn(name = "enemy_activity_id"))
    @Column(name = "attack_pattern")
    private List<String> attackPatterns = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private EnemyMobility mobility;

    @Enumerated(EnumType.STRING)
    @Column(name = "escalation_risk")
    private EscalationRisk escalationRisk;

    @ElementCollection
    @CollectionTable(name = "enemy_countermeasures",
            joinColumns = @JoinColumn(name = "enemy_activity_id"))
    @Column(name = "countermeasure")
    private List<String> countermeasuresUsed = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "mission_id", unique = true)
    private MissionEntity mission;

    public EnemyActivityEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBehaviorType() { return behaviorType; }
    public void setBehaviorType(String behaviorType) { this.behaviorType = behaviorType; }

    public List<String> getTargetPriority() { return targetPriority; }
    public void setTargetPriority(List<String> targetPriority) { this.targetPriority = targetPriority; }

    public List<String> getAttackPatterns() { return attackPatterns; }
    public void setAttackPatterns(List<String> attackPatterns) { this.attackPatterns = attackPatterns; }

    public EnemyMobility getMobility() { return mobility; }
    public void setMobility(EnemyMobility mobility) { this.mobility = mobility; }

    public EscalationRisk getEscalationRisk() { return escalationRisk; }
    public void setEscalationRisk(EscalationRisk escalationRisk) { this.escalationRisk = escalationRisk; }

    public List<String> getCountermeasuresUsed() { return countermeasuresUsed; }
    public void setCountermeasuresUsed(List<String> countermeasuresUsed) { this.countermeasuresUsed = countermeasuresUsed; }

    public MissionEntity getMission() { return mission; }
    public void setMission(MissionEntity mission) { this.mission = mission; }
}