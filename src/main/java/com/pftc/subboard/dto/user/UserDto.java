package com.pftc.subboard.dto.user;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pftc.subboard.dto.abstractdto.Dto;
import com.pftc.subboard.dto.role.RoleDto;
import com.pftc.subboard.dto.subscription.SubscriptionDto;
import com.pftc.subboard.models.role.Role;
import com.pftc.subboard.models.subscription.Subscription;
import com.pftc.subboard.models.user.User;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends Dto<User> {
    
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("roles")
	private Set<RoleDto> rolesDto;

    @JsonProperty("subscriptions")
	private Set<SubscriptionDto> subscriptionsDto;

    @Override
    public User toModel() {
        User user = super.toModel();

        Set<Role> roles = rolesDto.stream().map(RoleDto::toModel).collect(Collectors.toSet());
        Set<Subscription> subscriptions = subscriptionsDto.stream().map(SubscriptionDto::toModel).collect(Collectors.toSet());

        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        user.setSubscriptions(subscriptions);
        
        return user;
    }

    @Override
    public void validate() throws IllegalArgumentException {
        // User's username can't be null or empty
        if(!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username must be defined");
        }
        // User's password can't be null or empty
        if(!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password must be defined");
        }
    }
}
