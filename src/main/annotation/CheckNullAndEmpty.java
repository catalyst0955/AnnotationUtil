package main.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) @Target({ElementType.FIELD, ElementType.TYPE})
public @interface CheckNullAndEmpty {
    String checkNull() default "Y";
    String checkEmpty() default "Y";

}
