package com.khanalsharad.dailyshoppingcart.exception;

public class CategoryNotFoundException  extends RuntimeException{

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
