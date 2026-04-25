package MephiPackage.state;

import MephiPackage.objects.Mission;
import MephiPackage.collection.MissionCollection;

public interface MissionToolState {

    void display();

    void addMission(Mission mission);

    void addFolder(java.io.File folder);

    void showCurrentMission();

    void showStatistics();

    void showMissionList();

    String getModeName();

    int getMissionCount();

    MissionCollection getCollection();

    Mission getCurrentMission();
}
