package MephiPackage.logging;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Издатель событий (паттерн Наблюдатель).
 * Управляет подписками и уведомляет наблюдателей.
 */
public class EventManager {

    private static EventManager instance;
    private final Map<EventType, List<EventListener>> listeners;

    private EventManager() {
        listeners = new EnumMap<>(EventType.class);
        for (EventType type : EventType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    /**
     * Подписать наблюдателя на конкретный тип события.
     */
    public void subscribe(EventType eventType, EventListener listener) {
        listeners.get(eventType).add(listener);
    }

    /**
     * Подписать наблюдателя на все типы событий.
     */
    public void subscribeToAll(EventListener listener) {
        for (EventType type : EventType.values()) {
            subscribe(type, listener);
        }
    }

    /**
     * Отписать наблюдателя от события.
     */
    public void unsubscribe(EventType eventType, EventListener listener) {
        listeners.get(eventType).remove(listener);
    }

    /**
     * Уведомить всех наблюдателей о событии.
     */
    public void notify(EventType type, String message) {
        notify(new ApplicationEvent(type, message));
    }

    public void notify(EventType type, String message, Object source) {
        notify(new ApplicationEvent(type, message, source));
    }

    public void notify(EventType type, String message, Throwable exception) {
        notify(new ApplicationEvent(type, message, exception));
    }

    public void notify(ApplicationEvent event) {
        for (EventListener listener : listeners.get(event.getType())) {
            listener.onEvent(event);
        }
    }
}
