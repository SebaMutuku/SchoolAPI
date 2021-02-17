//package com.traffic.police.services;
//
//import com.traffic.police.models.ControlNumbersEntity;
//import com.traffic.police.repos.CrimeRepo;
//import com.traffic.police.repos.OffencesRepo;
//import com.traffic.police.utils.GeneralRequest;
//import com.traffic.police.utils.GeneralResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//@Service
//public class OffenceService {
//    @Autowired
//    OffencesRepo offencesRepo;
//    @Autowired
//    CrimeRepo crimeRepo;
//    GeneralResponse response;
//
////    public GeneralResponse getOffencePerOffender(GeneralRequest<ControlNumbersEntity> request) {
////        response = new GeneralResponse();
////        ControlNumbersEntity casenumber = request.payload;
////        System.out.println("Offence number:"+casenumber.getCaseNumber());
////        try {
////            if (offence != null) {
////                response.setPayload(offence);
////                response.setPayload(offence);
////                response.setHttpStatus(HttpStatus.OK);
////                response.setMessage("Success");
////            } else {
////                response.setPayload(null);
////                response.setHttpStatus(HttpStatus.NOT_FOUND);
////                response.setMessage("Not Found");
////                response.setRequestStatus(true);
////
////            }
////        } catch (Exception exception) {
////            response.setPayload(null);
////            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
////            response.setMessage("Error occurred");
////            response.setRequestStatus(false);
////        }
////
////        return response;
////
////    }
//
//    public GeneralResponse getAll(GeneralRequest<ControlNumbersEntity> request) {
//        response = new GeneralResponse();
//        response.setPayload(null);
//        response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
//        response.setMessage("Error occurred");
//        response.setRequestStatus(false);
//        try {
//            response.setPayload(offencesRepo.findAllCases());
//            response.setHttpStatus(HttpStatus.OK);
//            response.setMessage("Success");
//            response.setRequestStatus(true);
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//
//        }
//        return response;
//    }
//}
