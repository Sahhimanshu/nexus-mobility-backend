package com.nexus.mobility.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tenant_settings")
public class TenantSettings extends BaseEntity {

    @Column(nullable = false, unique = true)
    private UUID tenantId;

    @Column(nullable = false)
    private String timezone = "Asia/Calcutta";

    @Column(nullable = false)
    private String currency = "INR";

    @Column(nullable = false)
    private String locale = "en-IN";

    private String contactEmail;
    private String brandingColor = "#0F766E";

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getBrandingColor() {
        return brandingColor;
    }

    public void setBrandingColor(String brandingColor) {
        this.brandingColor = brandingColor;
    }
}
