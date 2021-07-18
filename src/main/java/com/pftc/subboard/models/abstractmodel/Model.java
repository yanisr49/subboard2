package com.pftc.subboard.models.abstractmodel;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.pftc.subboard.dto.abstractdto.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Model<T extends Dto<?>> {

    @NonNull
    protected LocalDateTime initDate;

    protected LocalDateTime updateDate;

    @PrePersist
    private void onCreate() {
        if(initDate == null) {
            initDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    private void onUpdate() {
        updateDate = LocalDateTime.now();
    }

    /**
     * Convert model to dto.
     * 
     * @return T : dto
     */
    public T toDto() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        
        @SuppressWarnings("unchecked")
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];

        try {
            T dto = (T) type.getDeclaredConstructor().newInstance();

            dto.setInitDate(initDate);
            dto.setUpdateDate(updateDate);

            return dto;
        } catch (Exception e) {
            // Type T doesn't have any default constructor :/
            throw new RuntimeException(e);
        }
    }
}
