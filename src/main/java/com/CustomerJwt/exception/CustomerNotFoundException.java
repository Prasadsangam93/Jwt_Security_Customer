package com.CustomerJwt.exception;

public class CustomerNotFoundException extends  RuntimeException{

    public  CustomerNotFoundException(String message){
        super(message);
    }
}
