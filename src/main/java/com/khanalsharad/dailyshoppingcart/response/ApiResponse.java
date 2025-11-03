package com.khanalsharad.dailyshoppingcart.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {

    private String message;

    private Object data;
}
