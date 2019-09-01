package com.swipejobs.test.matchingengine;

public enum ConditionType
{
    LICENSE("License"),
    REQD_CERTIFICATES("Certificates"),
    DISTANCE("Distance"),
    SKILLS("Skills");
 
    private String criteriaType;
 
    ConditionType(String type) {
        this.criteriaType = type;
    }
 
    public String getCriteriaType() {
        return criteriaType;
    }
}
