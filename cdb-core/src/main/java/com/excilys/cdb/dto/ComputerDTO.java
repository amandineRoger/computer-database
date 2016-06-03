package com.excilys.cdb.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

public class ComputerDTO {
    private static final String REGEX_DATE = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";

    private String id;

    @NotEmpty(message = "error.name")
    private String name;
    @Pattern(regexp = REGEX_DATE)
    private String introduced;
    @Pattern(regexp = REGEX_DATE)
    private String discontinued;
    private String companyId;
    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
