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
 * JPA based embeddable type representing addresses.
 */
@Embeddable
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2005, holders="Sascha Baumeister")
public class Address extends Object implements Comparable<Address> {
	static public final Comparator<Address> COMPARATOR = Comparator
		.comparing(Address::getCountry)
		.thenComparing(Address::getPostcode)
		.thenComparing(Address::getCity)
		.thenComparing(Address::getStreet);


	@NotNull @Size(min=1, max=15)
	@Column(nullable=false, updatable=true, length=15)
	private String postcode;

	@NotNull @Size(min=1, max=63)
	@Column(nullable=false, updatable=true, length=63)
	private String street;

	@NotNull @Size(min=1, max=63)
	@Column(nullable=false, updatable=true, length=63)
	private String city;

	@NotNull @Size(min=1, max=63)
	@Column(nullable=false, updatable=true, length=63)
	private String country;


	/**
	 * Returns the postcode.
	 * @return the ZIP code
	 */
	@JsonbProperty
	public String getPostcode () {
		return this.postcode;
	}


	/**
	 * Sets the postcode.
	 * @param postcode the ZIP code
	 */
	public void setPostcode (final String postcode) {
		this.postcode = postcode;
	}


	/**
	 * Returns the street.
	 * @return a street
	 */
	@JsonbProperty
	public String getStreet () {
		return this.street;
	}


	/**
	 * Sets the street.
	 * @param street the street
	 */
	public void setStreet (final String street) {
		this.street = street;
	}


	/**
	 * Returns the city.
	 * @return the city
	 */
	@JsonbProperty
	public String getCity () {
		return this.city;
	}


	/**
	 * Sets the city.
	 * @param city the city
	 */
	public void setCity (final String city) {
		this.city = city;
	}


	/**
	 * Returns the country.
	 * @return the country
	 */
	@JsonbProperty
	public String getCountry () {
		return this.country;
	}

	
	/**
	 * Sets the country.
	 * @param country the country
	 */
	public void setCountry (final String country) {
		this.country = country;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo (final Address other) {
		return COMPARATOR.compare(this, other);
	}
}
