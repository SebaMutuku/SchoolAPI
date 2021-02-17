package com.traffic.police.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity()
@Table(name = "application_users", schema = "traffic_offence")
public class ApplicationUsersEntity {
    private int userid;
    private String fullName;
    private String email;
    private int idNumber;
    private Date regDate;
    private String mobileNumber;
    private String password;
    private String userstatus;
    @JsonIgnore
    private RolesEntity rolesByRoleId;
    @Transient
    public String roleid;

    @Override
    public String toString() {
        return "ApplicationUsersEntity{" +
                "userid=" + userid +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", idNumber=" + idNumber +
                ", regDate=" + regDate +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", password='" + password + '\'' +
                ", userstatus='" + userstatus + '\'' +
                ", rolesByRoleId=" + rolesByRoleId +
                ", roleid='" + roleid + '\'' +
                ", crimeDescriptionsByUserid=" + crimeDescriptionsByUserid +
                '}';
    }

    @JsonIgnore
    private Collection<CrimeDescriptionEntity> crimeDescriptionsByUserid;

    @Id
    @Column(name = "userid")
    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "id_number")
    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    @Basic
    @Column(name = "Reg_date")
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Basic
    @Column(name = "mobile_number")
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "userstatus")
    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationUsersEntity entity = (ApplicationUsersEntity) o;
        return userid == entity.userid &&
                idNumber == entity.idNumber &&
                Objects.equals(fullName, entity.fullName) &&
                Objects.equals(email, entity.email) &&
                Objects.equals(regDate, entity.regDate) &&
                Objects.equals(mobileNumber, entity.mobileNumber) &&
                Objects.equals(password, entity.password) &&
                Objects.equals(userstatus, entity.userstatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, fullName, email, idNumber, regDate, mobileNumber, password, userstatus);
    }


    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "roleid")
    public RolesEntity getRolesByRoleId() {
        return rolesByRoleId;
    }

    public void setRolesByRoleId(RolesEntity rolesByRoleId) {
        this.rolesByRoleId = rolesByRoleId;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "applicationUsersByUserid")
    public Collection<CrimeDescriptionEntity> getCrimeDescriptionsByUserid() {
        return crimeDescriptionsByUserid;
    }

    public void setCrimeDescriptionsByUserid(Collection<CrimeDescriptionEntity> crimeDescriptionsByUserid) {
        this.crimeDescriptionsByUserid = crimeDescriptionsByUserid;
    }

    @PostLoad
    private void postLoad(){
        this.roleid = rolesByRoleId.getRoleid();
    }
}
