package hr.kingict.novak.flightoffer.util;

import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidStartEndDateValidator implements ConstraintValidator<ValidStartEndDate, Object> {
    private String startDate;
    private String endDate;

    @Override
    public void initialize(final ValidStartEndDate constraintAnnotation) {
        startDate = constraintAnnotation.startDate();
        endDate = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Object firstObj = BeanUtils.getProperty(value, startDate);
            final Object secondObj = BeanUtils.getProperty(value, endDate);

            return firstObj == null && secondObj == null || firstObj != null && LocalDate.parse((String)firstObj).isBefore(LocalDate.parse((String)secondObj));
        } catch (final Exception ignore) {
            System.out.println(ignore.getMessage());
            // ignore
        }
        return true;
    }
}
