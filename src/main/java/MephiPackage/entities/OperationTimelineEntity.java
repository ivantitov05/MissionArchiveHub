package MephiPackage.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "operation_timelines")
public class OperationTimelineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                                    // ← исправлено: Id → id

    @Column(nullable = false)
    private String timestamp;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private MissionEntity mission;

    public OperationTimelineEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MissionEntity getMission() { return mission; }
    public void setMission(MissionEntity mission) { this.mission = mission; }
}