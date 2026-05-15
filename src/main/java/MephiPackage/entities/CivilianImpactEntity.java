package MephiPackage.entities;

import MephiPackage.enums.PublicExposureRisk;
import jakarta.persistence.*;

@Entity
@Table(name = "civilian_impacts")
public class CivilianImpactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long evacuated;
    private Long injured;
    private Long missing;

    @Enumerated(EnumType.STRING)
    @Column(name = "public_exposure_risk")
    private PublicExposureRisk publicExposureRisk;

    @OneToOne
    @JoinColumn(name = "mission_id", unique = true)
    private MissionEntity mission;

    public CivilianImpactEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEvacuated() { return evacuated; }
    public void setEvacuated(Long evacuated) { this.evacuated = evacuated; }

    public Long getInjured() { return injured; }
    public void setInjured(Long injured) { this.injured = injured; }

    public Long getMissing() { return missing; }
    public void setMissing(Long missing) { this.missing = missing; }

    public PublicExposureRisk getPublicExposureRisk() { return publicExposureRisk; }
    public void setPublicExposureRisk(PublicExposureRisk publicExposureRisk) {
        this.publicExposureRisk = publicExposureRisk;
    }

    public MissionEntity getMission() { return mission; }
    public void setMission(MissionEntity mission) { this.mission = mission; }
}