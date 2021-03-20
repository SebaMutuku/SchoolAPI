package com.traffic.police.utils;

public class GeneralRequest<T> {
    public String token;
    public T payload;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
