package MephiPackage.logging;

public class ConsoleLogger implements EventListener {

    @Override
    public void onEvent(ApplicationEvent event) {
        if (event.getType() == EventType.ERROR_OCCURRED) {
            System.err.println(event);
        } else {
            System.out.println(event);
        }
    }
}