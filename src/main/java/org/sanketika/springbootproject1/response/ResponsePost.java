package org.sanketika.springbootproject1.response;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ResponsePost {
    public static Map<String, Object> createResponses(String status, HttpStatus responseCode, String message, Object result) {
        Map<String,Object> response=new LinkedHashMap<>();
        response.put("id","api.create");
        response.put("ver","1.0");
        response.put("ts", Instant.now().toString());


        Map<String,Object> param=new LinkedHashMap<>();
        param.put("resmsgid", UUID.randomUUID().toString());
        param.put("status",status);
        param.put("message",message);


        response.put("params",param);
        response.put("responseCode",responseCode.value());
        response.put("result",result);

        return response;
    }
}

