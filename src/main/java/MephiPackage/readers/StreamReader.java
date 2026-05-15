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

/**
 * Ридер для потокового формата (pipe-разделитель).
 * Только преобразует строки в Map универсальных ключей.
 */
public class StreamReader implements Reader {

    @Override
    public Mission extract(File file) throws IOException {
        Map<String, String> data = new HashMap<>();
        List<String> lines = Files.readAllLines(file.toPath());

        if (lines.isEmpty()) {
            throw new IOException("Файл пуст");
        }

        int curseCounter = 0;
        int sorcererCounter = 0;
        int techniqueCounter = 0;
        int timelineCounter = 0;

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length < 2) continue;

            String prefix = parts[0];

            switch (prefix) {
                case "MISSION_CREATED":
                    if (parts.length >= 4) {
                        data.put("missionId", parts[1]);
                        data.put("date", parts[2]);
                        data.put("location", parts[3]);
                    }
                    break;

                case "CURSE_DETECTED":
                    if (parts.length >= 3) {
                        data.put("curse[" + curseCounter + "].name", parts[1]);
                        data.put("curse[" + curseCounter + "].threatLevel", parts[2]);
                        curseCounter++;
                    }
                    break;

                case "SORCERER_ASSIGNED":
                    if (parts.length >= 3) {
                        data.put("sorcerer[" + sorcererCounter + "].name", parts[1]);
                        data.put("sorcerer[" + sorcererCounter + "].rank", parts[2]);
                        sorcererCounter++;
                    }
                    break;

                case "TECHNIQUE_USED":
                    if (parts.length >= 5) {
                        data.put("technique[" + techniqueCounter + "].name", parts[1]);
                        data.put("technique[" + techniqueCounter + "].type", parts[2]);
                        data.put("technique[" + techniqueCounter + "].owner", parts[3]);
                        data.put("technique[" + techniqueCounter + "].damage", parts[4]);
                        techniqueCounter++;
                    }
                    break;

                case "TIMELINE_EVENT":
                    if (parts.length >= 4) {
                        data.put("operationTimeline[" + timelineCounter + "].timestamp", parts[1]);
                        data.put("operationTimeline[" + timelineCounter + "].type", parts[2]);
                        data.put("operationTimeline[" + timelineCounter + "].description", parts[3]);
                        timelineCounter++;
                    }
                    break;

                case "MISSION_RESULT":
                    if (parts.length >= 2) {
                        data.put("outcome", parts[1]);
                        if (parts.length >= 3) {
                            String damagePart = parts[2];
                            if (damagePart.startsWith("damageCost=")) {
                                data.put("damageCost", damagePart.substring(11));
                            }
                        }
                    }
                    break;

                case "CIVILIAN_IMPACT":
                    parseCivilianImpact(line, data);
                    break;

                default:
                    System.err.println("Неизвестный префикс: " + prefix);
            }
        }

        MissionBuilder builder = new MissionBuilderImpl();
        return builder.load(data).build();
    }

    private void parseCivilianImpact(String line, Map<String, String> data) {
        String[] parts = line.split("\\|");

        for (int i = 1; i < parts.length; i++) {
            String[] kv = parts[i].split("=");
            if (kv.length == 2) {
                switch (kv[0]) {
                    case "evacuated":
                        data.put("civilianImpact.evacuated", kv[1]);
                        break;
                    case "injured":
                        data.put("civilianImpact.injured", kv[1]);
                        break;
                    case "missing":
                        data.put("civilianImpact.missing", kv[1]);
                        break;
                }
            }
        }
    }
}