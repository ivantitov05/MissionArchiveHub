package MephiPackage.readers;

import MephiPackage.builders.MissionBuilder;
import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.objects.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class StreamReader implements Reader {

    private final MissionBuilder builder;

    public StreamReader(MissionBuilder builder) {
        this.builder = builder;
    }

    @Override
    public Mission extract(File file) throws IOException {
        System.out.println("пустой");
        List<String> lines = Files.readAllLines(file.toPath());

        if (lines.isEmpty()) {
            throw new IOException("Файл пуст");
        }

        builder.reset();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length < 2) continue;

            String prefix = parts[0];

            switch (prefix) {
                case "MISSION_CREATED":
                    if (parts.length >= 4) {
                        builder.buildMissionId(parts[1])
                                .buildDate(parts[2])
                                .buildLocation(parts[3]);
                    }
                    break;

                case "CURSE_DETECTED":
                    if (parts.length >= 3) {
                        Curse curse = new Curse();
                        curse.setName(parts[1]);
                        curse.setThreatLevel(parts[2]);
                        builder.buildCurse(curse);
                    }
                    break;

                case "SORCERER_ASSIGNED":
                    if (parts.length >= 3) {
                        Sorcerer sorcerer = new Sorcerer();
                        sorcerer.setName(parts[1]);
                        sorcerer.setRank(parts[2]);
                        builder.buildSorcerer(sorcerer);
                    }
                    break;

                case "TECHNIQUE_USED":
                    if (parts.length >= 5) {
                        Technique technique = new Technique();
                        technique.setName(parts[1]);
                        technique.setType(parts[2]);
                        technique.setOwner(parts[3]);
                        try {
                            technique.setDamage(Long.parseLong(parts[4]));
                        } catch (NumberFormatException e) {
                            technique.setDamage(0);
                        }
                        builder.buildTechnique(technique);
                    }
                    break;

                case "TIMELINE_EVENT":
                    if (parts.length >= 4) {
                        OperationTimeline event = new OperationTimeline();
                        event.setTimestamp(parts[1]);
                        event.setType(parts[2]);
                        event.setDescription(parts[3]);
                        builder.buildOperationTimeline(event);
                    }
                    break;

                case "MISSION_RESULT":
                    if (parts.length >= 2) {
                        builder.buildOutcome(parts[1]);
                        if (parts.length >= 3) {
                            String damagePart = parts[2];
                            if (damagePart.startsWith("damageCost=")) {
                                try {
                                    long cost = Long.parseLong(damagePart.substring(11));
                                    builder.buildDamageCost(cost);
                                } catch (NumberFormatException e) {
                                    // ignore
                                }
                            }
                        }
                    }
                    break;

                case "CIVILIAN_IMPACT":
                    CivilianImpact impact = parseCivilianImpact(line);
                    if (impact != null) {
                        builder.buildCivilianImpact(impact);
                    }
                    break;

                default:
                    System.err.println("Неизвестный префикс: " + prefix);
            }
        }

        return builder.getResult();
    }

    private CivilianImpact parseCivilianImpact(String line) {
        CivilianImpact impact = new CivilianImpact();

        String[] parts = line.split("\\|");
        boolean hasData = false;

        for (int i = 1; i < parts.length; i++) {
            String[] kv = parts[i].split("=");
            if (kv.length == 2) {
                try {
                    switch (kv[0]) {
                        case "evacuated":
                            impact.setEvacuated(Long.parseLong(kv[1]));
                            hasData = true;
                            break;
                        case "injured":
                            impact.setInjured(Long.parseLong(kv[1]));
                            hasData = true;
                            break;
                        case "missing":
                            impact.setMissing(Long.parseLong(kv[1]));
                            hasData = true;
                            break;
                    }
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        }

        return hasData ? impact : null;
    }
}