package MephiPackage.service;

import MephiPackage.entities.MissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<MissionEntity,Long> {
    boolean existsByMissionId(String missionId);
}
