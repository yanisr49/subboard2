package com.pftc.subboard.models.user;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pftc.subboard.dto.role.RoleDto;
import com.pftc.subboard.dto.subscription.SubscriptionDto;
import com.pftc.subboard.dto.user.UserDto;
import com.pftc.subboard.models.abstractmodel.Model;
import com.pftc.subboard.models.role.Role;
import com.pftc.subboard.models.subscription.Subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends Model<UserDto> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String username;
	
	@NonNull
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role role) {
		roles.add(role);
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Subscription> subscriptions = new HashSet<>();

	public void addSubscription(Subscription subscription) {
		subscriptions.add(subscription);
	}

	@Override
    public UserDto toDto() {
		UserDto userDto = super.toDto();

		userDto.setId(id);
		userDto.setUsername(username);

        Set<RoleDto> rolesDto = roles.stream().map(role -> role.toDto()).collect(Collectors.toSet());
        Set<SubscriptionDto> subscriptionsDto = subscriptions.stream().map(subscriptions -> subscriptions.toDto()).collect(Collectors.toSet());

		userDto.setRolesDto(rolesDto);
		userDto.setSubscriptionsDto(subscriptionsDto);
        
        return userDto;
    }

}
