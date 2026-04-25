package MephiPackage.readers;

import MephiPackage.objects.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TXTReader implements Reader {

    public TXTReader() {}

    @Override
    public Mission extract(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath());
        if (lines.isEmpty()) {
            throw new IOException("Файл пуст");
        }

        Mission mission = new Mission();
        List<Curse> curses = new ArrayList<>();
        List<Sorcerer> sorcerers = new ArrayList<>();
        List<Technique> techniques = new ArrayList<>();

        // Новые блоки
        EconomicAssessment economicAssessment = null;
        CivilianImpact civilianImpact = null;
        EnemyActivity enemyActivity = null;
        EnvironmentConditions environmentConditions = null;
        List<OperationTimeline> operationTimeline = new ArrayList<>();
        List<String> operationTags = new ArrayList<>();
        List<String> supportUnits = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        List<String> artifactsRecovered = new ArrayList<>();
        List<String> evacuationZones = new ArrayList<>();
        List<String> statusEffects = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split(": ", 2);
            if (parts.length < 2) continue;

            String key = parts[0].trim();
            String value = parts[1].trim();
            String baseKey = extractBaseKey(key);

            switch (baseKey) {
                // === ОСНОВНЫЕ ПОЛЯ ===
                case "missionId":
                    mission.setMissionId(value);
                    break;
                case "date":
                    mission.setDate(value);
                    break;
                case "location":
                    mission.setLocation(value);
                    break;
                case "outcome":
                    mission.setOutcome(value);
                    break;
                case "damageCost":
                    try {
                        mission.setDamageCost(Long.parseLong(value));
                    } catch (NumberFormatException e) {
                        throw new IOException("Ошибка в поле damageCost: '" + value + "' не является числом");
                    }
                    break;
                case "comment":
                    mission.setComment(value);
                    break;

                // === ПРОКЛЯТИЯ ===
                case "curse":
                    processCurseField(key, value, curses);
                    break;

                // === МАГИ ===
                case "sorcerer":
                    processSorcererField(key, value, sorcerers);
                    break;

                // === ТЕХНИКИ ===
                case "technique":
                    processTechniqueField(key, value, techniques);
                    break;

                // === ЭКОНОМИЧЕСКАЯ ОЦЕНКА ===
                case "economicAssessment":
                    if (economicAssessment == null) {
                        economicAssessment = new EconomicAssessment();
                    }
                    processEconomicAssessmentField(key, value, economicAssessment);
                    break;

                // === ВЛИЯНИЕ НА ГРАЖДАНСКИХ ===
                case "civilianImpact":
                    if (civilianImpact == null) {
                        civilianImpact = new CivilianImpact();
                    }
                    processCivilianImpactField(key, value, civilianImpact);
                    break;

                // === ПОВЕДЕНИЕ ПРОТИВНИКА ===
                case "enemyActivity":
                    if (enemyActivity == null) {
                        enemyActivity = new EnemyActivity();
                    }
                    processEnemyActivityField(key, value, enemyActivity);
                    break;

                // === УСЛОВИЯ СРЕДЫ ===
                case "environmentConditions":
                    if (environmentConditions == null) {
                        environmentConditions = new EnvironmentConditions();
                    }
                    processEnvironmentConditionsField(key, value, environmentConditions);
                    break;

                // === ХРОНОЛОГИЯ ===
                case "operationTimeline":
                    processOperationTimelineField(key, value, operationTimeline);
                    break;

                // === ТЕГИ ===
                case "operationTags":
                    processListField(value, operationTags);
                    break;

                // === ПОДДЕРЖКА ===
                case "supportUnits":
                    processListField(value, supportUnits);
                    break;

                // === РЕКОМЕНДАЦИИ ===
                case "recommendations":
                    processListField(value, recommendations);
                    break;

                // === АРТЕФАКТЫ ===
                case "artifactsRecovered":
                    processListField(value, artifactsRecovered);
                    break;

                // === ЗОНЫ ЭВАКУАЦИИ ===
                case "evacuationZones":
                    processListField(value, evacuationZones);
                    break;

                // === ЭФФЕКТЫ ===
                case "statusEffects":
                    processListField(value, statusEffects);
                    break;

                // === ПРИМЕЧАНИЯ ===
                case "notes":
                    mission.setNotes(value);
                    break;
            }
        }

        // Устанавливаем все блоки в миссию
        mission.setCurses(curses);
        mission.setSorcerers(sorcerers);
        mission.setTechniques(techniques);
        mission.setEconomicAssessment(economicAssessment);
        mission.setCivilianImpact(civilianImpact);
        mission.setEnemyActivity(enemyActivity);
        mission.setEnvironmentConditions(environmentConditions);
        mission.setOperationTimeline(operationTimeline);
        mission.setOperationTags(operationTags);
        mission.setSupportUnits(supportUnits);
        mission.setRecommendations(recommendations);
        mission.setArtifactsRecovered(artifactsRecovered);
        mission.setEvacuationZones(evacuationZones);
        mission.setStatusEffects(statusEffects);

        if (mission.getMissionId() == null || mission.getMissionId().isEmpty()) {
            throw new IOException("Файл не содержит обязательного поля missionId");
        }

        return mission;
    }

    // === ОБРАБОТЧИКИ НОВЫХ БЛОКОВ ===

    private void processEconomicAssessmentField(String key, String value, EconomicAssessment assessment) {
        String field = getFieldName(key);
        switch (field) {
            case "totalDamageCost":
                assessment.setTotalDamageCost(Long.parseLong(value));
                break;
            case "infrastructureDamage":
                assessment.setInfrastructureDamage(Long.parseLong(value));
                break;
            case "commercialDamage":
                assessment.setCommercialDamage(Long.parseLong(value));
                break;
            case "transportDamage":
                assessment.setTransportDamage(Long.parseLong(value));
                break;
            case "recoveryEstimateDays":
                assessment.setRecoveryEstimateDays(Long.parseLong(value));
                break;
            case "insuranceCovered":
                assessment.setInsuranceCovered(Boolean.parseBoolean(value));
                break;
        }
    }

    private void processCivilianImpactField(String key, String value, CivilianImpact impact) {
        String field = getFieldName(key);
        switch (field) {
            case "evacuated":
                impact.setEvacuated(Long.parseLong(value));
                break;
            case "injured":
                impact.setInjured(Long.parseLong(value));
                break;
            case "missing":
                impact.setMissing(Long.parseLong(value));
                break;
            case "publicExposureRisk":
                impact.setPublicExposureRisk(value);
                break;
        }
    }

    private void processEnemyActivityField(String key, String value, EnemyActivity activity) {
        String field = getFieldName(key);
        switch (field) {
            case "behaviorType":
                activity.setBehaviorType(value);
                break;
            case "targetPriority":
                activity.setTargetPriority(value);
                break;
            case "attackPatterns":
                activity.setAttackPatterns(value);
                break;
            case "mobility":
                activity.setMobility(value);
                break;
            case "escalationRisk":
                activity.setEscalationRisk(value);
                break;
        }
    }

    private void processEnvironmentConditionsField(String key, String value, EnvironmentConditions conditions) {
        String field = getFieldName(key);
        switch (field) {
            case "weather":
                conditions.setWeather(value);
                break;
            case "timeOfDay":
                conditions.setTimeOfDay(value);
                break;
            case "visibility":
                conditions.setVisibility(value);
                break;
            case "cursedEnergyDensity":
                conditions.setCursedEnergyDensity(Double.parseDouble(value));
                break;
        }
    }

    private void processOperationTimelineField(String key, String value, List<OperationTimeline> timeline) {
        int index = parseIndex(key);
        String field = parseField(key);

        while (timeline.size() <= index) {
            timeline.add(new OperationTimeline());
        }

        OperationTimeline event = timeline.get(index);
        switch (field) {
            case "timestamp":
                event.setTimestamp(value);
                break;
            case "type":
                event.setType(value);
                break;
            case "description":
                event.setDescription(value);
                break;
        }
    }

    private void processListField(String value, List<String> list) {
        if (value != null && !value.isEmpty()) {
            list.add(value);
        }
    }



    private String getFieldName(String key) {
        int lastDot = key.lastIndexOf('.');
        if (lastDot == -1) return key;
        return key.substring(lastDot + 1);
    }

    private void processSorcererField(String key, String value, List<Sorcerer> sorcerers) {
        int index = parseIndex(key);
        String field = parseField(key);
        ensureSorcererListSize(sorcerers, index);

        Sorcerer sorcerer = sorcerers.get(index);

        switch (field) {
            case "name":
                sorcerer.setName(value);
                break;
            case "rank":
                sorcerer.setRank(value);
                break;
        }
    }

    private void processTechniqueField(String key, String value, List<Technique> techniques) {
        int index = parseIndex(key);
        String field = parseField(key);
        ensureTechniqueListSize(techniques, index);

        Technique technique = techniques.get(index);

        switch (field) {
            case "name":
                technique.setName(value);
                break;
            case "type":
                technique.setType(value);
                break;
            case "damage":
                try {
                    technique.setDamage(Long.parseLong(value));
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка: damage не число: " + value);
                }
                break;
            case "owner":                           // ← ДОБАВИТЬ ЭТОТ БЛОК
                technique.setOwner(value);
                break;
        }
    }

    private void processCurseField(String key, String value, List<Curse> curses) {
        if (key.contains("[")) {
            int index = parseIndex(key);
            String field = parseField(key);
            ensureCursesListSize(curses, index);

            Curse curse = curses.get(index);
            switch (field) {
                case "name":
                    curse.setName(value);
                    break;
                case "threatLevel":
                    curse.setThreatLevel(value);
                    break;
            }
        } else {
            int dotIndex = key.indexOf('.');
            if (dotIndex != -1) {
                String field = key.substring(dotIndex + 1);
                ensureCursesListSize(curses, 0);

                Curse curse = curses.get(0);
                switch (field) {
                    case "name":
                        curse.setName(value);
                        break;
                    case "threatLevel":
                        curse.setThreatLevel(value);
                        break;
                }
            }
        }
    }

    private String extractBaseKey(String key) {
        if (key.contains("[")) {
            return key.substring(0, key.indexOf('['));
        } else if (key.contains(".")) {
            return key.substring(0, key.indexOf('.'));
        }
        return key;
    }

    private void ensureSorcererListSize(List<Sorcerer> list, int index) {
        while (list.size() <= index) {
            list.add(new Sorcerer());
        }
    }

    private void ensureCursesListSize(List<Curse> list, int index) {
        while (list.size() <= index) {
            list.add(new Curse());
        }
    }

    private void ensureTechniqueListSize(List<Technique> list, int index) {
        while (list.size() <= index) {
            list.add(new Technique());
        }
    }

    private int parseIndex(String key) {
        String[] parts = key.split("\\[");
        String[] indexParts = parts[1].split("\\]");
        return Integer.parseInt(indexParts[0]);
    }

    private String parseField(String key) {
        String[] parts = key.split("\\[");
        String[] indexParts = parts[1].split("\\]");
        return indexParts[1].substring(1);
    }
}