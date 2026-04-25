package MephiPackage.report;

import MephiPackage.objects.Mission;
import MephiPackage.objects.Curse;
import MephiPackage.objects.Sorcerer;
import MephiPackage.objects.Technique;

/**
 * Генератор краткого резюме миссии.
 * Содержит только основную информацию.
 */
public class SummaryReportGenerator implements ReportGenerator {

    private static final String SEPARATOR = "============================================================";
    private static final String LINE = "----------------------------------------";

    @Override
    public String generate(Mission mission) {
        if (mission == null) {
            return "Ошибка: миссия не загружена";
        }

        String result = "";
        result += SEPARATOR + "\n";
        result += "                 Кратская сводка миссии\n";
        result += SEPARATOR + "\n\n";

        result += "【ОСНОВНАЯ ИНФОРМАЦИЯ】\n";
        result += "  ID миссии:   " + nullSafe(mission.getMissionId()) + "\n";
        result += "  Дата:        " + nullSafe(mission.getDate()) + "\n";
        result += "  Локация:     " + nullSafe(mission.getLocation()) + "\n";
        result += "  Результат:   " + nullSafe(mission.getOutcome()) + "\n";
        result += "  Ущерб:       " + formatDamage(mission.getDamageCost()) + "\n\n";

        result += "Проклятия \n";
        if (mission.getCurses() == null || mission.getCurses().isEmpty()) {
            result += "  (нет данных о проклятиях)\n";
        } else {
            int count = mission.getCurses().size();
            result += "  Количество: " + count + "\n";

            Curse first = mission.getCurses().get(0);
            result += "  Основное:   " + nullSafe(first.getName());
            if (first.getThreatLevel() != null) {
                result += " (" + first.getThreatLevel() + ")";
            }
            result += "\n";

            if (count > 1) {
                result += "  (ещё " + (count - 1) + " проклятий\n";
            }
        }
        result += "\n";

        result += "Участники\n";
        if (mission.getSorcerers() == null || mission.getSorcerers().isEmpty()) {
            result += "  (нет данных об участниках)\n";
        } else {
            int count = mission.getSorcerers().size();
            result += "  Количество: " + count + "\n";
            result += "  Состав:     ";

            for (int i = 0; i < Math.min(count, 3); i++) {
                Sorcerer s = mission.getSorcerers().get(i);
                if (i > 0) result += ", ";
                result += nullSafe(s.getName());
            }

            if (count > 3) {
                result += " и ещё " + (count - 3) + " чел.";
            }
            result += "\n";
        }
        result += "\n";

        result += "Техники\n";
        if (mission.getTechniques() == null || mission.getTechniques().isEmpty()) {
            result += "  (нет данных о техниках)\n";
        } else {
            int count = mission.getTechniques().size();
            long totalDamage = 0;

            for (Technique t : mission.getTechniques()) {
                totalDamage += t.getDamage();
            }

            result += "  Количество: " + count + "\n";
            result += "  Суммарный урон: " + formatDamage(totalDamage) + "\n";

            Technique strongest = getStrongestTechnique(mission.getTechniques());
            if (strongest != null) {
                result += "  Сильнейшая: " + nullSafe(strongest.getName());
                result += " (" + formatDamage(strongest.getDamage()) + ")\n";
            }
        }
        result += "\n";

        result += SEPARATOR + "\n";
        result += "                 Конец сводки\n";
        result += SEPARATOR;

        return result;
    }

    private Technique getStrongestTechnique(java.util.List<Technique> techniques) {
        if (techniques == null || techniques.isEmpty()) {
            return null;
        }
        Technique strongest = techniques.get(0);
        for (Technique t : techniques) {
            if (t.getDamage() > strongest.getDamage()) {
                strongest = t;
            }
        }
        return strongest;
    }

    private String formatDamage(long damage) {
        if (damage == 0) return "0";
        return String.format("%,d", damage).replace(",", " ");
    }

    private String nullSafe(Object obj) {
        if (obj == null) return "—";
        String str = obj.toString();
        return str.isEmpty() ? "—" : str;
    }

    @Override
    public String getType() {
        return "summary";
    }
}