package com.traffic.police.repos;

import com.traffic.police.models.ControlNumbersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ControlNumber extends JpaRepository<ControlNumbersEntity,Long> {
    ControlNumbersEntity findByCaseNumber(int casenumber);
}
