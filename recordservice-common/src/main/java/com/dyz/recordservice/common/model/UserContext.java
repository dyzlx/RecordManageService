package com.dyz.recordservice.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"userRoles", "authToken"})
public class UserContext {

    public final static String USER_ID = "ms-user-id";

    public final static String USER_ROLES = "ms-user-roles";

    public final static String CORRELATION_ID = "ms-correlation-id";

    public final static String AUTH_TOKEN = "ms-auth-token";

    private Integer userId;

    private List<String> userRoles;

    private String correlationId;

    private String authToken;
}