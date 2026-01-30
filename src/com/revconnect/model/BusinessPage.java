package com.revconnect.model;

import java.io.Serializable;

public class BusinessPage implements Serializable {
    private String businessName;
    private String ownerName;
    private String description;

    public BusinessPage(String businessName, String ownerName, String description) {
        this.businessName = businessName;
        this.ownerName = ownerName;
        this.description = description;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Business Name: " + businessName +
               "\nOwner: " + ownerName +
               "\nDescription: " + description;
    }
}
