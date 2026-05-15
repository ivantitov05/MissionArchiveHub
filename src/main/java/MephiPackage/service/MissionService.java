package MephiPackage.service;

import MephiPackage.core.MissionReader;
import MephiPackage.dto.MissionResponseDto;
import MephiPackage.entities.MissionEntity;
import MephiPackage.objects.Mission;
import MephiPackage.report.ReportFactory;
import MephiPackage.report.ReportGenerator;
import MephiPackage.service.MissionConverter;
import MephiPackage.service.MissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissionService {

    private final MissionReader missionReader;
    private final MissionRepository repository;
    private final MissionConverter converter;

    public MissionService(MissionReader missionReader,
                          MissionRepository repository,
                          MissionConverter converter) {
        this.missionReader = missionReader;
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional
    public Long saveMissionFromFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("mission_", ".tmp");
        file.transferTo(tempFile);

        try {
            Mission mission = missionReader.readMission(tempFile);

            if (repository.existsByMissionId(mission.getMissionId())) {
                throw new IOException("Миссия с ID '" + mission.getMissionId() + "' уже существует в архиве");
            }

            MissionEntity entity = converter.toEntity(mission);
            MissionEntity saved = repository.save(entity);
            return saved.getId();

        } catch (DataIntegrityViolationException e) {
            // На случай гонки (если проверка не сработала)
            throw new IOException("Миссия с таким ID уже существует");
        } finally {
            tempFile.delete();
        }
    }

    public MissionResponseDto findById(Long id) {
        MissionEntity entity = repository.findById(id).orElse(null);
        return converter.toDto(entity);
    }

    public List<MissionResponseDto> findAll() {
        return repository.findAll().stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public String generateReport(Long id, String type) {
        MissionEntity entity = repository.findById(id).orElse(null);
        if (entity == null) {
            throw new IllegalArgumentException("Миссия не найдена");
        }

        Mission mission = converter.toMission(entity);

        ReportGenerator generator = ReportFactory.getGenerator(type);
        return generator.generate(mission);
    }
}