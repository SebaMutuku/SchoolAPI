package com.traffic.police.repos;


import com.traffic.police.models.CrimeDescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeRepo extends JpaRepository<CrimeDescriptionEntity, Long> {
    @Query("SELECT a FROM CrimeDescriptionEntity a WHERE a.casenumberEntity.caseNumber= :casenumber ")
    CrimeDescriptionEntity findByCasenumber(int casenumber);

}
