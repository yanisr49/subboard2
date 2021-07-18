package com.pftc.subboard.payload.response;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import lombok.Data;

@Data
public class Response {
    private String timestamp;

    public Response() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        timestamp = sdf.format(new Timestamp(System.currentTimeMillis()));
    }
}