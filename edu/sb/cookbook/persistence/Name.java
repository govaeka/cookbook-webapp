package edu.sb.cookbook.persistence;

import java.util.Comparator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import edu.sb.tool.Copyright;
import edu.sb.tool.JsonProtectedPropertyStrategy;


/**
 * JPA based embeddable type representing names.
 */
@Embeddable
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2005, holders="Sascha Baumeister")
public class Name extends Object implements Comparable<Name> {
	static public final Comparator<Name> COMPARATOR = Comparator
		.comparing(Name::getTitle, Comparator.nullsLast(Comparator.naturalOrder()))
		.thenComparing(Name::getFamily)
		.thenComparing(Name::getGiven);

	@Size(min=1, max=15)
	@Column(nullable=true, updatable=true, length=15)
	private String title;

	@NotNull @Size(min=1, max=31)
	@Column(nullable=false, updatable=true, length=31)
	private String family;

	@NotNull @Size(min=1, max=31)
	@Column(nullable=false, updatable=true, length=31)
	private String given;


	/**
	 * Returns the title.
	 * @return the title, or null for none
	 */
	@JsonbProperty
	public String getTitle () {
		return this.title;
	}


	/**
	 * Sets the title.
	 * @param title the title, or null for none
	 */
	public void setTitle (final String title) {
		this.title = title;
	}

	
	/**
	 * Returns the family name.
	 * @return the family name
	 */
	@JsonbProperty
	public String getFamily () {
		return this.family;
	}

	
	/**
	 * Sets the family name.
	 * @param family the family name
	 */
	public void setFamily (final String family) {
		this.family = family;
	}

	
	/**
	 * Returns the given name.
	 * @return the given name
	 */
	@JsonbProperty
	public String getGiven () {
		return this.given;
	}

	
	/**
	 * Sets the given name.
	 * @param given the given name
	 */
	public void setGiven (final String given) {
		this.given = given;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo (final Name other) {
		return COMPARATOR.compare(this, other);
	} 
}
