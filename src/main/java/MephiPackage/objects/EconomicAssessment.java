package MephiPackage.objects;

public class EconomicAssessment {
    private Long totalDamageCost;
    private Long infrastructureDamage;
    private Long commercialDamage;
    private Long transportDamage;
    private Long recoveryEstimateDays;
    private Boolean insuranceCovered;

    public EconomicAssessment() {}

    public Long getTotalDamageCost() { return totalDamageCost; }
    public void setTotalDamageCost(Long totalDamageCost) { this.totalDamageCost = totalDamageCost; }

    public Long getInfrastructureDamage() { return infrastructureDamage; }
    public void setInfrastructureDamage(Long infrastructureDamage) { this.infrastructureDamage = infrastructureDamage; }

    public Long getCommercialDamage() { return commercialDamage; }
    public void setCommercialDamage(Long commercialDamage) { this.commercialDamage = commercialDamage; }

    public Long getTransportDamage() { return transportDamage; }
    public void setTransportDamage(Long transportDamage) { this.transportDamage = transportDamage; }

    public Long getRecoveryEstimateDays() { return recoveryEstimateDays; }
    public void setRecoveryEstimateDays(Long recoveryEstimateDays) { this.recoveryEstimateDays = recoveryEstimateDays; }

    public Boolean getInsuranceCovered() { return insuranceCovered; }
    public void setInsuranceCovered(Boolean insuranceCovered) { this.insuranceCovered = insuranceCovered; }
}