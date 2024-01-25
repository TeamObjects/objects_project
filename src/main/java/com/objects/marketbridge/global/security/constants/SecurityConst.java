package com.objects.marketbridge.global.security.constants;

/**
 * 토큰 정보 값을 설정하는 클래스
 */
public class SecurityConst {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 2 * 60 * 1000;
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 3 * 60 * 1000;

    //6시간
//    public static final long ACCESS_TOKEN_EXPIRE_TIME = 6 * 60 * 60 * 1000;

    //7일
//    public static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    public static final String AUTH = "auth";

    public static final String SECRET_KEY = "${spring.jwt.secret}";

    public static final String BEARER = "bearer";

    public static final String AUTHORIZATION = "Authorization";

    public static final String ID = "id";

    public static final String AUTH_USER = "hasAuthority('USER')";

    public static final String BLACKLIST = "blacklist";

    public static final String BLACKLIST_TRUE = "true";

    public static final String RE_ISSUE_URI = "/member/re-issue";
}
