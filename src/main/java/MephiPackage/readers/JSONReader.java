package MephiPackage.readers;

import MephiPackage.objects.Mission;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONReader implements Reader {

    public JSONReader(){}

    @Override
    public Mission extract(File file) throws IOException {
        System.out.println("json");
        ObjectMapper mapper = new ObjectMapper();
        Mission mission = mapper.readValue(file, Mission.class);

        if (mission.getMissionId() == null || mission.getMissionId().isEmpty()) {
            throw new IOException("Файл не содержит обязательного поля missionId");
        }

        if (mission.getCurses() == null || mission.getCurses().isEmpty()) {
            throw new IOException("Отсутствует обязательный блок curse");
        }

        return mission;
    }
}
