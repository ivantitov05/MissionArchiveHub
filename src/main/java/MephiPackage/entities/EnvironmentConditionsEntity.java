package MephiPackage.entities;

import MephiPackage.enums.Visibility;
import jakarta.persistence.*;

@Entity
@Table(name = "environment_conditions")
public class EnvironmentConditionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String weather;

    @Column(name = "time_of_day")
    private String timeOfDay;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Column(name = "cursed_energy_density")
    private Double cursedEnergyDensity;

    @OneToOne
    @JoinColumn(name = "mission_id", unique = true)
    private MissionEntity mission;

    public EnvironmentConditionsEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public String getTimeOfDay() { return timeOfDay; }
    public void setTimeOfDay(String timeOfDay) { this.timeOfDay = timeOfDay; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }

    public Double getCursedEnergyDensity() { return cursedEnergyDensity; }
    public void setCursedEnergyDensity(Double cursedEnergyDensity) {
        this.cursedEnergyDensity = cursedEnergyDensity;
    }

    public MissionEntity getMission() { return mission; }
    public void setMission(MissionEntity mission) { this.mission = mission; }
}