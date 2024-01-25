package com.objects.marketbridge.global.security.mock;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.objects.marketbridge.global.security.constants.SecurityConst.AUTH_USER;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(AUTH_USER)
public @interface UserAuthorize {
}
