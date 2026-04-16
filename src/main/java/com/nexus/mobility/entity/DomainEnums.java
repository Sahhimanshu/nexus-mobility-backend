package com.nexus.mobility.entity;

public final class DomainEnums {

    private DomainEnums() {
    }

    public enum UserRole {
        SUPER_ADMIN,
        ADMIN,
        COORDINATOR,
        STAFF,
        STUDENT
    }

    public enum ProgramType {
        SEMESTER_EXCHANGE,
        SUMMER_SCHOOL,
        JOINT_DEGREE,
        RESEARCH_FELLOWSHIP,
        INTERNSHIP
    }

    public enum PartnershipStatus {
        ACTIVE,
        PENDING,
        EXPIRING,
        EXPIRED
    }

    public enum EventType {
        FAIR,
        ORIENTATION,
        WORKSHOP,
        WEBINAR,
        INFO_SESSION
    }

    public enum VisitType {
        DELEGATION,
        CAMPUS_VISIT,
        PARTNER_MEETING,
        AUDIT,
        CEREMONY
    }

    public enum VisitStatus {
        PLANNED,
        CONFIRMED,
        COMPLETED,
        CANCELLED
    }

    public enum DocumentType {
        MOU,
        CONTRACT,
        AGREEMENT,
        POLICY,
        REPORT,
        OTHER
    }

    public enum NewsCategory {
        MOBILITY,
        PARTNERSHIP,
        CAMPUS,
        SCHOLARSHIP,
        ANNOUNCEMENT
    }

    public enum NotificationLevel {
        INFO,
        SUCCESS,
        WARNING,
        ERROR
    }

    public enum DefinitionModule {
        PROGRAM,
        PARTNERSHIP,
        EVENT,
        VISIT
    }
}
