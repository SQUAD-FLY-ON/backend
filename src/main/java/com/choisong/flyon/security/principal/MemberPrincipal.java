package com.choisong.flyon.security.principal;

import com.choisong.flyon.member.domain.Roles;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class MemberPrincipal implements UserDetails {

    private static final String EMPTY_PASSWORD = "";
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String name;

    public MemberPrincipal(
        final String name, final Map<String, Object> attributes, final List<Roles> roles) {
        this.name = name;
        this.attributes = attributes;
        this.authorities =
            AuthorityUtils.createAuthorityList(
                roles.stream().map(r -> r.getMemberRole().name()).toList());
    }

    @Override
    public String getPassword() {
        return EMPTY_PASSWORD;
    }

    @Override
    public String getUsername() {
        return name;
    }
}
