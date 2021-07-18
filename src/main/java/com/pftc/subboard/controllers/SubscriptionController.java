package com.pftc.subboard.controllers;

import java.net.URI;
import java.util.List;

import com.pftc.subboard.dto.subscription.SubscriptionDto;
import com.pftc.subboard.payload.response.DataResponse;
import com.pftc.subboard.payload.response.DatasResponse;
import com.pftc.subboard.payload.response.Response;
import com.pftc.subboard.services.SubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users/{user_id}")
public class SubscriptionController {

	@Autowired
	SubscriptionService subscriptionService;

	/**
	 * Get all the subscriptions of a user.
	 * 
	 * @param userId : user's id
	 * @return List of all the subscriptions of a user
	 */
	@GetMapping("/subscriptions")
	@PreAuthorize("#userId == principal.id")
	public ResponseEntity<DatasResponse<SubscriptionDto>> getAll(@PathVariable("user_id") Long userId) {
		List<SubscriptionDto> subscriptionsDto = subscriptionService.getAllSubscriptionByUser(userId);
		DatasResponse<SubscriptionDto> response = new DatasResponse<>(subscriptionsDto);
		return ResponseEntity.ok(response);
	}

	/**
	 * Create a subscription to a user.
	 * 
	 * @param subscriptionDto Subscription to create
	 * @param userId User's id
	 * @return Subscription created
	 */
	@PostMapping("/subscriptions")
    @PreAuthorize("#userId == principal.id")
	public ResponseEntity<DataResponse<SubscriptionDto>> create(@RequestBody SubscriptionDto subscriptionDto, @PathVariable("user_id") Long userId) {

		subscriptionDto = subscriptionService.createSubscription(subscriptionDto, userId);
		DataResponse<SubscriptionDto> response = new DataResponse<>(subscriptionDto);

		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(subscriptionDto.getId()).toUri();

		return ResponseEntity.created(location).body(response);
	}
	
	/**
	 * Update a subscription.
	 * 
	 * @param subscriptionDto Updated subscription's data
	 * @param userId User's id
	 * @param subscriptionId Subscription's id
	 * @return Updated subscription
	 */
	@PutMapping("/subscriptions/{subscription_id}")
    @PreAuthorize("#userId == principal.id")
	public ResponseEntity<DataResponse<SubscriptionDto>> update(@RequestBody SubscriptionDto subscriptionDto, @PathVariable("user_id") Long userId, @PathVariable("subscription_id") Long subscriptionId) {

		subscriptionDto = subscriptionService.updateSubscription(subscriptionDto, userId, subscriptionId);
		DataResponse<SubscriptionDto> response = new DataResponse<>(subscriptionDto);

		return ResponseEntity.ok(response);
	}
	
	/**
	 * Delete a subscription.
	 * 
	 * @param userId User's id
	 * @param subscriptionId Subscription's id
	 * @return 204 response
	 */
	@DeleteMapping("/subscriptions/{subscription_id}")
    @PreAuthorize("#userId == principal.id")
	public ResponseEntity<Response> delete(@PathVariable("user_id") Long userId, @PathVariable("subscription_id") Long subscriptionId) {

		subscriptionService.deleteSubscription(userId, subscriptionId);

		return ResponseEntity.noContent().build();
	}

}
