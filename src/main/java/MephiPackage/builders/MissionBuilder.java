package MephiPackage.builders;

import MephiPackage.enums.Outcome;
import MephiPackage.objects.*;

public interface MissionBuilder {

    MissionBuilder buildMissionId(String missionId);
    MissionBuilder buildDate(String date);
    MissionBuilder buildLocation(String location);
    MissionBuilder buildOutcome(String outcome);
    MissionBuilder buildDamageCost(long damageCost);
    MissionBuilder buildComment(String comment);

    MissionBuilder buildCurse(Curse curse);

    MissionBuilder buildSorcerer(Sorcerer sorcerer);

    MissionBuilder buildTechnique(Technique technique);

    MissionBuilder buildEconomicAssessment(EconomicAssessment assessment);

    MissionBuilder buildCivilianImpact(CivilianImpact impact);

    MissionBuilder buildEnvironmentConditions(EnvironmentConditions conditions);

    MissionBuilder buildOperationTimeline(OperationTimeline timeline);

    MissionBuilder buildOperationTag(String tag);

    MissionBuilder buildSupportUnit(String unit);

    MissionBuilder buildRecommendation(String recommendation);

    MissionBuilder buildNotes(String notes);

    MissionBuilder buildArtifactRecovered(String artifact);

    MissionBuilder buildEvacuationZone(String zone);

    MissionBuilder buildStatusEffect(String effect);

    Mission getResult();

    MissionBuilder reset();
}