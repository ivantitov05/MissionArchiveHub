package MephiPackage.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//Содержит всю информацию о произошедшем событии.

public class ApplicationEvent {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private final EventType type;
    private final String message;
    private final LocalDateTime timestamp;
    private final Throwable exception;
    private final Object source;

    public ApplicationEvent(EventType type, String message) {
        this(type, message, null, null);
    }

    public ApplicationEvent(EventType type, String message, Object source) {
        this(type, message, source, null);
    }

    public ApplicationEvent(EventType type, String message, Throwable exception) {
        this(type, message, null, exception);
    }

    public ApplicationEvent(EventType type, String message, Object source, Throwable exception) {
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.source = source;
        this.exception = exception;
    }

    public EventType getType() { return type; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Throwable getException() { return exception; }
    public Object getSource() { return source; }

    public String format() {
        String formatted = FORMATTER.format(timestamp) + " [" + type + "] " + message;
        if (exception != null) {
            formatted += " - " + exception.getMessage();
        }
        return formatted;
    }

    @Override
    public String toString() {
        return format();
    }
}