package MephiPackage.service;

import MephiPackage.dto.*;
import MephiPackage.enums.Outcome;
import MephiPackage.enums.ThreatLevel;
import MephiPackage.objects.*;
import MephiPackage.entities.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class MissionConverter {


    public MissionEntity toEntity(Mission mission) {
        if (mission == null) {
            return null;
        }

        MissionEntity entity = new MissionEntity();

        entity.setMissionId(mission.getMissionId());
        entity.setDate(mission.getDate());
        entity.setLocation(mission.getLocation());
        entity.setOutcome(mission.getOutcome() != null ? Outcome.valueOf(mission.getOutcome().toString()) : null);
        entity.setDamageCost(mission.getDamageCost());
        entity.setComment(mission.getComment());
        entity.setNotes(mission.getNotes());

        if (mission.getEconomicAssessment() != null) {
            entity.setEconomicAssessment(toEntity(mission.getEconomicAssessment()));
        }
        if (mission.getCivilianImpact() != null) {
            entity.setCivilianImpact(toEntity(mission.getCivilianImpact()));
        }
        if (mission.getEnemyActivity() != null) {
            entity.setEnemyActivity(toEntity(mission.getEnemyActivity()));
        }
        if (mission.getEnvironmentConditions() != null) {
            entity.setEnvironmentConditions(toEntity(mission.getEnvironmentConditions()));
        }

        if (mission.getCurses() != null) {
            entity.setCurses(mission.getCurses().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList()));
            entity.getCurses().forEach(curse -> curse.setMission(entity));
        }

        if (mission.getSorcerers() != null) {
            entity.setSorcerers(mission.getSorcerers().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList()));
            entity.getSorcerers().forEach(sorcerer -> sorcerer.setMission(entity));
        }

        if (mission.getTechniques() != null) {
            entity.setTechniques(mission.getTechniques().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList()));
            entity.getTechniques().forEach(technique -> technique.setMission(entity));
        }

        if (mission.getOperationTimeline() != null) {
            entity.setOperationTimeline(mission.getOperationTimeline().stream()
                    .map(this::toEntity)
                    .collect(Collectors.toList()));
            entity.getOperationTimeline().forEach(timeline -> timeline.setMission(entity));
        }

        entity.setOperationTags(mission.getOperationTags());
        entity.setSupportUnits(mission.getSupportUnits());
        entity.setRecommendations(mission.getRecommendations());
        entity.setArtifactsRecovered(mission.getArtifactsRecovered());
        entity.setEvacuationZones(mission.getEvacuationZones());
        entity.setStatusEffects(mission.getStatusEffects());

        return entity;
    }


    private CurseEntity toEntity(Curse curse) {
        CurseEntity entity = new CurseEntity();
        entity.setName(curse.getName());
        entity.setThreatLevel(curse.getThreatLevel() != null ? curse.getThreatLevel().toString() : null);
        return entity;
    }



    private SorcererEntity toEntity(Sorcerer sorcerer) {
        SorcererEntity entity = new SorcererEntity();
        entity.setName(sorcerer.getName());
        entity.setRank(sorcerer.getRank());
        return entity;
    }

    private TechniqueEntity toEntity(Technique technique) {
        TechniqueEntity entity = new TechniqueEntity();
        entity.setName(technique.getName());
        entity.setType(technique.getType());
        entity.setOwner(technique.getOwner());
        entity.setDamage(technique.getDamage());
        return entity;
    }

    private EconomicAssessmentEntity toEntity(EconomicAssessment assessment) {
        EconomicAssessmentEntity entity = new EconomicAssessmentEntity();
        entity.setTotalDamageCost(assessment.getTotalDamageCost());
        entity.setInfrastructureDamage(assessment.getInfrastructureDamage());
        entity.setCommercialDamage(assessment.getCommercialDamage());
        entity.setTransportDamage(assessment.getTransportDamage());
        entity.setRecoveryEstimateDays(assessment.getRecoveryEstimateDays());
        entity.setInsuranceCovered(assessment.getInsuranceCovered());
        return entity;
    }

    private CivilianImpactEntity toEntity(CivilianImpact impact) {
        CivilianImpactEntity entity = new CivilianImpactEntity();
        entity.setEvacuated(impact.getEvacuated());
        entity.setInjured(impact.getInjured());
        entity.setMissing(impact.getMissing());
        entity.setPublicExposureRisk(impact.getPublicExposureRisk());
        return entity;
    }

    private EnemyActivityEntity toEntity(EnemyActivity activity) {
        EnemyActivityEntity entity = new EnemyActivityEntity();
        entity.setBehaviorType(activity.getBehaviorType());

        if (activity.getTargetPriority() != null && !activity.getTargetPriority().isEmpty()) {
            entity.setTargetPriority(new ArrayList<>(activity.getTargetPriority()));
        }

        if (activity.getAttackPatterns() != null && !activity.getAttackPatterns().isEmpty()) {
            entity.setAttackPatterns(new ArrayList<>(activity.getAttackPatterns()));
        }

        if (activity.getCountermeasuresUsed() != null && !activity.getCountermeasuresUsed().isEmpty()) {
            entity.setCountermeasuresUsed(new ArrayList<>(activity.getCountermeasuresUsed()));
        }

        entity.setMobility(activity.getMobility() != null ? activity.getMobility() : null);
        entity.setEscalationRisk(activity.getEscalationRisk() != null ? activity.getEscalationRisk() : null);

        return entity;
    }

    private EnvironmentConditionsEntity toEntity(EnvironmentConditions conditions) {
        EnvironmentConditionsEntity entity = new EnvironmentConditionsEntity();
        entity.setWeather(conditions.getWeather());
        entity.setTimeOfDay(conditions.getTimeOfDay());
        entity.setVisibility(conditions.getVisibility());
        entity.setCursedEnergyDensity(conditions.getCursedEnergyDensity());
        return entity;
    }

    private OperationTimelineEntity toEntity(OperationTimeline timeline) {
        OperationTimelineEntity entity = new OperationTimelineEntity();
        entity.setTimestamp(timeline.getTimestamp());
        entity.setType(timeline.getType());
        entity.setDescription(timeline.getDescription());
        return entity;
    }

    public MissionResponseDto toDto(MissionEntity entity) {
        if (entity == null) return null;

        MissionResponseDto dto = new MissionResponseDto();

        dto.setId(entity.getId());
        dto.setMissionId(entity.getMissionId());
        dto.setDate(entity.getDate());
        dto.setLocation(entity.getLocation());
        dto.setOutcome(entity.getOutcome().toString());
        dto.setDamageCost(entity.getDamageCost());
        dto.setComment(entity.getComment());
        dto.setNotes(entity.getNotes());

        if (entity.getCurses() != null) {
            dto.setCurses(entity.getCurses().stream()
                    .map(this::toCurseDto)
                    .collect(Collectors.toList()));
        }

        // Маги
        if (entity.getSorcerers() != null) {
            dto.setSorcerers(entity.getSorcerers().stream()
                    .map(this::toSorcererDto)
                    .collect(Collectors.toList()));
        }

        // Техники
        if (entity.getTechniques() != null) {
            dto.setTechniques(entity.getTechniques().stream()
                    .map(this::toTechniqueDto)
                    .collect(Collectors.toList()));
        }

        // Одиночные блоки
        dto.setEconomicAssessment(toEconomicAssessmentDto(entity.getEconomicAssessment()));
        dto.setCivilianImpact(toCivilianImpactDto(entity.getCivilianImpact()));
        dto.setEnemyActivity(toEnemyActivityDto(entity.getEnemyActivity()));
        dto.setEnvironmentConditions(toEnvironmentConditionsDto(entity.getEnvironmentConditions()));

        // Хронология
        if (entity.getOperationTimeline() != null) {
            dto.setOperationTimeline(entity.getOperationTimeline().stream()
                    .map(this::toOperationTimelineDto)
                    .collect(Collectors.toList()));
        }

        // Простые списки
        dto.setOperationTags(entity.getOperationTags());
        dto.setSupportUnits(entity.getSupportUnits());
        dto.setRecommendations(entity.getRecommendations());
        dto.setArtifactsRecovered(entity.getArtifactsRecovered());
        dto.setEvacuationZones(entity.getEvacuationZones());
        dto.setStatusEffects(entity.getStatusEffects());

        return dto;
    }



    private CurseDto toCurseDto(CurseEntity entity) {
        if (entity == null) return null;
        CurseDto dto = new CurseDto();
        dto.setName(entity.getName());
        dto.setThreatLevel(entity.getThreatLevel());
        return dto;
    }

    private SorcererDto toSorcererDto(SorcererEntity entity) {
        if (entity == null) return null;
        SorcererDto dto = new SorcererDto();
        dto.setName(entity.getName());
        dto.setRank(entity.getRank() != null ? entity.getRank().toString() : null);
        return dto;
    }

    private TechniqueDto toTechniqueDto(TechniqueEntity entity) {
        if (entity == null) return null;
        TechniqueDto dto = new TechniqueDto();
        dto.setName(entity.getName());
        dto.setType(entity.getType() != null ? entity.getType().toString() : null);
        dto.setOwner(entity.getOwner());
        dto.setDamage(entity.getDamage());
        return dto;
    }

    private EconomicAssessmentDto toEconomicAssessmentDto(EconomicAssessmentEntity entity) {
        if (entity == null) return null;
        EconomicAssessmentDto dto = new EconomicAssessmentDto();
        dto.setTotalDamageCost(entity.getTotalDamageCost());
        dto.setInfrastructureDamage(entity.getInfrastructureDamage());
        dto.setCommercialDamage(entity.getCommercialDamage());
        dto.setTransportDamage(entity.getTransportDamage());
        dto.setRecoveryEstimateDays(entity.getRecoveryEstimateDays());
        dto.setInsuranceCovered(entity.getInsuranceCovered());
        return dto;
    }

    private CivilianImpactDto toCivilianImpactDto(CivilianImpactEntity entity) {
        if (entity == null) return null;
        CivilianImpactDto dto = new CivilianImpactDto();
        dto.setEvacuated(entity.getEvacuated());
        dto.setInjured(entity.getInjured());
        dto.setMissing(entity.getMissing());
        dto.setPublicExposureRisk(entity.getPublicExposureRisk() != null ?
                entity.getPublicExposureRisk().toString() : null);
        return dto;
    }

    private EnemyActivityDto toEnemyActivityDto(EnemyActivityEntity entity) {
        if (entity == null) return null;
        EnemyActivityDto dto = new EnemyActivityDto();
        dto.setBehaviorType(entity.getBehaviorType());
        dto.setTargetPriority(entity.getTargetPriority());
        dto.setAttackPatterns(entity.getAttackPatterns());
        dto.setMobility(entity.getMobility() != null ? entity.getMobility().toString() : null);
        dto.setEscalationRisk(entity.getEscalationRisk() != null ?
                entity.getEscalationRisk().toString() : null);
        dto.setCountermeasuresUsed(entity.getCountermeasuresUsed());
        return dto;
    }

    private EnvironmentConditionsDto toEnvironmentConditionsDto(EnvironmentConditionsEntity entity) {
        if (entity == null) return null;
        EnvironmentConditionsDto dto = new EnvironmentConditionsDto();
        dto.setWeather(entity.getWeather());
        dto.setTimeOfDay(entity.getTimeOfDay());
        dto.setVisibility(entity.getVisibility() != null ? entity.getVisibility().toString() : null);
        dto.setCursedEnergyDensity(entity.getCursedEnergyDensity());
        return dto;
    }

    private OperationTimelineDto toOperationTimelineDto(OperationTimelineEntity entity) {
        if (entity == null) return null;
        OperationTimelineDto dto = new OperationTimelineDto();
        dto.setTimestamp(entity.getTimestamp());
        dto.setType(entity.getType());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public Mission toMission(MissionEntity entity) {
        if (entity == null) return null;

        Mission mission = new Mission();
        mission.setMissionId(entity.getMissionId());
        mission.setDate(entity.getDate());
        mission.setLocation(entity.getLocation());
        mission.setOutcome(Outcome.fromString(entity.getOutcome().toString()));
        mission.setDamageCost(entity.getDamageCost());
        mission.setComment(entity.getComment());
        mission.setNotes(entity.getNotes());

        // Преобразование списков (Entity → POJO)
        if (entity.getCurses() != null) {
            mission.setCurses(entity.getCurses().stream()
                    .map(this::toCurse)
                    .collect(Collectors.toList()));
        }

        if (entity.getSorcerers() != null) {
            mission.setSorcerers(entity.getSorcerers().stream()
                    .map(this::toSorcerer)
                    .collect(Collectors.toList()));
        }

        if (entity.getTechniques() != null) {
            mission.setTechniques(entity.getTechniques().stream()
                    .map(this::toTechnique)
                    .collect(Collectors.toList()));
        }

        // ... остальные поля

        return mission;
    }

    private Curse toCurse(CurseEntity entity) {
        Curse curse = new Curse();
        curse.setName(entity.getName());
        curse.setThreatLevel(ThreatLevel.fromString(entity.getThreatLevel()));
        return curse;
    }

    private Sorcerer toSorcerer(SorcererEntity entity) {
        Sorcerer sorcerer = new Sorcerer();
        sorcerer.setName(entity.getName());
        sorcerer.setRank(entity.getRank());
        return sorcerer;
    }

    private Technique toTechnique(TechniqueEntity entity) {
        Technique technique = new Technique();
        technique.setName(entity.getName());
        technique.setType(entity.getType());
        technique.setOwner(entity.getOwner());
        technique.setDamage(entity.getDamage());
        return technique;
    }

}
