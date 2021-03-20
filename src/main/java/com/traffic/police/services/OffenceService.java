package com.traffic.police.services;

import com.traffic.police.models.ControlNumbersEntity;
import com.traffic.police.models.OffencesEntity;
import com.traffic.police.repos.CrimeRepo;
import com.traffic.police.repos.OffencesRepo;
import com.traffic.police.utils.GeneralRequest;
import com.traffic.police.utils.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OffenceService {
    @Autowired
    OffencesRepo offencesRepo;
    @Autowired
    CrimeRepo crimeRepo;
    GeneralResponse response;

    public GeneralResponse getOffenceAmount(GeneralRequest<OffencesEntity> generalRequest) {
        response = new GeneralResponse();
        response.setPayload(null);
        response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
        response.setMessage("Error occurred");
        response.setRequestStatus(false);
        System.out.println("My desc is "+generalRequest.getPayload().getOffencedescription());
        try {
            System.out.println("Fetching crime ids >>>>>>>>"+generalRequest.getPayload());
            response.setPayload(offencesRepo.findByOffencedescription(generalRequest.getPayload().getOffencedescription()));
            response.setHttpStatus(HttpStatus.OK);
            response.setMessage("Success");
            response.setRequestStatus(true);

        } catch (Exception exception) {
            exception.printStackTrace();
            response.setMessage("Error occurred");
            response.setRequestStatus(false);

        }
        return response;
    }
}
