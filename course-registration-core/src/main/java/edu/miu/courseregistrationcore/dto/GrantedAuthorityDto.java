package edu.miu.courseregistrationcore.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Setter
@Getter
public class GrantedAuthorityDto implements GrantedAuthority {
    private String authority;

    public GrantedAuthorityDto(GrantedAuthority grantedAuthority) {
        this.authority = grantedAuthority.getAuthority();
    }
}
