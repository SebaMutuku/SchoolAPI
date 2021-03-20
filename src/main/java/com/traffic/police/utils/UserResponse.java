package com.traffic.police.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class UserResponse {
    String Message,token;
    HttpStatus httpStatus;
    Boolean requestStatus;
    String roleId;
    String fullname;
    String mobileNumber;
    int userid;

}
