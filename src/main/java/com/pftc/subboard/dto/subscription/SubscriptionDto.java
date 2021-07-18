package com.pftc.subboard.dto.subscription;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pftc.subboard.dto.abstractdto.Dto;
import com.pftc.subboard.models.subscription.Subscription;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubscriptionDto extends Dto<Subscription> {
    
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("color")
    private String color;

    @Override
    public Subscription toModel() {
        Subscription subscription = super.toModel();

        subscription.setId(id);
        subscription.setName(name);
        subscription.setColor(color);
        
        return subscription;
    }

    @Override
    public void validate() throws IllegalArgumentException {
        // Subscription's name can't be null or empty
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Name must be defined");
        }
    }

}
