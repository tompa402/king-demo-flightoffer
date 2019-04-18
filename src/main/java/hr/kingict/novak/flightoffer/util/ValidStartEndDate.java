package hr.kingict.novak.flightoffer.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidStartEndDateValidator.class)
@Documented
public @interface ValidStartEndDate {
    String message() default "{End date can't be before start}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return The first field
     */
    String startDate();

    /**
     * @return The second field
     */
    String endDate();

    /**
     * Defines several <code>@ValidStartEndDate</code> annotations on the same element
     *
     * @see ValidStartEndDate
     */
    @Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ValidStartEndDate[] value();
    }
}
