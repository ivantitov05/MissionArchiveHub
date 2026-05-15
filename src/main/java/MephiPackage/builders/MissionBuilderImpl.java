package MephiPackage.builders;

import MephiPackage.objects.*;
import MephiPackage.enums.Outcome;
import MephiPackage.enums.ThreatLevel;

import java.util.*;

public class MissionBuilderImpl implements MissionBuilder {

    // === Простые поля ===
    private String missionId;
    private String date;
    private String location;
    private Outcome outcome;
    private long damageCost;
    private String comment;
    private String notes;

    // === Коллекции для составных объектов ===
    private final Map<Integer, Curse> cursesByIndex = new HashMap<>();
    private final Map<Integer, Sorcerer> sorcerersByIndex = new HashMap<>();
    private final Map<Integer, Technique> techniquesByIndex = new HashMap<>();
    private final Map<Integer, OperationTimeline> timelineByIndex = new HashMap<>();

    // === Одиночные блоки ===
    private EconomicAssessment economicAssessment;
    private CivilianImpact civilianImpact;
    private EnemyActivity enemyActivity;
    private EnvironmentConditions environmentConditions;

    // === Простые списки ===
    private final List<String> operationTags = new ArrayList<>();
    private final List<String> supportUnits = new ArrayList<>();
    private final List<String> recommendations = new ArrayList<>();
    private final List<String> artifactsRecovered = new ArrayList<>();
    private final List<String> evacuationZones = new ArrayList<>();
    private final List<String> statusEffects = new ArrayList<>();

    // === Вспомогательные множества для отслеживания загруженных данных ===
    private final Set<String> loadedKeys = new HashSet<>();

    // ======================== ОСНОВНЫЕ МЕТОДЫ ========================

