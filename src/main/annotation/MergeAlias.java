package main.annotation;

import main.annotation.interfacepkg.Convertable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ElementType.FIELD})
public @interface MergeAlias {
    String[] value() default {};

    String stringToDate() default "";

    String dateToString() default "";

    Class<? extends Convertable>[] convert() default {};
}
