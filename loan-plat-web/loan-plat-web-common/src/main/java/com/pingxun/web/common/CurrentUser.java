package com.pingxun.web.common;

import java.lang.annotation.*;

/**
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
