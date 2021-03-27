package com.example.demo.global.security.model;

import com.example.demo.domain.user.User;
import com.example.demo.global.security.utils.CodecUtil;
import com.example.demo.global.security.utils.UserContextUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserContext {
    private String sessionKey;
    private Long userNo;
    private String contextId;
    private List<GrantedAuthority> authorities;
    private boolean signed;
    private User user;
    private Scopes scopes;

    @Builder
    public UserContext(String sessionKey, Long userNo, String contextId, List<GrantedAuthority> authorities, boolean signed, User user, Scopes scopes) {
        this.sessionKey = sessionKey;
        this.userNo = userNo;
        this.contextId = contextId;
        this.authorities = authorities;
        this.signed = signed;
        this.user = user;
        this.scopes = scopes;
    }

    public static UserContext createDefaultUserContext(String payload) {
        if (StringUtils.isEmpty(payload)) throw new IllegalArgumentException("payload is blank");
        return UserContext.builder()
                .sessionKey(UUID.randomUUID().toString())
                .contextId(CodecUtil.decodeBase64(payload))
                .signed(false)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(Scopes.ANONYMOUS.authority())))
                .scopes(Scopes.ANONYMOUS)
                .build();
    }

    public static UserContext createAccessUserContext(User user) {
        if (user == null) throw new IllegalArgumentException("user is blank");
        return UserContext.builder()
                .sessionKey(UserContextUtil.getSessionKey())
                .user(user)
                .userNo(user.getId())
                .contextId(UserContextUtil.getContextId())
                .signed(true)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(Scopes.USER.authority())))
                .scopes(Scopes.USER)
                .build();
    }

    public String getSessionKey() {
        return this.sessionKey;
    }

    public boolean isSigned() {
        return signed || user != null;
    }

    public boolean isUserScopes() {
        return scopes.equals(Scopes.USER);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void authenticateUser() {
        if(this.isUserScopes() && !this.isSigned()) {
            throw new RuntimeException("401 Not Authorized");
        }
    }
}
