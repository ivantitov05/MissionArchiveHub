package MephiPackage.readers;

import MephiPackage.builders.MissionBuilder;
import MephiPackage.objects.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.File;
import java.io.IOException;

public class YAMLReader implements Reader {

    private final MissionBuilder builder;
    private final ObjectMapper yamlMapper;

    public YAMLReader(MissionBuilder builder) {
        this.builder = builder;
        this.yamlMapper = new YAMLMapper();
    }

    @Override
    public Mission extract(File file) throws IOException {
        System.out.println("yaml");
        builder.reset();

        JsonNode root = yamlMapper.readTree(file);

        parseBasicFields(root);

        parseCurse(root);

        parseSorcerers(root);

        parseTechniques(root);

        parseEconomicAssessment(root);

        parseCivilianImpact(root);

        parseEnvironmentConditions(root);

        parseOperationTimeline(root);

        parseOperationTags(root);

        parseSupportUnits(root);

        parseRecommendations(root);

        parseNotes(root);

        parseArtifactsRecovered(root);

        parseEvacuationZones(root);

        parseStatusEffects(root);

        return builder.getResult();
    }

    private void parseBasicFields(JsonNode root) {
        if (root.has("missionId")) {
            builder.buildMissionId(root.get("missionId").asText());
        }
        if (root.has("date")) {
            builder.buildDate(root.get("date").asText());
        }
        if (root.has("location")) {
            builder.buildLocation(root.get("location").asText());
        }
        if (root.has("outcome")) {
            builder.buildOutcome(root.get("outcome").asText());
        }
        if (root.has("damageCost")) {
            builder.buildDamageCost(root.get("damageCost").asLong());
        }
        if (root.has("comment")) {
            builder.buildComment(root.get("comment").asText());
        }
    }

    private void parseCurse(JsonNode root) {
        if (!root.has("curse")) return;

        JsonNode curseNode = root.get("curse");
        Curse curse = new Curse();

        if (curseNode.has("name")) {
            curse.setName(curseNode.get("name").asText());
        }
        if (curseNode.has("threatLevel")) {
            curse.setThreatLevel(curseNode.get("threatLevel").asText());
        }

        builder.buildCurse(curse);
    }

    private void parseSorcerers(JsonNode root) {
        if (!root.has("sorcerers") || !root.get("sorcerers").isArray()) return;

        for (JsonNode s : root.get("sorcerers")) {
            Sorcerer sorcerer = new Sorcerer();

            if (s.has("name")) {
                sorcerer.setName(s.get("name").asText());
            }
            if (s.has("rank")) {
                sorcerer.setRank(s.get("rank").asText());
            }

            builder.buildSorcerer(sorcerer);
        }
    }

    private void parseTechniques(JsonNode root) {
        if (!root.has("techniques") || !root.get("techniques").isArray()) return;

        for (JsonNode t : root.get("techniques")) {
            Technique technique = new Technique();

            if (t.has("name")) {
                technique.setName(t.get("name").asText());
            }
            if (t.has("type")) {
                technique.setType(t.get("type").asText());
            }
            if (t.has("owner")) {
                technique.setOwner(t.get("owner").asText());
            }
            if (t.has("damage")) {
                technique.setDamage(t.get("damage").asLong());
            }

            builder.buildTechnique(technique);
        }
    }

    private void parseEconomicAssessment(JsonNode root) {
        if (!root.has("economicAssessment")) return;

        JsonNode eaNode = root.get("economicAssessment");
        EconomicAssessment assessment = new EconomicAssessment();

        if (eaNode.has("totalDamageCost")) {
            assessment.setTotalDamageCost(eaNode.get("totalDamageCost").asLong());
        }
        if (eaNode.has("infrastructureDamage")) {
            assessment.setInfrastructureDamage(eaNode.get("infrastructureDamage").asLong());
        }
        if (eaNode.has("commercialDamage")) {
            assessment.setCommercialDamage(eaNode.get("commercialDamage").asLong());
        }
        if (eaNode.has("transportDamage")) {
            assessment.setTransportDamage(eaNode.get("transportDamage").asLong());
        }
        if (eaNode.has("recoveryEstimateDays")) {
            assessment.setRecoveryEstimateDays(eaNode.get("recoveryEstimateDays").asLong());
        }
        if (eaNode.has("insuranceCovered")) {
            assessment.setInsuranceCovered(eaNode.get("insuranceCovered").asBoolean());
        }

        builder.buildEconomicAssessment(assessment);
    }

