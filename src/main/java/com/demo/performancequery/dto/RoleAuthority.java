package com.demo.performancequery.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class RoleAuthority implements GrantedAuthority, Serializable {
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
