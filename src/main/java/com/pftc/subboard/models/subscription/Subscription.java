package com.pftc.subboard.models.subscription;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.pftc.subboard.dto.subscription.SubscriptionDto;
import com.pftc.subboard.models.abstractmodel.Model;
import com.pftc.subboard.models.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Subscription extends Model<SubscriptionDto> {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Override
    public SubscriptionDto toDto() {
        SubscriptionDto subscriptionDto = super.toDto();

        subscriptionDto.setId(id);
        subscriptionDto.setName(name);
        subscriptionDto.setColor(color);
        
        return subscriptionDto;
    }

}
