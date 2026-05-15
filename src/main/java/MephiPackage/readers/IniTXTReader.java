package MephiPackage.readers;

import MephiPackage.builders.MissionBuilder;
import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.objects.Mission;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IniTXTReader implements Reader {

    public IniTXTReader() {
    }

    @Override
    public Mission extract(File file) throws IOException {
        Map<String, String> data = new HashMap<>();
        List<String> lines = Files.readAllLines(file.toPath());

        if (lines.isEmpty()) {
            throw new IOException("Файл пуст");
        }

        String currentSection = "";
        int curseCounter = 0;
        int sorcererCounter = 0;
        int techniqueCounter = 0;
        int timelineCounter = 0;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("[") && line.endsWith("]")) {
                currentSection = line.substring(1, line.length() - 1);
                continue;
            }

            int eqIndex = line.indexOf('=');
            if (eqIndex == -1) continue;

            String key = line.substring(0, eqIndex).trim();
            String value = line.substring(eqIndex + 1).trim();
            if (value.isEmpty()) continue;

            String universalKey = toUniversalKey(currentSection, key,
                    curseCounter, sorcererCounter, techniqueCounter, timelineCounter);

            data.put(universalKey, value);

            // Обновляем счётчики
            if (currentSection.equals("CURSE") && key.equals("threatLevel")) {
                curseCounter++;
            }
            if (currentSection.equals("SORCERER") && key.equals("rank")) {
                sorcererCounter++;
            }
            if (currentSection.equals("TECHNIQUE") && key.equals("damage")) {
                techniqueCounter++;
            }
            if (currentSection.equals("OPERATION_TIMELINE") && key.equals("description")) {
                timelineCounter++;
            }
        }

        MissionBuilder builder = new MissionBuilderImpl();
        return builder.load(data).build();
    }

    private String toUniversalKey(String section, String key,
                                  int curseIdx, int sorcererIdx,
                                  int techniqueIdx, int timelineIdx) {
        if (section == null || section.isEmpty()) return key;

        switch (section) {
            case "MISSION": return key;
            case "CURSE": return "curse[" + curseIdx + "]." + key;
            case "SORCERER": return "sorcerer[" + sorcererIdx + "]." + key;
            case "TECHNIQUE": return "technique[" + techniqueIdx + "]." + key;
            case "ENVIRONMENT": return "environmentConditions." + key;
            case "ECONOMIC_ASSESSMENT": return "economicAssessment." + key;
            case "CIVILIAN_IMPACT": return "civilianImpact." + key;
            case "OPERATION_TIMELINE": return "operationTimeline[" + timelineIdx + "]." + key;
            case "OPERATION_TAGS": return "operationTags";
            case "SUPPORT_UNITS": return "supportUnits";
            case "RECOMMENDATIONS": return "recommendations";
            case "ARTIFACTS_RECOVERED": return "artifactsRecovered";
            case "EVACUATION_ZONES": return "evacuationZones";
            case "STATUS_EFFECTS": return "statusEffects";
            default: return key;
        }
    }
}