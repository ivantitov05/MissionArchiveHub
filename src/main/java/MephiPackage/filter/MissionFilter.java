package MephiPackage.filter;

import MephiPackage.objects.Mission;


public class MissionFilter {

    public boolean matches(Mission mission) {
        // ЗАТЫЧКА: всегда true (показываем всё)
        return true;
    }
}