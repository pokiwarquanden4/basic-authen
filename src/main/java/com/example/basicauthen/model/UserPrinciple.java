package com.example.basicauthen.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String grant_type;

    private String password;

    private Collection<? extends GrantedAuthority> roles;

    public UserPrinciple(Long id,
                         String grant_type, String password,
                         Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.grant_type = grant_type;
        this.password = password;
        this.roles = roles;
    }

    public static UserPrinciple build(User user) {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(user.getRole());
        List<GrantedAuthority> authorities = roleSet.stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(),
                user.getGrand_type(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return grant_type;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
