package com.pftc.subboard.services;

import java.util.List;
import java.util.stream.Collectors;

import com.pftc.subboard.dto.subscription.SubscriptionDto;
import com.pftc.subboard.models.subscription.Subscription;
import com.pftc.subboard.models.user.User;
import com.pftc.subboard.repositories.SubscriptionRepository;
import com.pftc.subboard.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    
    @Autowired
    SubscriptionRepository subscriptionRepository;
    
    @Autowired
    UserRepository userRepository;

    /**
     * Get all the subscriptions of a user.
     * 
     * @param userId User's id
     * @return List of all the subscriptions of a user
     */
    public List<SubscriptionDto> getAllSubscriptionByUser(Long userId) {
        User user = userRepository.getOne(userId);
        List<Subscription> subscriptions = subscriptionRepository.findByUser(user);
        List<SubscriptionDto> subscriptionsDto = subscriptions.stream().map(Subscription::toDto).collect(Collectors.toList());

        return subscriptionsDto;
    }

    /**
     * Create a subscription to a user.
     * 
     * @param subscriptionDto Subscription to create
     * @param userId User's id
     * @return Subscription created
     */
    public SubscriptionDto createSubscription(SubscriptionDto subscriptionDto, Long userId) {
        // Subscription's id must be null or 0
        if(subscriptionDto.getId() != null && subscriptionDto.getId() > 0) {
            throw new IllegalArgumentException("Id can't be defined");
        }

        subscriptionDto.validate();

        Subscription subscription = subscriptionDto.toModel();
        subscription.setUser(userRepository.getOne(userId));
        subscriptionDto = subscriptionRepository.save(subscription).toDto();
        
        return subscriptionDto;
    }

    /**
     * Update a subscription.
     * 
     * @param subscriptionDto Updated subscription's data
     * @param userId User's id
     * @param subscriptionId Subscription's id
     * @return Updated subscription
     */
    public SubscriptionDto updateSubscription(SubscriptionDto subscriptionDto, Long userId, Long subscriptionId) {
        // Subscription's id can't be null or 0
        if(subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("Subscription id must be defined");
        } else {
            subscriptionDto.setId(subscriptionId);
        }

        subscriptionDto.validate();

        subscriptionDto.setInitDate(subscriptionRepository.getOne(subscriptionId).getInitDate());

        Subscription subscription = subscriptionDto.toModel();
        subscription.setUser(userRepository.getOne(userId));
        subscriptionDto = subscriptionRepository.save(subscription).toDto();
        
        return subscriptionDto;
    }

    /**
     * Delete a subscription.
     * 
     * @param userId User's id
     * @param subscriptionId Subscription's id
     */
    public void deleteSubscription(Long userId, Long subscriptionId) {
        // Subscription's id can't be null or 0
        if(subscriptionId == null || subscriptionId <= 0) {
            throw new IllegalArgumentException("Subscription id must be defined");
        }

        subscriptionRepository.deleteById(subscriptionId);
    }

}
