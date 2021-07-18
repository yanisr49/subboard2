package com.pftc.subboard.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.pftc.subboard.dto.subscription.SubscriptionDto;
import com.pftc.subboard.models.subscription.Subscription;
import com.pftc.subboard.models.user.User;
import com.pftc.subboard.repositories.SubscriptionRepository;
import com.pftc.subboard.repositories.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(value = MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    SubscriptionRepository subscriptionRepository;
    
    @Mock
    UserRepository userRepository;

    @InjectMocks
    SubscriptionService subscriptionService;

    private Subscription subscription1;
    private Subscription subscription2;
    private List<Subscription> subscriptions;

    private SubscriptionDto subscriptionDto1;
    private SubscriptionDto subscriptionDto2;
    private List<SubscriptionDto> subscriptionsDto;

    private User user;

    @BeforeEach
    void init() {
        user = new User(1L, "admin", "admin",  null, null);

        subscription1 = new Subscription(1L, "Netflix", "red", user);
        subscription2 = new Subscription(2L, "Spotify", "green", user);
        subscriptions = new ArrayList<>();
        subscriptions.add(subscription1);
        subscriptions.add(subscription2);

        subscriptionDto1 = new SubscriptionDto(1L, "Netflix", "red");
        subscriptionDto2 = new SubscriptionDto(2L, "Spotify", "green");
        subscriptionsDto = new ArrayList<>();
        subscriptionsDto.add(subscriptionDto1);
        subscriptionsDto.add(subscriptionDto2);
    }

    @Test
    void givenValidUserId_whenGetAll_ThenReturnSubscriptions() {
        // When
        when(userRepository.getOne(1L)).thenReturn(user);
        when(subscriptionRepository.findByUser(user)).thenReturn(subscriptions);
        List<SubscriptionDto> returnSubscriptions = subscriptionService.getAllSubscriptionByUser(1L);

        // Then
        Assertions.assertEquals(returnSubscriptions.size(), 2);
        Assertions.assertEquals(returnSubscriptions.get(0).getId(), 1L);
        Assertions.assertEquals(returnSubscriptions.get(0).getName(), "Netflix");
        Assertions.assertEquals(returnSubscriptions.get(0).getColor(), "red");
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 1000})
    void givenWrongSubscriptionId_whenCreateSubscription_ThenThrowsException(Long id) {
        // Given
        subscriptionDto1.setId(id);

        // When / Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> subscriptionService.createSubscription(subscriptionDto1, 1L));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongName_whenCreateSubscription_ThenThrowsException(String name) {
        // Given
        subscriptionDto1.setId(null);
        subscriptionDto1.setName(name);

        // When / Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> subscriptionService.createSubscription(subscriptionDto1, 1L));
    }

    @Test
    void givenValidData_whenCreateSubscription_ThenReturnCreatedSubscription() {
        // Given
        subscriptionDto1.setId(null);

        // When
        when(userRepository.getOne(1L)).thenReturn(user);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription1);
        SubscriptionDto returnSubscription = subscriptionService.createSubscription(subscriptionDto1, 1L);

        // Then
        Assertions.assertEquals(returnSubscription.getId(), 1L);
        Assertions.assertEquals(returnSubscription.getName(), "Netflix");
        Assertions.assertEquals(returnSubscription.getColor(), "red");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0})
    void givenWrongSubscriptionId_whenUpdateSubscription_ThenThrowsException(Long subscriptionId) {
        // When / Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> subscriptionService.updateSubscription(subscriptionDto1, 1L, subscriptionId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenWrongName_whenUpdateSubscription_ThenThrowsException(String name) {
        // Given
        subscriptionDto1.setName(name);

        // When / Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> subscriptionService.updateSubscription(subscriptionDto1, 1L, 1L));
    }

    @Test
    void givenValidData_whenUpdateSubscription_ThenReturnUpdatedSubscription() {
        // When
        when(subscriptionRepository.getOne(1L)).thenReturn(subscription1);
        when(userRepository.getOne(1L)).thenReturn(user);
        when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription1);
        SubscriptionDto returnSubscription = subscriptionService.updateSubscription(subscriptionDto1, 1L, 1L);

        // Then
        Assertions.assertEquals(returnSubscription.getId(), 1L);
        Assertions.assertEquals(returnSubscription.getName(), "Netflix");
        Assertions.assertEquals(returnSubscription.getColor(), "red");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {0})
    void givenWrongSubscriptionId_whenDeleteSubscription_ThenThrowsException(Long subscriptionId) {
        // When / Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> subscriptionService.deleteSubscription(1L, subscriptionId));
    }

    @Test
    void givenValidData_whenDeleteSubscription_ThenWorks() {
        // When
        subscriptionService.deleteSubscription(1L, 1L);

        // Then
        verify(subscriptionRepository).deleteById(1L);
    }
}
