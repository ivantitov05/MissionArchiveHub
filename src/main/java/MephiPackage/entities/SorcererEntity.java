package MephiPackage.entities;

import MephiPackage.enums.SorcererRank;
import jakarta.persistence.*;

@Entity
@Table(name = "sorcerers")
public class SorcererEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sorcerer_rank")
    private SorcererRank rank;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    private MissionEntity mission;

    public SorcererEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public SorcererRank getRank() { return rank; }
    public void setRank(SorcererRank rank) { this.rank = rank; }

    public MissionEntity getMission() { return mission; }
    public void setMission(MissionEntity mission) { this.mission = mission; }
}