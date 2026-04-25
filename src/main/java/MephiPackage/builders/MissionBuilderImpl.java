package MephiPackage.builders;

import MephiPackage.enums.Outcome;
import MephiPackage.objects.*;
import java.util.ArrayList;
import java.util.List;

public class MissionBuilderImpl implements MissionBuilder {

    private String missionId;
    private String date;
    private String location;
    private Outcome outcome;
    private long damageCost;
    private String comment;

    private List<Curse> curses;
    private List<Sorcerer> sorcerers;
    private List<Technique> techniques;

    private EconomicAssessment economicAssessment;
    private CivilianImpact civilianImpact;
    private EnvironmentConditions environmentConditions;
    private List<OperationTimeline> operationTimeline;

    private List<String> operationTags;
    private List<String> supportUnits;
    private List<String> recommendations;
    private String notes;
    private List<String> artifactsRecovered;
    private List<String> evacuationZones;
    private List<String> statusEffects;

    public MissionBuilderImpl() {
        reset();
    }

    @Override
    public MissionBuilder buildMissionId(String missionId) {
        this.missionId = missionId;
        return this;
    }

    @Override
    public MissionBuilder buildDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public MissionBuilder buildLocation(String location) {
        this.location = location;
        return this;
    }

    @Override
    public MissionBuilder buildOutcome(String outcome) {
        this.outcome = Outcome.fromString(outcome);
        return this;
    }

    @Override
    public MissionBuilder buildDamageCost(long damageCost) {
        this.damageCost = damageCost;
        return this;
    }

    @Override
    public MissionBuilder buildComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public MissionBuilder buildCurse(Curse curse) {
        if (this.curses == null) {
            this.curses = new ArrayList<>();
        }
        this.curses.add(curse);
        return this;
    }

    @Override
    public MissionBuilder buildSorcerer(Sorcerer sorcerer) {
        if (this.sorcerers == null) {
            this.sorcerers = new ArrayList<>();
        }
        this.sorcerers.add(sorcerer);
        return this;
    }

    @Override
    public MissionBuilder buildTechnique(Technique technique) {
        if (this.techniques == null) {
            this.techniques = new ArrayList<>();
        }
        this.techniques.add(technique);
        return this;
    }

    @Override
    public MissionBuilder buildEconomicAssessment(EconomicAssessment assessment) {
        this.economicAssessment = assessment;
        return this;
    }

    @Override
    public MissionBuilder buildCivilianImpact(CivilianImpact impact) {
        this.civilianImpact = impact;
        return this;
    }

    @Override
    public MissionBuilder buildEnvironmentConditions(EnvironmentConditions conditions) {
        this.environmentConditions = conditions;
        return this;
    }

    @Override
    public MissionBuilder buildOperationTimeline(OperationTimeline timeline) {
        if (this.operationTimeline == null) {
            this.operationTimeline = new ArrayList<>();
        }
        this.operationTimeline.add(timeline);
        return this;
    }

    @Override
    public MissionBuilder buildOperationTag(String tag) {
        if (this.operationTags == null) {
            this.operationTags = new ArrayList<>();
        }
        this.operationTags.add(tag);
        return this;
    }

    @Override
    public MissionBuilder buildSupportUnit(String unit) {
        if (this.supportUnits == null) {
            this.supportUnits = new ArrayList<>();
        }
        this.supportUnits.add(unit);
        return this;
    }

    @Override
    public MissionBuilder buildRecommendation(String recommendation) {
        if (this.recommendations == null) {
            this.recommendations = new ArrayList<>();
        }
        this.recommendations.add(recommendation);
        return this;
    }

    @Override
    public MissionBuilder buildNotes(String notes) {
        this.notes = notes;
        return this;
    }

    @Override
    public MissionBuilder buildArtifactRecovered(String artifact) {
        if (this.artifactsRecovered == null) {
            this.artifactsRecovered = new ArrayList<>();
        }
        this.artifactsRecovered.add(artifact);
        return this;
    }

    @Override
    public MissionBuilder buildEvacuationZone(String zone) {
        if (this.evacuationZones == null) {
            this.evacuationZones = new ArrayList<>();
        }
        this.evacuationZones.add(zone);
        return this;
    }

    @Override
    public MissionBuilder buildStatusEffect(String effect) {
        if (this.statusEffects == null) {
            this.statusEffects = new ArrayList<>();
        }
        this.statusEffects.add(effect);
        return this;
    }

    @Override
    public Mission getResult() {
        Mission mission = new Mission();

        // Основные поля
        mission.setMissionId(missionId);
        mission.setDate(date);
        mission.setLocation(location);
        mission.setOutcome(outcome);
        mission.setDamageCost(damageCost);
        mission.setComment(comment);

        // Коллекции
        mission.setCurses(curses);
        mission.setSorcerers(sorcerers);
        mission.setTechniques(techniques);

        // Блоки
        mission.setEconomicAssessment(economicAssessment);
        mission.setCivilianImpact(civilianImpact);
        mission.setEnvironmentConditions(environmentConditions);
        mission.setOperationTimeline(operationTimeline);

        mission.setOperationTags(operationTags);
        mission.setSupportUnits(supportUnits);
        mission.setRecommendations(recommendations);
        mission.setNotes(notes);
        mission.setArtifactsRecovered(artifactsRecovered);
        mission.setEvacuationZones(evacuationZones);
        mission.setStatusEffects(statusEffects);

        return mission;
    }

    @Override
    public MissionBuilder reset() {
        missionId = null;
        date = null;
        location = null;
        outcome = null;
        damageCost = 0;
        comment = null;

        curses = null;
        sorcerers = null;
        techniques = null;

        economicAssessment = null;
        civilianImpact = null;
        environmentConditions = null;
        operationTimeline = null;

        operationTags = null;
        supportUnits = null;
        recommendations = null;
        notes = null;
        artifactsRecovered = null;
        evacuationZones = null;
        statusEffects = null;

        return this;
    }
}