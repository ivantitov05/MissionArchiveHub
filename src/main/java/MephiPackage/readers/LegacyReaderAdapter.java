package MephiPackage.readers;

import MephiPackage.builders.MissionBuilder;
import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.objects.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegacyReaderAdapter implements Reader {

    private final Reader legacyReader;
    private final MissionBuilder builder;

    public LegacyReaderAdapter(Reader legacyReader, MissionBuilder builder) {
        this.legacyReader = legacyReader;
        this.builder = builder;
    }

    @Override
    public Mission extract(File file) throws IOException {
        // 1. Старый ридер создаёт Mission (через сеттеры)
        Mission mission = legacyReader.extract(file);

        // 2. Преобразуем Mission в Map универсальных ключей
        Map<String, String> data = convertMissionToMap(mission);

        // 3. Билдер сам соберёт Mission из Map
        return builder.load(data).build();
    }

    private Map<String, String> convertMissionToMap(Mission mission) {
        Map<String, String> data = new HashMap<>();

        // === ПРОСТЫЕ ПОЛЯ ===
        putIfNotNull(data, "missionId", mission.getMissionId());
        putIfNotNull(data, "date", mission.getDate());
        putIfNotNull(data, "location", mission.getLocation());
        if (mission.getOutcome() != null) {
            data.put("outcome", mission.getOutcome().toString());
        }
        data.put("damageCost", String.valueOf(mission.getDamageCost()));
        putIfNotNull(data, "comment", mission.getComment());
        putIfNotNull(data, "notes", mission.getNotes());

        // === ПРОКЛЯТИЯ ===
        if (mission.getCurses() != null) {
            for (int i = 0; i < mission.getCurses().size(); i++) {
                Curse c = mission.getCurses().get(i);
                if (c.getName() != null) {
                    data.put("curse[" + i + "].name", c.getName());
                }
                if (c.getThreatLevel() != null) {
                    data.put("curse[" + i + "].threatLevel", c.getThreatLevel().toString());
                }
            }
        }

        // === МАГИ ===
        if (mission.getSorcerers() != null) {
            for (int i = 0; i < mission.getSorcerers().size(); i++) {
                Sorcerer s = mission.getSorcerers().get(i);
                putIfNotNull(data, "sorcerer[" + i + "].name", s.getName());
                putIfNotNull(data, "sorcerer[" + i + "].rank", s.getRank());
            }
        }

        // === ТЕХНИКИ ===
        if (mission.getTechniques() != null) {
            for (int i = 0; i < mission.getTechniques().size(); i++) {
                Technique t = mission.getTechniques().get(i);
                putIfNotNull(data, "technique[" + i + "].name", t.getName());
                putIfNotNull(data, "technique[" + i + "].type", t.getType());
                putIfNotNull(data, "technique[" + i + "].owner", t.getOwner());
                data.put("technique[" + i + "].damage", String.valueOf(t.getDamage()));
            }
        }

        // === ЭКОНОМИЧЕСКАЯ ОЦЕНКА ===
        if (mission.getEconomicAssessment() != null) {
            EconomicAssessment ea = mission.getEconomicAssessment();
            putIfNotNull(data, "economicAssessment.totalDamageCost", ea.getTotalDamageCost());
            putIfNotNull(data, "economicAssessment.infrastructureDamage", ea.getInfrastructureDamage());
            putIfNotNull(data, "economicAssessment.commercialDamage", ea.getCommercialDamage());
            putIfNotNull(data, "economicAssessment.transportDamage", ea.getTransportDamage());
            putIfNotNull(data, "economicAssessment.recoveryEstimateDays", ea.getRecoveryEstimateDays());
            if (ea.getInsuranceCovered() != null) {
                data.put("economicAssessment.insuranceCovered", ea.getInsuranceCovered().toString());
            }
        }

        // === ВЛИЯНИЕ НА ГРАЖДАНСКИХ ===
        if (mission.getCivilianImpact() != null) {
            CivilianImpact ci = mission.getCivilianImpact();
            putIfNotNull(data, "civilianImpact.evacuated", ci.getEvacuated());
            putIfNotNull(data, "civilianImpact.injured", ci.getInjured());
            putIfNotNull(data, "civilianImpact.missing", ci.getMissing());
            putIfNotNull(data, "civilianImpact.publicExposureRisk", ci.getPublicExposureRisk());
        }

        // === ПОВЕДЕНИЕ ПРОТИВНИКА ===
        if (mission.getEnemyActivity() != null) {
            EnemyActivity ea = mission.getEnemyActivity();
            putIfNotNull(data, "enemyActivity.behaviorType", ea.getBehaviorType());

            // targetPriority — список
            if (ea.getTargetPriority() != null && !ea.getTargetPriority().isEmpty()) {
                // Берём первый элемент или объединяем?
                data.put("enemyActivity.targetPriority", ea.getTargetPriority().get(0));
            }

            // attackPatterns — список
            if (ea.getAttackPatterns() != null && !ea.getAttackPatterns().isEmpty()) {
                data.put("enemyActivity.attackPatterns", ea.getAttackPatterns().get(0));
            }

            if (ea.getMobility() != null) {
                data.put("enemyActivity.mobility", ea.getMobility().toString());
            }
            if (ea.getEscalationRisk() != null) {
                data.put("enemyActivity.escalationRisk", ea.getEscalationRisk().toString());
            }
        }

        // === УСЛОВИЯ СРЕДЫ ===
        if (mission.getEnvironmentConditions() != null) {
            EnvironmentConditions ec = mission.getEnvironmentConditions();
            putIfNotNull(data, "environmentConditions.weather", ec.getWeather());
            putIfNotNull(data, "environmentConditions.timeOfDay", ec.getTimeOfDay());
            putIfNotNull(data, "environmentConditions.visibility", ec.getVisibility());
            if (ec.getCursedEnergyDensity() != null) {
                data.put("environmentConditions.cursedEnergyDensity", ec.getCursedEnergyDensity().toString());
            }
        }

        // === ХРОНОЛОГИЯ ===
        if (mission.getOperationTimeline() != null) {
            for (int i = 0; i < mission.getOperationTimeline().size(); i++) {
                OperationTimeline ot = mission.getOperationTimeline().get(i);
                putIfNotNull(data, "operationTimeline[" + i + "].timestamp", ot.getTimestamp());
                putIfNotNull(data, "operationTimeline[" + i + "].type", ot.getType());
                putIfNotNull(data, "operationTimeline[" + i + "].description", ot.getDescription());
            }
        }

        // === ПРОСТЫЕ СПИСКИ ===
        putAllToList(data, "operationTags", mission.getOperationTags());
        putAllToList(data, "supportUnits", mission.getSupportUnits());
        putAllToList(data, "recommendations", mission.getRecommendations());
        putAllToList(data, "artifactsRecovered", mission.getArtifactsRecovered());
        putAllToList(data, "evacuationZones", mission.getEvacuationZones());
        putAllToList(data, "statusEffects", mission.getStatusEffects());

        return data;
    }

    private void putIfNotNull(Map<String, String> data, String key, String value) {
        if (value != null && !value.isEmpty()) {
            data.put(key, value);
        }
    }

    private void putIfNotNull(Map<String, String> data, String key, Long value) {
        if (value != null) {
            data.put(key, String.valueOf(value));
        }
    }

    private void putIfNotNull(Map<String, String> data, String key, Object value) {
        if (value != null) {
            data.put(key, value.toString());
        }
    }

    private void putAllToList(Map<String, String> data, String key, List<String> values) {
        if (values != null) {
            for (String value : values) {
                data.put(key, value);  // Для простых списков без индексов
            }
        }
    }
}