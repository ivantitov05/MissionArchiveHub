package MephiPackage.entities;

import MephiPackage.enums.TechniquesType;
import jakarta.persistence.*;

@Entity
@Table(name = "techniques")
public class TechniqueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "technique_type")
    private TechniquesType type;

    private long damage;

    private String owner;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private MissionEntity mission;

    public TechniqueEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public TechniquesType getType() { return type; }
    public void setType(TechniquesType type) { this.type = type; }

    public long getDamage() { return damage; }
    public void setDamage(long damage) { this.damage = damage; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }

    public MissionEntity getMission() { return mission; }
    public void setMission(MissionEntity mission) { this.mission = mission; }
}