    private void parseCivilianImpact(JsonNode root) {
        if (!root.has("civilianImpact")) return;

        JsonNode ciNode = root.get("civilianImpact");
        CivilianImpact impact = new CivilianImpact();

        if (ciNode.has("evacuated")) {
            impact.setEvacuated(ciNode.get("evacuated").asLong());
        }
        if (ciNode.has("injured")) {
            impact.setInjured(ciNode.get("injured").asLong());
        }
        if (ciNode.has("missing")) {
            impact.setMissing(ciNode.get("missing").asLong());
        }
        if (ciNode.has("publicExposureRisk")) {
            impact.setPublicExposureRisk(ciNode.get("publicExposureRisk").asText());
        }

        builder.buildCivilianImpact(impact);
    }

    private void parseEnvironmentConditions(JsonNode root) {
        if (!root.has("environmentConditions")) return;

        JsonNode envNode = root.get("environmentConditions");
        EnvironmentConditions conditions = new EnvironmentConditions();

        if (envNode.has("weather")) {
            conditions.setWeather(envNode.get("weather").asText());
        }
        if (envNode.has("timeOfDay")) {
            conditions.setTimeOfDay(envNode.get("timeOfDay").asText());
        }
        if (envNode.has("visibility")) {
            conditions.setVisibility(envNode.get("visibility").asText());
        }
        if (envNode.has("cursedEnergyDensity")) {
            conditions.setCursedEnergyDensity(envNode.get("cursedEnergyDensity").asDouble());
        }

        builder.buildEnvironmentConditions(conditions);
    }

    private void parseOperationTimeline(JsonNode root) {
        if (!root.has("operationTimeline") || !root.get("operationTimeline").isArray()) return;

        for (JsonNode otNode : root.get("operationTimeline")) {
            OperationTimeline timeline = new OperationTimeline();

            if (otNode.has("timestamp")) {
                timeline.setTimestamp(otNode.get("timestamp").asText());
            }
            if (otNode.has("type")) {
                timeline.setType(otNode.get("type").asText());
            }
            if (otNode.has("description")) {
                timeline.setDescription(otNode.get("description").asText());
            }

            builder.buildOperationTimeline(timeline);
        }
    }

    private void parseOperationTags(JsonNode root) {
        if (!root.has("operationTags") || !root.get("operationTags").isArray()) return;

        for (JsonNode tag : root.get("operationTags")) {
            builder.buildOperationTag(tag.asText());
        }
    }

    private void parseSupportUnits(JsonNode root) {
        if (!root.has("supportUnits") || !root.get("supportUnits").isArray()) return;

        for (JsonNode unit : root.get("supportUnits")) {
            builder.buildSupportUnit(unit.asText());
        }
    }

    private void parseRecommendations(JsonNode root) {
        if (!root.has("recommendations") || !root.get("recommendations").isArray()) return;

        for (JsonNode rec : root.get("recommendations")) {
            builder.buildRecommendation(rec.asText());
        }
    }

    private void parseNotes(JsonNode root) {
        if (root.has("notes")) {
            builder.buildNotes(root.get("notes").asText());
        }
    }

    private void parseArtifactsRecovered(JsonNode root) {
        if (!root.has("artifactsRecovered") || !root.get("artifactsRecovered").isArray()) return;

        for (JsonNode artifact : root.get("artifactsRecovered")) {
            builder.buildArtifactRecovered(artifact.asText());
        }
    }

    private void parseEvacuationZones(JsonNode root) {
        if (!root.has("evacuationZones") || !root.get("evacuationZones").isArray()) return;

        for (JsonNode zone : root.get("evacuationZones")) {
            builder.buildEvacuationZone(zone.asText());
        }
    }

    private void parseStatusEffects(JsonNode root) {
        if (!root.has("statusEffects") || !root.get("statusEffects").isArray()) return;

        for (JsonNode effect : root.get("statusEffects")) {
            builder.buildStatusEffect(effect.asText());
        }
    }
}