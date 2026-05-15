package MephiPackage.dto;

import java.util.List;

public class MissionResponseDto {

    private Long id;
    private String missionId;
    private String date;
    private String location;
    private String outcome;
    private long damageCost;
    private String comment;
    private String notes;

    private List<CurseDto> curses;
    private List<SorcererDto> sorcerers;
    private List<TechniqueDto> techniques;
    private EconomicAssessmentDto economicAssessment;
    private CivilianImpactDto civilianImpact;
    private EnemyActivityDto enemyActivity;
    private EnvironmentConditionsDto environmentConditions;
    private List<OperationTimelineDto> operationTimeline;

    // Простые списки
    private List<String> operationTags;
    private List<String> supportUnits;
    private List<String> recommendations;
    private List<String> artifactsRecovered;
    private List<String> evacuationZones;
    private List<String> statusEffects;

    // Геттеры и сеттеры (сгенерируйте через IDE)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMissionId() { return missionId; }
    public void setMissionId(String missionId) { this.missionId = missionId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getOutcome() { return outcome; }
    public void setOutcome(String outcome) { this.outcome = outcome; }

    public long getDamageCost() { return damageCost; }
    public void setDamageCost(long damageCost) { this.damageCost = damageCost; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<CurseDto> getCurses() {
        return curses;
    }

    public void setCurses(List<CurseDto> curses) {
        this.curses = curses;
    }

    public List<SorcererDto> getSorcerers() {
        return sorcerers;
    }

    public void setSorcerers(List<SorcererDto> sorcerers) {
        this.sorcerers = sorcerers;
    }

    public List<TechniqueDto> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<TechniqueDto> techniques) {
        this.techniques = techniques;
    }

    public EconomicAssessmentDto getEconomicAssessment() {
        return economicAssessment;
    }

    public void setEconomicAssessment(EconomicAssessmentDto economicAssessment) {
        this.economicAssessment = economicAssessment;
    }

    public CivilianImpactDto getCivilianImpact() {
        return civilianImpact;
    }

    public void setCivilianImpact(CivilianImpactDto civilianImpact) {
        this.civilianImpact = civilianImpact;
    }

    public EnemyActivityDto getEnemyActivity() {
        return enemyActivity;
    }

    public void setEnemyActivity(EnemyActivityDto enemyActivity) {
        this.enemyActivity = enemyActivity;
    }

    public EnvironmentConditionsDto getEnvironmentConditions() {
        return environmentConditions;
    }

    public void setEnvironmentConditions(EnvironmentConditionsDto environmentConditions) {
        this.environmentConditions = environmentConditions;
    }

    public List<OperationTimelineDto> getOperationTimeline() {
        return operationTimeline;
    }

    public void setOperationTimeline(List<OperationTimelineDto> operationTimeline) {
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
}