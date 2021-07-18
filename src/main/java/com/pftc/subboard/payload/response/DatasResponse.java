package com.pftc.subboard.payload.response;

import java.util.List;

import com.pftc.subboard.dto.abstractdto.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DatasResponse<T extends Dto<?>> extends Response {

    private List<T> datas;

    public DatasResponse(List<T> datas) {
        super();
        
        this.datas = datas;
    }
}
