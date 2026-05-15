package MephiPackage.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "curses")
public class CurseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "threat_level")
    private String threatLevel;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private MissionEntity mission;

    public CurseEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getThreatLevel() { return threatLevel; }
    public void setThreatLevel(String threatLevel) { this.threatLevel = threatLevel; }

    public MissionEntity getMission() { return mission; }

    public void setMission(MissionEntity mission) {
        this.mission = mission;
    }
}