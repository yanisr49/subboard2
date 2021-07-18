package com.pftc.subboard.repositories;

import java.util.List;

import com.pftc.subboard.models.subscription.Subscription;
import com.pftc.subboard.models.user.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    List<Subscription> findByUser(User user);
}
