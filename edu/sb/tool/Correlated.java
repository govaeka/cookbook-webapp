package edu.sb.tool;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;


/**
 * The annotated class (or it's superclasses, or relationally navigable classes) must contain two properties, and the numeric
 * value of said properties must satisfy a correlation defined by a given comparison operator.<br/>
 * <br/>
 * The required {@link #leftAccessPath} and {@link #rightAccessPath} attributes are used to resolve both operand values, using
 * an instance of the annotated target class as a starting point. A path's first element must be a direct or inherited property
 * name of the target instance; any further path elements address a property of a property, and so on. The comparison is
 * performed after converting both operands to either long, or double if any of the operands is not guaranteed to fit into
 * long's value range. The optional {@link #operator} attribute (which defaults to {@code NOT_EQUAL}) is used to determine how
 * to compare both values.<br />
 * <br />
 * Supported property types are any subclasses of Number, Character (interpreted as character code), Enum (interpreted as enum
 * ordinal), and Boolean (similarly interpreted as 0 for false and 1 for true). Note that by definition a correlation is
 * considered valid if any of the operands is {@code null}.<br/>
 * <br/>
 * For example, this annotation on a contract class validates if a worker's wage is legal within a contract's country:<br />
 * <tt>@Correlated("wage", {"country", "minimumWage"}, Correlated.Operator.GREATER_EQUAL)}</tt>
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Correlated.List.class)
@Constraint(validatedBy = Correlated.Validator.class)
@Copyright(year = 2015, holders = "Sascha Baumeister")
public @interface Correlated {
	static enum Operator {
		NOT_EQUAL, LESS, LESS_EQUAL, EQUAL, GREATER_EQUAL, GREATER
	}



	/**
	 * Allows several {@link Correlated} annotations on the same element.
	 */
	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	static @interface List {
		Correlated[] value();
	}


	/**
	 * @return the optional comparison operator, with NOT_EQUAL as default
	 */
	Operator operator() default Operator.NOT_EQUAL;


	/**
	 * @return the required access path for the left operand, as a sequence of property names
	 */
	String[] leftAccessPath();


	/**
	 * @return the required access path for the right operand, as a sequence of property names
	 */
	String[] rightAccessPath();


	String message() default "{de.sb.toolbox.val.Correlated.message}";


	Class<?>[] groups() default {};


	Class<? extends Payload>[] payload() default {};



	/**
	 * Validator for the {@link Correlated} annotation.
	 */
	static class Validator implements ConstraintValidator<Correlated,Object> {
		private Correlated.Operator operator;
		private String[] leftAccessPath;
		private String[] rightAccessPath;


		/**
		 * {@inheritDoc}
		 * @throws NullPointerException if the given argument is {@code null}
		 */
		public void initialize (final Correlated annotation) throws NullPointerException {
			this.leftAccessPath = annotation.leftAccessPath();
			this.rightAccessPath = annotation.rightAccessPath();
			this.operator = annotation.operator();
		}


		/**
		 * {@inheritDoc}
		 * @throws NullPointerException if the given object is {@code null}
		 */
		public boolean isValid (final Object object, final ConstraintValidatorContext context) throws NullPointerException {
			if (object == null) throw new NullPointerException();
			final Number leftOperand = toNumber(reflectPropertyValue(object, this.leftAccessPath));
			final Number rightOperand = toNumber(reflectPropertyValue(object, this.rightAccessPath));

			if (leftOperand == null | rightOperand == null) return true;
			final int compare = leftOperand instanceof Long & rightOperand instanceof Long ? Long.compare(leftOperand.longValue(), rightOperand.longValue()) : Double.compare(leftOperand.doubleValue(), rightOperand.doubleValue());

			switch (this.operator) {
				case NOT_EQUAL:
					return compare != 0;
				case LESS:
					return compare < 0;
				case LESS_EQUAL:
					return compare <= 0;
				case EQUAL:
					return compare == 0;
				case GREATER_EQUAL:
					return compare >= 0;
				case GREATER:
					return compare > 0;
				default:
					throw new AssertionError();
			}
		}


		/**
		 * Recursively reflects the objects's inheritance chain for the given sequence of property names. Note that each
		 * subsequent property name is reflected against the result of the previous property reflection.
		 * @param object the root object
		 * @param propertyNames the property name sequence
		 * @return the property value, or {@code null} for none
		 * @throws NullPointerException if the given property names array, or any of it's elements, is {@code null}
		 */
		static private Object reflectPropertyValue (Object object, final String[] propertyNames) throws NullPointerException {
			for (final String propertyName : propertyNames)
				object = reflectPropertyValue(object, propertyName);

			return object;
		}


		/**
		 * Recursively reflects the objects's inheritance chain for the given property.
		 * @param object the object
		 * @param propertyName the property name
		 * @return the property value, or {@code null} for none
		 * @throws NullPointerException if the given property name is {@code null}
		 */
		static private Object reflectPropertyValue (final Object object, final String propertyName) throws NullPointerException {
			if (object == null) return null;

			try {
				try {
					final Field field = reflectField(object.getClass(), propertyName);
					field.setAccessible(true);
					return field.get(object);
				} catch (final NoSuchFieldException exception) {
					// do nothing
				}

				Method method;
				final String suffix = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
				try {
					method = reflectMethod(object.getClass(), "get" + suffix);
				} catch (final NoSuchMethodException exception) {
					try {
						method = reflectMethod(object.getClass(), "is" + suffix);
					} catch (final NoSuchMethodException nestedException) {
						return null;
					}
				}

				method.setAccessible(true);
				return method.invoke(object);
			} catch (InvocationTargetException exception) {
				return null;
			} catch (IllegalAccessException exception) {
				throw new AssertionError();
			}
		}


		/**
		 * Recursively reflects the type's inheritance chain for the given field.
		 * @param type the type
		 * @param fieldName the field name
		 * @return the field
		 * @throws NullPointerException if any of the given arguments is {@code null}
		 * @throws NoSuchFieldException if no field with the specified name is found
		 */
		static private Field reflectField (final Class<?> type, final String fieldName) throws NullPointerException, NoSuchFieldException {
			try {
				return type.getDeclaredField(fieldName);
			} catch (final NoSuchFieldException exception) {
				final Class<?> superType = type.getSuperclass();
				if (superType == null) throw new NoSuchFieldException();
				return reflectField(superType, fieldName);
			}
		}


		/**
		 * Recursively reflects the type's inheritance chain for the given method.
		 * @param type the type
		 * @param methodName the method name
		 * @return the method
		 * @throws NullPointerException if any of the given arguments is {@code null}
		 * @throws NoSuchMethodException if no method with the specified name is found
		 */
		static private Method reflectMethod (final Class<?> type, final String methodName) throws NullPointerException, NoSuchMethodException {
			try {
				return type.getDeclaredMethod(methodName);
			} catch (final NoSuchMethodException exception) {
				final Class<?> superType = type.getSuperclass();
				if (superType == null) throw new NoSuchMethodException();
				return reflectMethod(superType, methodName);
			}
		}


		/**
		 * Converts the given object into either a Double or a Long. If the object is {@code null} or already a Double or a
		 * Long, then it is returned as is. If it is a Byte, a Short or an Integer, then it's value is returned as a Long. If it
		 * is any other type of Number, it's value is returned as a Double. If the object is a Character, it's code is returned
		 * as a Long. If it is an Enum, it's ordinal is returned as a Long. If it is is a Boolean, true is returned a 1L, and
		 * false is returned as 0L.
		 * @param object the (optional) object, or {@code null} for none
		 * @throws IllegalArgumentException if the given object's type is neither {@code null}, nor a Number, Character, Boolean
		 *         or Enum
		 */
		static private Number toNumber (final Object object) throws IllegalArgumentException {
			if (object == null) return null;
			if (object instanceof Number) {
				final Number number = (Number) object;
				if (number instanceof Long | object instanceof Double) return number;
				if (number instanceof Byte | object instanceof Short | object instanceof Integer) return number.longValue();
				return number.doubleValue();
			}

			if (object instanceof Character) return (long) ((Character) object).charValue();
			if (object instanceof Boolean) return ((Boolean) object).booleanValue() ? 1L : 0L;
			if (object instanceof Enum) return (long) ((Enum<?>) object).ordinal();
			throw new IllegalArgumentException();
		}
	}
}