    @Override
    public MissionBuilder load(Map<String, String> data) {
        if (data == null) return this;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            load(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public MissionBuilder load(String key, String value) {
        if (key == null || value == null || value.isEmpty()) return this;

        loadedKeys.add(key);

        // === ПРОСТЫЕ ПОЛЯ ===
        if (key.equals("missionId")) { missionId = value; }
        else if (key.equals("date")) { date = value; }
        else if (key.equals("location")) { location = value; }
        else if (key.equals("outcome")) { outcome = Outcome.fromString(value); }
        else if (key.equals("damageCost")) { damageCost = parseLong(value, 0L); }
        else if (key.equals("comment")) { comment = value; }
        else if (key.equals("notes")) { notes = value; }

        // === ПРОКЛЯТИЯ (curse.name, curse[0].name, curse.threatLevel) ===
        else if (key.startsWith("curse")) {
            processCurseField(key, value);
        }

        // === МАГИ (sorcerer[0].name, sorcerer[0].rank) ===
        else if (key.startsWith("sorcerer")) {
            processSorcererField(key, value);
        }

        // === ТЕХНИКИ (technique[0].name, technique[0].type, technique[0].owner, technique[0].damage) ===
        else if (key.startsWith("technique")) {
            processTechniqueField(key, value);
        }

        // === ЭКОНОМИЧЕСКАЯ ОЦЕНКА ===
        else if (key.startsWith("economicAssessment.")) {
            processEconomicAssessment(key, value);
        }

        // === ВЛИЯНИЕ НА ГРАЖДАНСКИХ ===
        else if (key.startsWith("civilianImpact.")) {
            processCivilianImpact(key, value);
        }

        // === ПОВЕДЕНИЕ ПРОТИВНИКА ===
        else if (key.startsWith("enemyActivity.")) {
            processEnemyActivity(key, value);
        }

        // === УСЛОВИЯ СРЕДЫ ===
        else if (key.startsWith("environmentConditions.")) {
            processEnvironmentConditions(key, value);
        }

        // === ХРОНОЛОГИЯ ===
        else if (key.startsWith("operationTimeline")) {
            processOperationTimeline(key, value);
        }

        // === ПРОСТЫЕ СПИСКИ ===
        else if (key.equals("operationTags")) { operationTags.add(value); }
        else if (key.equals("supportUnits")) { supportUnits.add(value); }
        else if (key.equals("recommendations")) { recommendations.add(value); }
        else if (key.equals("artifactsRecovered")) { artifactsRecovered.add(value); }
        else if (key.equals("evacuationZones")) { evacuationZones.add(value); }
        else if (key.equals("statusEffects")) { statusEffects.add(value); }

        return this;
    }

    // ======================== ОБРАБОТЧИКИ ПОЛЕЙ ========================

    private void processCurseField(String key, String value) {
        int index = extractIndex(key);
        String field = extractField(key);

        Curse curse = cursesByIndex.computeIfAbsent(index, k -> new Curse());

        switch (field) {
            case "name":
                curse.setName(value);
                break;
            case "threatLevel":
                curse.setThreatLevel(ThreatLevel.fromString(value));
                break;
        }
    }

    private void processSorcererField(String key, String value) {
        int index = extractIndex(key);
        String field = extractField(key);

        Sorcerer sorcerer = sorcerersByIndex.computeIfAbsent(index, k -> new Sorcerer());

        switch (field) {
            case "name":
                sorcerer.setName(value);
                break;
            case "rank":
                sorcerer.setRank(value);
                break;
        }
    }

    private void processTechniqueField(String key, String value) {
        int index = extractIndex(key);
        String field = extractField(key);

        Technique technique = techniquesByIndex.computeIfAbsent(index, k -> new Technique());

        switch (field) {
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
                technique.setDamage(parseLong(value, 0L));
                break;
        }
    }

    private void processEconomicAssessment(String key, String value) {
        if (economicAssessment == null) economicAssessment = new EconomicAssessment();
        String field = key.substring("economicAssessment.".length());

        switch (field) {
            case "totalDamageCost":
                economicAssessment.setTotalDamageCost(parseLong(value, null));
                break;
            case "infrastructureDamage":
                economicAssessment.setInfrastructureDamage(parseLong(value, null));
                break;
            case "commercialDamage":
                economicAssessment.setCommercialDamage(parseLong(value, null));
                break;
            case "transportDamage":
                economicAssessment.setTransportDamage(parseLong(value, null));
                break;
            case "recoveryEstimateDays":
                economicAssessment.setRecoveryEstimateDays(parseLong(value, null));
                break;
            case "insuranceCovered":
                economicAssessment.setInsuranceCovered(Boolean.parseBoolean(value));
                break;
        }
    }

    private void processCivilianImpact(String key, String value) {
        if (civilianImpact == null) civilianImpact = new CivilianImpact();
        String field = key.substring("civilianImpact.".length());

        switch (field) {
            case "evacuated":
                civilianImpact.setEvacuated(parseLong(value, null));
                break;
            case "injured":
                civilianImpact.setInjured(parseLong(value, null));
                break;
            case "missing":
                civilianImpact.setMissing(parseLong(value, null));
                break;
            case "publicExposureRisk":
                civilianImpact.setPublicExposureRisk(value);
                break;
        }
    }

    private void processEnemyActivity(String key, String value) {
        if (enemyActivity == null) enemyActivity = new EnemyActivity();
        String field = key.substring("enemyActivity.".length());

        switch (field) {
            case "behaviorType":
                enemyActivity.setBehaviorType(value);
                break;
            case "targetPriority":
                enemyActivity.setTargetPriority(value);
                break;
            case "attackPatterns":
                enemyActivity.setAttackPatterns(value);
                break;
            case "mobility":
                enemyActivity.setMobility(value);
                break;
            case "escalationRisk":
                enemyActivity.setEscalationRisk(value);
                break;
        }
    }

    private void processEnvironmentConditions(String key, String value) {
        if (environmentConditions == null) environmentConditions = new EnvironmentConditions();
        String field = key.substring("environmentConditions.".length());

        switch (field) {
            case "weather":
                environmentConditions.setWeather(value);
                break;
            case "timeOfDay":
                environmentConditions.setTimeOfDay(value);
                break;
            case "visibility":
                environmentConditions.setVisibility(value);
                break;
            case "cursedEnergyDensity":
                environmentConditions.setCursedEnergyDensity(parseDouble(value, 0.0));
                break;
        }
    }

    private void processOperationTimeline(String key, String value) {
        int index = extractIndex(key);
        String field = extractField(key);

        OperationTimeline event = timelineByIndex.computeIfAbsent(index, k -> new OperationTimeline());

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

    // ======================== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ========================

    /**
     * Извлекает индекс из ключа вида "sorcerer[0].name" → 0
     */
    private int extractIndex(String key) {
        int start = key.indexOf('[');
        int end = key.indexOf(']');
        if (start == -1 || end == -1) return 0;
        try {
            return Integer.parseInt(key.substring(start + 1, end));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Извлекает имя поля из ключа:
     * "sorcerer[0].name" → "name"
     * "curse.threatLevel" → "threatLevel"
     */
    private String extractField(String key) {
        int dotIndex = key.lastIndexOf('.');
        if (dotIndex == -1) return key;
        return key.substring(dotIndex + 1);
    }

    private long parseLong(String value, Long defaultValue) {
        if (value == null || value.isEmpty()) return defaultValue != null ? defaultValue : 0;
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue != null ? defaultValue : 0;
        }
    }

    private double parseDouble(String value, double defaultValue) {
        if (value == null || value.isEmpty()) return defaultValue;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // ======================== СБОРКА ПРОДУКТА ========================

    @Override
    public Mission build() {
        Mission mission = new Mission();

        // Простые поля
        mission.setMissionId(missionId);
        mission.setDate(date);
        mission.setLocation(location);
        mission.setOutcome(outcome);
        mission.setDamageCost(damageCost);
        mission.setComment(comment);
        mission.setNotes(notes);

        // Коллекции
        mission.setCurses(new ArrayList<>(cursesByIndex.values()));
        mission.setSorcerers(new ArrayList<>(sorcerersByIndex.values()));
        mission.setTechniques(new ArrayList<>(techniquesByIndex.values()));
        mission.setOperationTimeline(new ArrayList<>(timelineByIndex.values()));

        // Одиночные блоки
        mission.setEconomicAssessment(economicAssessment);
        mission.setCivilianImpact(civilianImpact);
        mission.setEnemyActivity(enemyActivity);
        mission.setEnvironmentConditions(environmentConditions);

        // Простые списки
        mission.setOperationTags(operationTags);
        mission.setSupportUnits(supportUnits);
        mission.setRecommendations(recommendations);
        mission.setArtifactsRecovered(artifactsRecovered);
        mission.setEvacuationZones(evacuationZones);
        mission.setStatusEffects(statusEffects);

        return mission;
    }

    @Override
    public void reset() {
        // Простые поля
        missionId = null;
        date = null;
        location = null;
        outcome = null;
        damageCost = 0;
        comment = null;
        notes = null;

        // Коллекции
        cursesByIndex.clear();
        sorcerersByIndex.clear();
        techniquesByIndex.clear();
        timelineByIndex.clear();

        // Одиночные блоки
        economicAssessment = null;
        civilianImpact = null;
        enemyActivity = null;
        environmentConditions = null;

        // Простые списки
        operationTags.clear();
        supportUnits.clear();
        recommendations.clear();
        artifactsRecovered.clear();
        evacuationZones.clear();
        statusEffects.clear();

        loadedKeys.clear();
    }

    /**
     * Возвращает набор загруженных ключей (для отладки).
     */
    public Set<String> getLoadedKeys() {
        return new HashSet<>(loadedKeys);
    }
}