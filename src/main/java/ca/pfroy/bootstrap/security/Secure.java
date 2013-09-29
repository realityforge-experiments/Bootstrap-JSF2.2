package ca.pfroy.bootstrap.security;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@InterceptorBinding
public @interface Secure {
    /**
     * @return The EL expression that should be evaluated. If it evaluates to
     * {@code true}, access will be granted. The EL expression may reference
     * any objects that are in any context, as well as the arguments of the method,
     * under the names {@code arg0, arg1, arg2, ...}.
     */
    @Nonbinding String value() default "";
}