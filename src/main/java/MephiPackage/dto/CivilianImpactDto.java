package MephiPackage.dto;

public class CivilianImpactDto {
    private Long evacuated;
    private Long injured;
    private Long missing;
    private String publicExposureRisk;

    public Long getEvacuated() {
        return evacuated;
    }

    public void setEvacuated(Long evacuated) {
        this.evacuated = evacuated;
    }

    public Long getInjured() {
        return injured;
    }

    public void setInjured(Long injured) {
        this.injured = injured;
    }

    public Long getMissing() {
        return missing;
    }

    public void setMissing(Long missing) {
        this.missing = missing;
    }

    public String getPublicExposureRisk() {
        return publicExposureRisk;
    }

    public void setPublicExposureRisk(String publicExposureRisk) {
        this.publicExposureRisk = publicExposureRisk;
    }
}