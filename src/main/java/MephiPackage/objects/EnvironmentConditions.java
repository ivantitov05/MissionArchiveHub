package MephiPackage.objects;

import MephiPackage.enums.Visibility;

public class EnvironmentConditions {
    private String weather;
    private String timeOfDay;
    private Visibility visibility;
    private Double cursedEnergyDensity;

    public EnvironmentConditions() {}

    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }

    public String getTimeOfDay() { return timeOfDay; }
    public void setTimeOfDay(String timeOfDay) { this.timeOfDay = timeOfDay; }

    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }

    public void setVisibility(String visibility) {
        this.visibility = Visibility.fromString(visibility);
    }

    public Double getCursedEnergyDensity() { return cursedEnergyDensity; }
    public void setCursedEnergyDensity(Double cursedEnergyDensity) {
        this.cursedEnergyDensity = cursedEnergyDensity;
    }
}