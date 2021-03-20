package com.traffic.police.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "roles", schema = "traffic_offence", catalog = "")
public class RolesEntity {
    private String roleid;
    private String roleName;
    private Collection<ApplicationUsersEntity> applicationUsersByRoleid;

    @Id
    @Column(name = "roleid")
    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    @Basic
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolesEntity that = (RolesEntity) o;
        return Objects.equals(roleid, that.roleid) &&
                Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleid, roleName);
    }


    @OneToMany(mappedBy = "rolesByRoleId")
    public Collection<ApplicationUsersEntity> getApplicationUsersByRoleid() {
        return applicationUsersByRoleid;
    }

    public void setApplicationUsersByRoleid(Collection<ApplicationUsersEntity> applicationUsersByRoleid) {
        this.applicationUsersByRoleid = applicationUsersByRoleid;
    }
}
