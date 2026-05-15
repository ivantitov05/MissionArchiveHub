package MephiPackage.entities;

import MephiPackage.enums.Outcome;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "missions")
public class MissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mission_id", unique = true, nullable = false)
    private String missionId;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Outcome outcome;

    @Column(name = "damage_cost")
    private long damageCost;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurseEntity> curses = new ArrayList<>();

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SorcererEntity> sorcerers = new ArrayList<>();

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechniqueEntity> techniques = new ArrayList<>();

    @OneToOne(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private EconomicAssessmentEntity economicAssessment;

    @OneToOne(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private CivilianImpactEntity civilianImpact;

    @OneToOne(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnemyActivityEntity enemyActivity;

    @OneToOne(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnvironmentConditionsEntity environmentConditions;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperationTimelineEntity> operationTimeline = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_operation_tags",
            joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "tag")
    private List<String> operationTags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_support_units",
            joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "unit")
    private List<String> supportUnits = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_recommendations",
            joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "recommendation", columnDefinition = "TEXT")
    private List<String> recommendations = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_artifacts",
            joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "artifact")
    private List<String> artifactsRecovered = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_evacuation_zones",
            joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "zone")
    private List<String> evacuationZones = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "mission_status_effects",
            joinColumns = @JoinColumn(name = "mission_id"))
    @Column(name = "effect")
    private List<String> statusEffects = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }

    public long getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(long damageCost) {
        this.damageCost = damageCost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<CurseEntity> getCurses() {
        return curses;
    }

    public void setCurses(List<CurseEntity> curses) {
        this.curses = curses;
    }

    public List<SorcererEntity> getSorcerers() {
        return sorcerers;
    }

    public void setSorcerers(List<SorcererEntity> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public List<TechniqueEntity> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<TechniqueEntity> techniques) {
        this.techniques = techniques;
    }

    public EconomicAssessmentEntity getEconomicAssessment() {
        return economicAssessment;
    }

    public void setEconomicAssessment(EconomicAssessmentEntity economicAssessment) {
        this.economicAssessment = economicAssessment;
    }

    public CivilianImpactEntity getCivilianImpact() {
        return civilianImpact;
    }

    public void setCivilianImpact(CivilianImpactEntity civilianImpact) {
        this.civilianImpact = civilianImpact;
    }

    public EnemyActivityEntity getEnemyActivity() {
        return enemyActivity;
    }

    public void setEnemyActivity(EnemyActivityEntity enemyActivity) {
        this.enemyActivity = enemyActivity;
    }

    public EnvironmentConditionsEntity getEnvironmentConditions() {
        return environmentConditions;
    }

    public void setEnvironmentConditions(EnvironmentConditionsEntity environmentConditions) {
        this.environmentConditions = environmentConditions;
    }

    public List<OperationTimelineEntity> getOperationTimeline() {
        return operationTimeline;
    }

    public void setOperationTimeline(List<OperationTimelineEntity> operationTimeline) {
        this.operationTimeline = operationTimeline;
    }

    public List<String> getOperationTags() {
        return operationTags;
    }

    public void setOperationTags(List<String> operationTags) {
        this.operationTags = operationTags;
    }

    public List<String> getSupportUnits() {
        return supportUnits;
    }

    public void setSupportUnits(List<String> supportUnits) {
        this.supportUnits = supportUnits;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getArtifactsRecovered() {
        return artifactsRecovered;
    }

    public void setArtifactsRecovered(List<String> artifactsRecovered) {
        this.artifactsRecovered = artifactsRecovered;
    }

    public List<String> getEvacuationZones() {
        return evacuationZones;
    }

    public void setEvacuationZones(List<String> evacuationZones) {
        this.evacuationZones = evacuationZones;
    }

    public List<String> getStatusEffects() {
        return statusEffects;
    }

    public void setStatusEffects(List<String> statusEffects) {
        this.statusEffects = statusEffects;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}