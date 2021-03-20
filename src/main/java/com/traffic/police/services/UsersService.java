package com.traffic.police.services;


import com.traffic.police.models.ApplicationUsersEntity;
import com.traffic.police.repos.RolesRepo;
import com.traffic.police.repos.UsersRepo;
import com.traffic.police.utils.GeneralRequest;
import com.traffic.police.utils.GeneralResponse;
import com.traffic.police.utils.UserRequest;
import com.traffic.police.utils.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;

@Service
public class UsersService {
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RolesRepo rolesRepo;
    UserResponse userResponse;
    GeneralResponse response;

    public UserResponse loginUser(UserRequest userRequest) {
        userResponse = new UserResponse();
        userResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
        userResponse.setMessage("Unauthorized");
        userResponse.setRequestStatus(false);
        userResponse.setToken(null);
        try {
            String password = encryptPassword(userRequest.getPassword());
            ApplicationUsersEntity users = usersRepo.findByEmailAndPassword(userRequest.getUsername(), password);
            if (users != null) {
                if ((users.getUserstatus().equalsIgnoreCase("Active"))) {
                    userResponse.setMessage("Successfully Logged In");
                    userResponse.setRequestStatus(true);
                    userResponse.setHttpStatus(HttpStatus.OK);
                    String role = null, roleId;
                    System.out.println("Roles is: " + users.getRolesByRoleId().getRoleid());
                    roleId = users.getRolesByRoleId().getRoleid();
                    userResponse.setRoleId(roleId);
                    userResponse.setToken("JPW1ISL3qPf2phv6ihHe0XKhBHnfQcnpOsx8NlmbILdigZmv29C14jD0KVmq8cWz");
                    userResponse.setFullname(users.getFullName());
                    userResponse.setUserid(users.getUserid());
                    userResponse.setMobileNumber(users.getMobileNumber());
                } else {
                    userResponse.setToken(null);
                    userResponse.setRequestStatus(true);
                    userResponse.setHttpStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
                    userResponse.setMessage("User is inactive");
                }
            } else {
                userResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
                userResponse.setMessage("Invalid Credentials");
                userResponse.setRequestStatus(true);
                userResponse.setToken(null);
                userResponse.setRoleId(null);

            }
            System.out.println("Response message" + users);
        } catch (Exception exception) {
            exception.printStackTrace();

        }

        return userResponse;
    }

