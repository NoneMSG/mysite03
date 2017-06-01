package com.jx372.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) //어디에 사용될 어노테이션인지
@Retention(RetentionPolicy.RUNTIME) //사용될 시간(기간)
public @interface Auth {

}
