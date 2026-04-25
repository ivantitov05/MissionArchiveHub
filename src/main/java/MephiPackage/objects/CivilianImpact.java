package MephiPackage.objects;

import MephiPackage.enums.PublicExposureRisk;

public class CivilianImpact {
    private Long evacuated;
    private Long injured;
    private Long missing;
    private PublicExposureRisk publicExposureRisk;

    public CivilianImpact() {}

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

    public void setPublicExposureRisk(String publicExposureRisk) {
        this.publicExposureRisk = PublicExposureRisk.fromString(publicExposureRisk);
    }
}