    public GeneralResponse createUser(GeneralRequest<ApplicationUsersEntity> request) {
        response = new GeneralResponse();
        ApplicationUsersEntity users = request.getPayload();
        ApplicationUsersEntity userEXist = usersRepo.findByEmail(users.getEmail());
        ApplicationUsersEntity usersInstance = new ApplicationUsersEntity();
        try {
            if (userEXist != null) {
                response.setHttpStatus(HttpStatus.CONFLICT);
                response.setMessage(users.getEmail() + " Already exist");
                response.setRequestStatus(true);

            } else {
                usersInstance.setEmail(users.getEmail());
                usersInstance.setIdNumber(users.getIdNumber());
                usersInstance.setMobileNumber(users.getMobileNumber());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new java.util.Date();
                usersInstance.setRegDate(new java.sql.Date(System.currentTimeMillis()));
                String role = (String.valueOf(users.roleid)), userRole;
                switch (role) {
                    case "Admin":
                        userRole = "1";
                        break;
                    case "TrafficOffice":
                        userRole = "2";
                        break;
                    default:
                        userRole = "3";
                }

                usersInstance.setFullName(users.getFullName());
                usersInstance.setRolesByRoleId(rolesRepo.findByRoleid(userRole));
                usersInstance.setUserstatus("Active");
                usersInstance.setPassword(encryptPassword(users.getPassword()));
                usersRepo.save(usersInstance);
                response.setHttpStatus(HttpStatus.ACCEPTED);
                response.setMessage("Success");
                response.setRequestStatus(true);
                response.setPayload(usersInstance);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setMessage("An error Occurred");
            response.setRequestStatus(false);
            response.setPayload(null);

        }
        return response;

    }

    public GeneralResponse disable(GeneralRequest<ApplicationUsersEntity> request) {
        response = new GeneralResponse();
        ApplicationUsersEntity users = request.getPayload();
        ApplicationUsersEntity userEXist = usersRepo.findByEmail(users.getEmail());
        response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
        response.setRequestStatus(false);
        try {
            if (userEXist != null) {
                if (userEXist.getUserstatus().equalsIgnoreCase("Inactive")) {
                    response.setMessage("User already Deactivated");
                    response.setPayload(null);
                } else {
                    userEXist.setUserstatus("Inactive");
                    usersRepo.save(userEXist);
                    response.setHttpStatus(HttpStatus.ACCEPTED);
                    response.setRequestStatus(true);
                    response.setMessage("Success");
                    response.setPayload(userEXist);


                }

            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setMessage("Not Found");
                response.setRequestStatus(true);
                response.setPayload(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setMessage("An error Occurred");
            response.setRequestStatus(false);
            response.setPayload(null);

        }
        return response;

    }

    public GeneralResponse enable(GeneralRequest<ApplicationUsersEntity> request) {
        response = new GeneralResponse();
        ApplicationUsersEntity users = request.getPayload();
        ApplicationUsersEntity userEXist = usersRepo.findByEmail(users.getEmail());
        response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
        response.setRequestStatus(false);
        try {
            if (userEXist != null) {
                if (userEXist.getUserstatus().equalsIgnoreCase("Active")) {
                    response.setMessage("User already active");
                    response.setPayload(null);
                } else {
                    userEXist.setUserstatus("Active");
                    usersRepo.save(userEXist);
                    response.setHttpStatus(HttpStatus.ACCEPTED);
                    response.setRequestStatus(true);
                    response.setMessage("Success");
                    response.setPayload(userEXist);


                }

            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setMessage("Not Found");
                response.setRequestStatus(true);
                response.setPayload(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setMessage("An error Occurred");
            response.setRequestStatus(false);
            response.setPayload(null);

        }
        return response;

    }

    public GeneralResponse reset(GeneralRequest<ApplicationUsersEntity> request) {
        response = new GeneralResponse();
        ApplicationUsersEntity users = request.getPayload();
        ApplicationUsersEntity userEXist = usersRepo.findByEmail(users.getEmail());
        response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
        response.setRequestStatus(false);
        try {
            if (userEXist != null) {
                System.out.println(userEXist.getUserstatus());
                if (userEXist.getUserstatus().equalsIgnoreCase("Active")) {
                    userEXist.setPassword(encryptPassword("12345678"));
                    usersRepo.save(userEXist);
                    response.setHttpStatus(HttpStatus.ACCEPTED);
                    response.setRequestStatus(true);
                    response.setMessage("Success");
                    response.setPayload(userEXist);
                } else {
                    response.setRequestStatus(true);
                    response.setMessage("User is inactive");
                    response.setPayload(null);
                }

            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setMessage("Not Found");
                response.setRequestStatus(true);
                response.setPayload(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setMessage("An error Occurred");
            response.setRequestStatus(false);
            response.setPayload(null);

        }
        return response;

    }

    public GeneralResponse updateSecurity(GeneralRequest<ApplicationUsersEntity> request) {
        response = new GeneralResponse();
        ApplicationUsersEntity users = request.getPayload();
        ApplicationUsersEntity userEXist = usersRepo.findByEmail(users.getEmail());
        response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
        response.setRequestStatus(false);
        try {
            if (userEXist != null) {
                System.out.println(userEXist.getUserstatus());
                if (userEXist.getUserstatus().equalsIgnoreCase("Active")) {
                    userEXist.setPassword(encryptPassword(users.getPassword()));
                    usersRepo.save(userEXist);
                    response.setHttpStatus(HttpStatus.ACCEPTED);
                    response.setRequestStatus(true);
                    response.setMessage("Success");
                    response.setPayload(userEXist);
                } else {
                    response.setRequestStatus(true);
                    response.setMessage("User is inactive");
                    response.setPayload(null);
                }

            } else {
                response.setHttpStatus(HttpStatus.NOT_FOUND);
                response.setMessage("Not Found");
                response.setRequestStatus(true);
                response.setPayload(null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
            response.setMessage("An error Occurred");
            response.setRequestStatus(false);
            response.setPayload(null);

        }
        return response;

    }

    public String encryptPassword(String password) {
        String salt = "zvQTRjptMiCf3GxyQCHpss70E0Y6bTIg";
        byte[] keyBytes = Base64.getDecoder().decode(salt.getBytes());
        SecretKey key = new SecretKeySpec(keyBytes, "DESede");
        String encryptedPassword = null, encryptionScheme = "DESede";
        try {
            Cipher cipher = Cipher.getInstance(encryptionScheme);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainpassword = password.getBytes(StandardCharsets.UTF_8);
            byte[] encryptPassword = cipher.doFinal(plainpassword);
            encryptedPassword = Base64.getEncoder().encodeToString(encryptPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(encryptedPassword);
        return encryptedPassword;
    }

    public GeneralResponse getAllUsers(GeneralRequest<ApplicationUsersEntity> request) {
        response = new GeneralResponse();
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage("Success");
        response.setRequestStatus(true);
        response.setPayload(usersRepo.findAll());
        return response;

    }


}
