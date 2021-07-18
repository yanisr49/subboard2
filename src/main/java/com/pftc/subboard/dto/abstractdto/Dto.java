package com.pftc.subboard.dto.abstractdto;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;

import com.pftc.subboard.models.abstractmodel.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Dto<T extends Model<?>> {

    protected LocalDateTime initDate;

    protected LocalDateTime updateDate;

    public abstract void validate() throws Exception;

    /**
     * Convert dto to model.
     * 
     * @return T : model
     */
    public T toModel() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];

        try {
            T model = (T) type.getDeclaredConstructor().newInstance();

            if(initDate != null) {
                model.setInitDate(initDate);
            }
            model.setUpdateDate(updateDate);

            return model;
        } catch (Exception e) {
            // Type T doesn't have any default constructor :/
            throw new RuntimeException(e);
        }
    }
}
