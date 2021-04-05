package com.zut.lpb.api;

public interface Api {
    String BASE_URL = "http://192.168.20.32:8080";
   // String BASE_URL = "http://192.168.1.105:8080";
    String FIND_USER_ALL = BASE_URL+"/findUserAll";
    String UPDATE_USER = BASE_URL+"/updateUserInfo";
    String SIGN_UP = BASE_URL+"/signUp";
    String LOGIN = BASE_URL+"/login";
}
