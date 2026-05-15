package MephiPackage.builders;

import MephiPackage.objects.Mission;
import java.util.Map;

/**
 * Строитель для создания объекта Mission.
 * Соответствует паттерну Builder из GoF.
 */
public interface MissionBuilder {

    /**
     * Загружает Map с данными миссии в билдер.
     * Ключи имеют формат:
     * - простые поля: "missionId", "date", "location", "outcome", "damageCost", "comment"
     * - вложенные объекты: "curse.name", "curse.threatLevel"
     * - массивы: "sorcerer[0].name", "technique[0].type", "operationTimeline[0].timestamp"
     *
     * @param data Map, где ключ — путь к полю, значение — строковое представление
     * @return this (для chaining)
     */
    MissionBuilder load(Map<String, String> data);

    /**
     * Загружает одну пару ключ-значение.
     */
    MissionBuilder load(String key, String value);

    /**
     * Создаёт объект Mission из загруженных данных.
     */
    Mission build();

    /**
     * Сбрасывает билдер для переиспользования.
     */
    void reset();
}