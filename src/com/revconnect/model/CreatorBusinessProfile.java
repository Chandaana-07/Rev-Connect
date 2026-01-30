package com.revconnect.model;

public class CreatorBusinessProfile {

    private int userId;
    private String accountType;
    private String displayName;
    private String category;
    private String bio;
    private String address;
    private String contactInfo;
    private String website;
    private String socialLinks;
    private String businessHours;
    private String externalLinks;

    public CreatorBusinessProfile() {}

    // GETTERS & SETTERS
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getSocialLinks() { return socialLinks; }
    public void setSocialLinks(String socialLinks) { this.socialLinks = socialLinks; }

    public String getBusinessHours() { return businessHours; }
    public void setBusinessHours(String businessHours) { this.businessHours = businessHours; }

    public String getExternalLinks() { return externalLinks; }
    public void setExternalLinks(String externalLinks) { this.externalLinks = externalLinks; }
}
