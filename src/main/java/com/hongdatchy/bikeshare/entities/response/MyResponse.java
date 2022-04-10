package com.hongdatchy.bikeshare.entities.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyResponse {

    String message;
    Object data;

    public static MyResponse success(Object data){
        return new MyResponse("success", data);
    }

    public static MyResponse fail(Object data){
        return new MyResponse("fail", data);
    }

    public static MyResponse loginSuccess(String role,Object data){
        return new MyResponse(role, data);
    }

}

