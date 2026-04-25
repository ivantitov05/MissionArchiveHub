package MephiPackage.readers;

import MephiPackage.builders.MissionBuilder;
import MephiPackage.objects.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class IniTXTReader implements Reader {

    private final MissionBuilder builder;

    public IniTXTReader(MissionBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Mission extract(File file) throws IOException {
        System.out.println("новый txt");
        List<String> lines = Files.readAllLines(file.toPath());

        builder.reset();

        String currentSection = "";

        Curse currentCurse = null;
        Sorcerer currentSorcerer = null;
        Technique currentTechnique = null;
        EnvironmentConditions currentEnvironment = null;
        EconomicAssessment currentEconomicAssessment = null;
        CivilianImpact currentCivilianImpact = null;
        OperationTimeline currentTimeline = null;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("[") && line.endsWith("]")) {
                commitSection(currentSection, currentCurse, currentSorcerer,
                        currentTechnique, currentEnvironment,
                        currentEconomicAssessment, currentCivilianImpact,
                        currentTimeline);

                currentSection = line.substring(1, line.length() - 1);

                switch (currentSection) {
                    case "CURSE":
                        currentCurse = new Curse();
                        break;
                    case "SORCERER":
                        currentSorcerer = new Sorcerer();
                        break;
                    case "TECHNIQUE":
                        currentTechnique = new Technique();
                        break;
                    case "ENVIRONMENT":
                        currentEnvironment = new EnvironmentConditions();
                        break;
                    case "ECONOMIC_ASSESSMENT":
                        currentEconomicAssessment = new EconomicAssessment();
                        break;
                    case "CIVILIAN_IMPACT":
                        currentCivilianImpact = new CivilianImpact();
                        break;
                    case "OPERATION_TIMELINE":
                        currentTimeline = new OperationTimeline();
                        break;
                    default:
                        break;
                }
                continue;
            }

            String[] parts = line.split("=", 2);
            if (parts.length < 2) continue;

            String key = parts[0].trim();
            String value = parts[1].trim();

            switch (currentSection) {
                case "MISSION":
                    parseMissionField(key, value);
                    break;
                case "CURSE":
                    parseCurseField(currentCurse, key, value);
                    break;
                case "SORCERER":
                    parseSorcererField(currentSorcerer, key, value);
                    break;
                case "TECHNIQUE":
                    parseTechniqueField(currentTechnique, key, value);
                    break;
                case "ENVIRONMENT":
                    parseEnvironmentField(currentEnvironment, key, value);
                    break;
                case "ECONOMIC_ASSESSMENT":
                    parseEconomicAssessmentField(currentEconomicAssessment, key, value);
                    break;
                case "CIVILIAN_IMPACT":
                    parseCivilianImpactField(currentCivilianImpact, key, value);
                    break;
                case "OPERATION_TIMELINE":
                    parseOperationTimelineField(currentTimeline, key, value);
                    break;
                case "OPERATION_TAGS":
                    parseStringListField(value, "operationTag");
                    break;
                case "SUPPORT_UNITS":
                    parseStringListField(value, "supportUnit");
                    break;
                case "RECOMMENDATIONS":
                    parseStringListField(value, "recommendation");
                    break;
                case "ARTIFACTS_RECOVERED":
                    parseStringListField(value, "artifact");
                    break;
                case "EVACUATION_ZONES":
                    parseStringListField(value, "evacuationZone");
                    break;
                case "STATUS_EFFECTS":
                    parseStringListField(value, "statusEffect");
                    break;
            }
        }

        commitSection(currentSection, currentCurse, currentSorcerer,
                currentTechnique, currentEnvironment,
                currentEconomicAssessment, currentCivilianImpact,
                currentTimeline);

        return builder.getResult();
    }

    private void commitSection(String section, Curse curse, Sorcerer sorcerer,
                               Technique technique, EnvironmentConditions environment,
                               EconomicAssessment economicAssessment,
                               CivilianImpact civilianImpact, OperationTimeline timeline) {
        switch (section) {
            case "CURSE":
                if (curse != null) builder.buildCurse(curse);
                break;
            case "SORCERER":
                if (sorcerer != null) builder.buildSorcerer(sorcerer);
                break;
            case "TECHNIQUE":
                if (technique != null) builder.buildTechnique(technique);
                break;
            case "ENVIRONMENT":
                if (environment != null) builder.buildEnvironmentConditions(environment);
                break;
            case "ECONOMIC_ASSESSMENT":
                if (economicAssessment != null) builder.buildEconomicAssessment(economicAssessment);
                break;
            case "CIVILIAN_IMPACT":
                if (civilianImpact != null) builder.buildCivilianImpact(civilianImpact);
                break;
            case "OPERATION_TIMELINE":
                if (timeline != null) builder.buildOperationTimeline(timeline);
                break;
        }
    }

    private void parseMissionField(String key, String value) {
        switch (key) {
            case "missionId":
                builder.buildMissionId(value);
                break;
            case "date":
                builder.buildDate(value);
                break;
            case "location":
                builder.buildLocation(value);
                break;
            case "outcome":
                builder.buildOutcome(value);
                break;
            case "damageCost":
                try {
                    builder.buildDamageCost(Long.parseLong(value));
                } catch (NumberFormatException e) {
                    // игнорируем
                }
                break;
            case "comment":
                builder.buildComment(value);
                break;
        }
    }

    private void parseCurseField(Curse curse, String key, String value) {
        switch (key) {
            case "name":
                curse.setName(value);
                break;
            case "threatLevel":
                curse.setThreatLevel(value);
                break;
        }
    }

    private void parseSorcererField(Sorcerer sorcerer, String key, String value) {
        switch (key) {
            case "name":
                sorcerer.setName(value);
                break;
            case "rank":
                sorcerer.setRank(value);
                break;
        }
    }

    private void parseTechniqueField(Technique technique, String key, String value) {
        switch (key) {
            case "name":
                technique.setName(value);
                break;
            case "type":
                technique.setType(value);
                break;
            case "owner":
                technique.setOwner(value);
                break;
            case "damage":
                try {
                    technique.setDamage(Long.parseLong(value));
                } catch (NumberFormatException e) {
                    // игнорируем
                }
                break;
        }
    }

    private void parseEnvironmentField(EnvironmentConditions env, String key, String value) {
        switch (key) {
            case "weather":
                env.setWeather(value);
                break;
            case "timeOfDay":
                env.setTimeOfDay(value);
                break;
            case "visibility":
                env.setVisibility(value);
                break;
            case "cursedEnergyDensity":
                try {
                    env.setCursedEnergyDensity(Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    // игнорируем
                }
                break;
        }
    }

    private void parseEconomicAssessmentField(EconomicAssessment ea, String key, String value) {
        try {
            switch (key) {
                case "totalDamageCost":
                    ea.setTotalDamageCost(Long.parseLong(value));
                    break;
                case "infrastructureDamage":
                    ea.setInfrastructureDamage(Long.parseLong(value));
                    break;
                case "commercialDamage":
                    ea.setCommercialDamage(Long.parseLong(value));
                    break;
                case "transportDamage":
                    ea.setTransportDamage(Long.parseLong(value));
                    break;
                case "recoveryEstimateDays":
                    ea.setRecoveryEstimateDays(Long.parseLong(value));
                    break;
                case "insuranceCovered":
                    ea.setInsuranceCovered(Boolean.parseBoolean(value));
                    break;
            }
        } catch (NumberFormatException e) {
            // игнорируем
        }
    }

    private void parseCivilianImpactField(CivilianImpact ci, String key, String value) {
        try {
            switch (key) {
                case "evacuated":
                    ci.setEvacuated(Long.parseLong(value));
                    break;
                case "injured":
                    ci.setInjured(Long.parseLong(value));
                    break;
                case "missing":
                    ci.setMissing(Long.parseLong(value));
                    break;
                case "publicExposureRisk":
                    ci.setPublicExposureRisk(value);
                    break;
            }
        } catch (NumberFormatException e) {
            // игнорируем
        }
    }

    private void parseOperationTimelineField(OperationTimeline ot, String key, String value) {
        switch (key) {
            case "timestamp":
                ot.setTimestamp(value);
                break;
            case "type":
                ot.setType(value);
                break;
            case "description":
                ot.setDescription(value);
                break;
        }
    }

    private void parseStringListField(String value, String type) {
        if (value == null || value.isEmpty()) return;

        String[] items = value.split(",");
        for (String item : items) {
            String trimmed = item.trim();
            if (trimmed.isEmpty()) continue;

            switch (type) {
                case "operationTag":
                    builder.buildOperationTag(trimmed);
                    break;
                case "supportUnit":
                    builder.buildSupportUnit(trimmed);
                    break;
                case "recommendation":
                    builder.buildRecommendation(trimmed);
                    break;
                case "artifact":
                    builder.buildArtifactRecovered(trimmed);
                    break;
                case "evacuationZone":
                    builder.buildEvacuationZone(trimmed);
                    break;
                case "statusEffect":
                    builder.buildStatusEffect(trimmed);
                    break;
            }
        }
    }
}