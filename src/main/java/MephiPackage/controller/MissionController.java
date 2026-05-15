package MephiPackage.controller;

import MephiPackage.dto.MissionResponseDto;
import MephiPackage.service.MissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/missions")
public class MissionController {

    private final MissionService missionService;

    // MissionReader не нужен в контроллере — вся логика в сервисе
    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    /**
     * Загрузка файла миссии
     * @param file файл в формате JSON, XML, TXT или YAML
     * @return ID сохранённой миссии
     */
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<UploadResponse> uploadMission(@RequestParam("file") MultipartFile file) {
        try {
            Long id = missionService.saveMissionFromFile(file);
            return ResponseEntity.ok(new UploadResponse(id, "Миссия сохранена"));
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(new UploadResponse(null, "Ошибка: " + e.getMessage()));
        }
    }

    /**
     * Получить миссию по ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MissionResponseDto> getMission(@PathVariable Long id) {
        MissionResponseDto mission = missionService.findById(id);
        if (mission == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mission);
    }

    /**
     * Получить все миссии
     */
    @GetMapping
    public ResponseEntity<List<MissionResponseDto>> getAllMissions() {
        return ResponseEntity.ok(missionService.findAll());
    }

    /**
     * Удалить миссию
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        boolean deleted = missionService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    public static class UploadResponse {
        private final Long id;
        private final String message;

        public UploadResponse(Long id, String message) {
            this.id = id;
            this.message = message;
        }

        public Long getId() { return id; }
        public String getMessage() { return message; }
    }

    /**
     * Сгенерировать отчёт по миссии
     * @param id ID миссии
     * @param type тип отчёта (summary, detailed, risk)
     * @return текст отчёта
     */
    @GetMapping("/{id}/report")
    public ResponseEntity<String> generateReport(
            @PathVariable Long id,
            @RequestParam(defaultValue = "detailed") String type) {

        try {
            String report = missionService.generateReport(id, type);
            return ResponseEntity.ok(report);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка генерации отчёта: " + e.getMessage());
        }
    }
}