package com.traffic.police.services;


import com.traffic.police.models.ControlNumbersEntity;
import com.traffic.police.models.CrimeDescriptionEntity;
import com.traffic.police.models.OffencesEntity;
import com.traffic.police.repos.ControlNumber;
import com.traffic.police.repos.CrimeRepo;
import com.traffic.police.repos.OffencesRepo;
import com.traffic.police.repos.UsersRepo;
import com.traffic.police.utils.GeneralRequest;
import com.traffic.police.utils.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


@Service
public class CrimeService {
    @Autowired
    CrimeRepo crimeRepo;
    @Autowired
    OffencesRepo offencesRepo;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    ControlNumber controlNumber;
    GeneralResponse response;

    public static String getRadom(int size) {
        if (size <= 1) {
            return null;
        }
        Random rad = new Random();
        String output = "";
        for (int i = 0; i < size; i++) {
            output += rad.nextInt(9);
        }
        return output;

    }

    public GeneralResponse getUserOffences(GeneralRequest<CrimeDescriptionEntity> generalRequest) {
        response = new GeneralResponse();
        CrimeDescriptionEntity crime = generalRequest.getPayload();
        CrimeDescriptionEntity offence = null;
        System.out.println("Case Number: " + crime.casenumber);
        try {
            offence = crimeRepo.findByCasenumber(crime.casenumber);
            response.setRequestStatus(true);
            if (offence != null) {
                if (offence.getOffencestatus().equalsIgnoreCase("Paid") || offence.getOffencestatus().equalsIgnoreCase("Closed")) {
                    response.setMessage("Case is closed or already paid");
                    response.setHttpStatus(HttpStatus.OK);
                    response.setPayload(null);
                } else {
                    response.setMessage("Success");
                    response.setHttpStatus(HttpStatus.OK);
                    response.setPayload(offence);
                }

            } else {
                response.setMessage("Not Found");
                response.setHttpStatus(HttpStatus.OK);
                response.setPayload(null);

            }
        } catch (Exception exception) {
            response.setRequestStatus(false);
            response.setMessage("An Error occurred");
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setPayload(null);
        }
        return response;
    }

    public GeneralResponse fetchAllCases(GeneralRequest<CrimeDescriptionEntity> generalRequest) {
        response = new GeneralResponse();
        CrimeDescriptionEntity descriptionEntity = new CrimeDescriptionEntity();
        try {
            response.setRequestStatus(true);
            response.setMessage("Success");
            response.setHttpStatus(HttpStatus.OK);
            System.out.println("Offence status : " + descriptionEntity.getOffencestatus());
            response.setPayload(crimeRepo.findAll());

        } catch (Exception exception) {
            exception.printStackTrace();
            response.setRequestStatus(false);
            response.setMessage("An Error occurred");
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setPayload(null);
        }
        return response;
    }

    public GeneralResponse fetchOffences(GeneralRequest<OffencesEntity> generalRequest) {
        response = new GeneralResponse();
        try {
            response.setRequestStatus(true);
            response.setMessage("Success");
            response.setHttpStatus(HttpStatus.OK);
            response.setPayload(offencesRepo.findAll());
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setRequestStatus(false);
            response.setMessage("An Error occurred");
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setPayload(null);
        }
        return response;
    }


    public GeneralResponse createCrime(GeneralRequest<CrimeDescriptionEntity> generalRequest) {
        response = new GeneralResponse();
        CrimeDescriptionEntity crime = generalRequest.getPayload();
        System.out.println("Offence desc" + crime.getOffence());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        String casenumber = new StringBuilder().append("5321").append(getRadom(2)).toString();

        int offence;
        try {
            OffencesEntity amount = offencesRepo.findByOffenceid(crime.getOffence());
            System.out.println(crime.getOffence());
            ControlNumbersEntity controlNumbersEntity = ControlNumbersEntity.builder()
                    .caseNumber(Integer.valueOf(casenumber))
                    .amount(amount.getOffenceamount())
                    .description(amount.getOffencedescription())
                    .offencelocation(crime.getOffencelocation())
                    .offendernationalid(Integer.parseInt(crime.getOffernderidnumber()))
                    .issuerofficer(crime.getIssuerofficer())
                    .offencesByOffenceid(amount)
                    .build();
            controlNumber.save(controlNumbersEntity);
            System.out.println("Save Control Number" + controlNumbersEntity.toString());
            System.out.println("Offence " + crime.toString());
            crime.setCasenumberEntity(controlNumbersEntity);
            crime.setCrime(amount.getOffencedescription());
            crime.setExpirydate(date);
            crime.setOffencedate(date);
            crime.setOffenceamount(amount.getOffenceamount());
            crime.setOffencestatus("Open");
            crime.setBalanceamount(amount.getOffenceamount());
            crime.setApplicationUsersByUserid(usersRepo.findByUserid(crime.userid));
            crime.casenumber = crime.getCasenumberEntity().getCaseNumber();
            crimeRepo.save(crime);
            controlNumber.save(controlNumbersEntity);
            response.setRequestStatus(true);
            response.setMessage("Success");
            response.setHttpStatus(HttpStatus.ACCEPTED);
            response.setPayload(crime);
        } catch (Exception exception) {

            exception.printStackTrace();
            response.setRequestStatus(false);
            response.setMessage("An Error occurred");
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setPayload(null);
        }
        return response;
    }

