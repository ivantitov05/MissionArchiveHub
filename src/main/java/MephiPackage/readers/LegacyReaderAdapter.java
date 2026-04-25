package MephiPackage.readers;

import MephiPackage.builders.MissionBuilder;
import MephiPackage.objects.*;
import java.io.File;
import java.io.IOException;


public class LegacyReaderAdapter implements Reader {

    private final Reader legacyReader;
    private final MissionBuilder builder;
    public LegacyReaderAdapter(Reader legacyReader, MissionBuilder builder) {
        this.legacyReader = legacyReader;
        this.builder = builder;
    }

    @Override
    public Mission extract(File file) throws IOException {
        Mission mission = legacyReader.extract(file);

        transferToBuilder(mission, builder);

        return builder.getResult();
    }


    private void transferToBuilder(Mission mission, MissionBuilder builder) {
        if (mission.getMissionId() != null) {
            builder.buildMissionId(mission.getMissionId());
        }
        if (mission.getDate() != null) {
            builder.buildDate(mission.getDate());
        }
        if (mission.getLocation() != null) {
            builder.buildLocation(mission.getLocation());
        }
        if (mission.getOutcome() != null) {
            builder.buildOutcome(mission.getOutcome().toString());
        }
        builder.buildDamageCost(mission.getDamageCost());

        if (mission.getCurses() != null) {
            for (Curse curse : mission.getCurses()) {
                builder.buildCurse(curse);
            }
        }

        if (mission.getSorcerers() != null) {
            for (Sorcerer sorcerer : mission.getSorcerers()) {
                builder.buildSorcerer(sorcerer);
            }
        }

        if (mission.getTechniques() != null) {
            for (Technique technique : mission.getTechniques()) {
                builder.buildTechnique(technique);
            }
        }

        if (mission.getComment() != null) {
            builder.buildComment(mission.getComment());
        }

        if (mission.getEconomicAssessment() != null) {
            builder.buildEconomicAssessment(mission.getEconomicAssessment());
        }

        if (mission.getCivilianImpact() != null) {
            builder.buildCivilianImpact(mission.getCivilianImpact());
        }

        if (mission.getEnvironmentConditions() != null) {
            builder.buildEnvironmentConditions(mission.getEnvironmentConditions());
        }

        if (mission.getOperationTimeline() != null) {
            for (OperationTimeline timeline : mission.getOperationTimeline()) {
                builder.buildOperationTimeline(timeline);
            }
        }

        if (mission.getOperationTags() != null) {
            for (String tag : mission.getOperationTags()) {
                builder.buildOperationTag(tag);
            }
        }

        if (mission.getSupportUnits() != null) {
            for (String unit : mission.getSupportUnits()) {
                builder.buildSupportUnit(unit);
            }
        }

        if (mission.getRecommendations() != null) {
            for (String rec : mission.getRecommendations()) {
                builder.buildRecommendation(rec);
            }
        }

        if (mission.getNotes() != null) {
            builder.buildNotes(mission.getNotes());
        }

        if (mission.getArtifactsRecovered() != null) {
            for (String artifact : mission.getArtifactsRecovered()) {
                builder.buildArtifactRecovered(artifact);
            }
        }

        if (mission.getEvacuationZones() != null) {
            for (String zone : mission.getEvacuationZones()) {
                builder.buildEvacuationZone(zone);
            }
        }

        if (mission.getStatusEffects() != null) {
            for (String effect : mission.getStatusEffects()) {
                builder.buildStatusEffect(effect);
            }
        }

        Curse singleCurse = mission.getCurse();
        if (singleCurse != null && (mission.getCurses() == null || mission.getCurses().isEmpty())) {
            builder.buildCurse(singleCurse);
        }
    }
}