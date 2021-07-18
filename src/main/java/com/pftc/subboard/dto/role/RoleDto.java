package com.pftc.subboard.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pftc.subboard.dto.abstractdto.Dto;
import com.pftc.subboard.models.role.ERole;
import com.pftc.subboard.models.role.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends Dto<Role> {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private ERole name;

    @Override
    public Role toModel() {
        Role role = super.toModel();

        role.setId(id);
        role.setName(name);
        
        return role;
    }

    @Override
    public void validate() throws Exception {
        // No checks yet
    }
    
}