    public GeneralResponse payCrime(GeneralRequest<CrimeDescriptionEntity> generalRequest) {
        response = new GeneralResponse();
        CrimeDescriptionEntity crime = generalRequest.getPayload();
        response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
        int casenumber = crime.casenumber;
        response.setRequestStatus(false);
        response.setMessage("Error");
        try {
            CrimeDescriptionEntity descritionEntity = crimeRepo.findByCasenumber(casenumber);
            if (descritionEntity != null) {
                int currentBalance = Integer.parseInt(descritionEntity.getBalanceamount());
                int paymentAmount = Integer.parseInt(crime.getOffenceamount());
                int payBalance = currentBalance - paymentAmount;
                if (paymentAmount > currentBalance) {
                    response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
                    response.setRequestStatus(false);
                    response.setMessage("Amount more than outstanding balance");

                } else if (payBalance == 0) {
                    descritionEntity.setOffencestatus("closed");
                    descritionEntity.setBalanceamount(String.valueOf(payBalance));
                    crimeRepo.save(descritionEntity);
                    response.setRequestStatus(true);
                    response.setHttpStatus(HttpStatus.ACCEPTED);
                    response.setMessage("Payment Successful");
                } else {
                    response.setHttpStatus(HttpStatus.ACCEPTED);
                    descritionEntity.setBalanceamount(String.valueOf(payBalance));
                    crimeRepo.save(descritionEntity);
                    response.setRequestStatus(true);
                    response.setMessage("Payment Successful");
                }

            }
            response.setPayload(descritionEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setRequestStatus(false);
            response.setMessage("An Error occurred");
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setPayload(null);
        }
        return response;
    }

    public GeneralResponse createCrimeDesc(GeneralRequest<OffencesEntity> generalRequest) {
        response = new GeneralResponse();
        OffencesEntity offencesEntity = new OffencesEntity();
        OffencesEntity newOffence = generalRequest.getPayload();
        try {
            Integer offenceId = Integer.parseInt(new StringBuilder().append("2021").append(getRadom(2)).toString());
            offencesEntity.setOffenceid(offenceId);
            offencesEntity.setOffenceamount(String.valueOf(newOffence.getOffenceamount()));
            offencesEntity.setOffencedescription(newOffence.getOffencedescription());
            offencesRepo.save(offencesEntity);
            response.setHttpStatus(HttpStatus.ACCEPTED);
            response.setRequestStatus(true);
            response.setMessage("Success");
        } catch (Exception exception) {
            response.setRequestStatus(false);
            response.setMessage("An Error occurred");
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setPayload(null);
            exception.printStackTrace();
        }

        return response;
    }

    public GeneralResponse updateCrime(GeneralRequest<OffencesEntity> generalRequest) {
        response = new GeneralResponse();
        try {
            OffencesEntity offencesEntity = offencesRepo.findByOffenceid(generalRequest.getPayload().getOffenceid());
            System.out.println("........."+generalRequest.getPayload().getOffenceamount());
            if (offencesEntity != null) {
                offencesEntity.setOffenceid(generalRequest.getPayload().getOffenceid());
                offencesEntity.setOffenceamount(generalRequest.getPayload().getOffenceamount());
                offencesEntity.setOffencedescription(generalRequest.getPayload().getOffencedescription());
                offencesRepo.save(offencesEntity);
                response.setHttpStatus(HttpStatus.ACCEPTED);
                response.setPayload(offencesEntity);
                response.setRequestStatus(true);
                response.setMessage("Successfully update Offence ID  " + generalRequest.getPayload().getOffenceid());
            } else {
                response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
                response.setRequestStatus(true);
                response.setMessage("No Offence ID found");
            }
        } catch (Exception exception) {
            response.setRequestStatus(false);
            response.setMessage("An Error occurred");
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setPayload(null);
            exception.printStackTrace();
        }

        return response;
    }


}

