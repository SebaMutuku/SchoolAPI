package com.traffic.police.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "control_numbers", schema = "traffic_offence")
@ToString(exclude = {"crimeDescriptionsByCaseNumber", "offencesByOffenceid"})
public class ControlNumbersEntity {
    private int caseNumber;
    private String issuerofficer;
    private String amount;
    private String offencelocation;
    private String description;
    private int offendernationalid;
    private OffencesEntity offencesByOffenceid;
    private CrimeDescriptionEntity crimeDescriptionsByCaseNumber;

    @Id
    @Column(name = "case_number")
    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    @Basic
    @Column(name = "issuerofficer")
    public String getIssuerofficer() {
        return issuerofficer;
    }

    public void setIssuerofficer(String issuerofficer) {
        this.issuerofficer = issuerofficer;
    }

    @Basic
    @Column(name = "Amount")
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "offencelocation")
    public String getOffencelocation() {
        return offencelocation;
    }

    public void setOffencelocation(String offencelocation) {
        this.offencelocation = offencelocation;
    }

    @Basic
    @Column(name = "Description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "Offendernationalid")
    public int getOffendernationalid() {
        return offendernationalid;
    }

    public void setOffendernationalid(int offendernationalid) {
        this.offendernationalid = offendernationalid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControlNumbersEntity that = (ControlNumbersEntity) o;
        return caseNumber == that.caseNumber &&
                offendernationalid == that.offendernationalid &&
                Objects.equals(issuerofficer, that.issuerofficer) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(offencelocation, that.offencelocation) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseNumber, issuerofficer, amount, offencelocation, description, offendernationalid);
    }
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "offenceid", referencedColumnName = "offenceid")
    public OffencesEntity getOffencesByOffenceid() {
        return offencesByOffenceid;
    }

    public void setOffencesByOffenceid(OffencesEntity offencesByOffenceid) {
        this.offencesByOffenceid = offencesByOffenceid;
    }
    @JsonIgnore
    @OneToOne(mappedBy = "casenumberEntity")
    public CrimeDescriptionEntity getCrimeDescriptionsByCaseNumber() {
        return crimeDescriptionsByCaseNumber;
    }

    public void setCrimeDescriptionsByCaseNumber(CrimeDescriptionEntity crimeDescriptionsByCaseNumber) {
        this.crimeDescriptionsByCaseNumber = crimeDescriptionsByCaseNumber;
    }

}
