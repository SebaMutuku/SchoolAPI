package com.traffic.police.repos;

import com.traffic.police.models.OffencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


public interface OffencesRepo extends JpaRepository<OffencesEntity, Long> {

    OffencesEntity findByOffenceid(@Param("offence") int offenceid );


}
