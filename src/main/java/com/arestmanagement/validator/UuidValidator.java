package com.arestmanagement.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER, ANNOTATION_TYPE})
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Pattern(regexp = "^[\\da-f]{8}-[\\da-f]{4}-[1-5][\\da-f]{3}-[89ab][\\da-f]{3}-[\\da-f]{12}$")
public @interface UuidValidator {
    String message() default "{invalid.uuid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
