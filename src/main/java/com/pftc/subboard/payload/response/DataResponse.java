package com.pftc.subboard.payload.response;

import com.pftc.subboard.dto.abstractdto.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataResponse<T extends Dto<?>> extends Response {

    private T data;

    public DataResponse(T data) {
        super();
        
        this.data = data;
    }
}
