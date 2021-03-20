package com.traffic.police.repos;

import com.traffic.police.models.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolesRepo  extends JpaRepository<RolesEntity,Long> {
    RolesEntity findByRoleid(String roleid);
}
