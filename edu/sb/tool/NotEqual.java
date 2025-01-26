package edu.sb.tool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;


/**
 * The annotated element must have a text representation that differs from the annotation's value, which is always the case with
 * {@code null}. Accepts any type.
 * @see Object#toString()
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(NotEqual.List.class)
@Constraint(validatedBy = NotEqual.Validator.class)
@Copyright(year = 2015, holders = "Sascha Baumeister")
public @interface NotEqual {

	/**
	 * Allows several {@link NotEqual} annotations on the same element.
	 */
	@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	static @interface List {
		NotEqual[] value();
	}


	/**
	 * @return the value
	 */
	String value();


	String message() default "{de.sb.toolbox.val.NotEqual.message}";


	Class<?>[] groups() default {};


	Class<? extends Payload>[] payload() default {};



	/**
	 * Validator for the {@link NotEqual} annotation.
	 */
	static class Validator implements ConstraintValidator<NotEqual,Object> {
		private String value;


		/**
		 * {@inheritDoc}
		 * @throws NullPointerException if the given argument is {@code null}
		 */
		public void initialize (final NotEqual annotation) throws NullPointerException {
			this.value = annotation.value();
		}


		/**
		 * {@inheritDoc}
		 */
		public boolean isValid (final Object object, final ConstraintValidatorContext context) {
			return !Objects.equals(this.value, Objects.toString(object, null));
		}
	}
}