package edu.sb.cookbook.persistence;

import java.util.Comparator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import edu.sb.tool.Copyright;
import edu.sb.tool.JsonProtectedPropertyStrategy;
import edu.sb.tool.Representable;


/**
 * JPA based entity type representing victuals.
 */
@Entity
@Table(schema="cookbook", name="Victual", indexes=@Index(columnList="alias", unique=true))
@PrimaryKeyJoinColumn(name="victualIdentity")
@DiscriminatorValue("Victual")
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2022, holders="Sascha Baumeister")
public class Victual extends AbstractEntity implements Representable {
	static public enum Diet { CARNIVORIAN, PESCATARIAN, LACTO_OVO_VEGETARIAN, LACTO_VEGETARIAN, VEGAN }
	static public final Comparator<Victual> ALIAS_COMPARATOR = Comparator.comparing(Victual::getAlias);

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, updatable=true)
	private Diet diet;

	@NotNull @Size(min=1, max=128)
	@Column(nullable=false, updatable=true, length=128, unique=true)
	private String alias;

	@Size(min=1, max=4094)
	@Column(nullable=true, updatable=true, length=4094)
	private String description; 

	// *:1 relationship
	// do not specify @NotNull because of web-service context
	@ManyToOne(optional=false)
	@JoinColumn(nullable=false, updatable=true, name="avatarReference")
	private Document avatar;

	// *:0..1 relationship
	@ManyToOne(optional=true)
	@JoinColumn(nullable=true, updatable=true, name="authorReference")
	private Person author;


	/**
	 * Initializes a new instance.
	 */
	public Victual () {
		this.diet = Diet.VEGAN;
		this.alias = null;
		this.description = null;
		this.avatar = null;
		this.author = null;
	}


	/**
	 * Returns the diet.
	 * @return the diet
	 */
	@JsonbProperty
	public Diet getDiet () {
		return this.diet;
	}


	/**
	 * Sets the diet.
	 * @param diet the diet
	 */
	public void setDiet (final Diet diet) {
		this.diet = diet;
	}


	/**
	 * Returns the alias.
	 * @return the alias
	 */
	@JsonbProperty
	public String getAlias () {
		return this.alias;
	}


	/**
	 * Sets the alias.
	 * @param alias the alias
	 */
	public void setAlias (final String alias) {
		this.alias = alias;
	}


	/**
	 * Returns the description.
	 * @return the description, or null for none
	 */
	@JsonbProperty
	public String getDescription () {
		return this.description;
	}


	/**
	 * Sets the description.
	 * @param description the description, or null for none
	 */
	public void setDescription (final String description) {
		this.description = description;
	}


	/**
	 * Returns the avatar.
	 * @return the *:1 related avatar
	 */
	@JsonbProperty
	public Document getAvatar () {
		return this.avatar;
	}


	/**
	 * Sets the avatar.
	 * @param avatar the *:1 related avatar
	 */
	public void setAvatar (final Document avatar) {
		this.avatar = avatar;
	}


	/**
	 * Returns the author reference.
	 * @return the *:0..1 related author's identity, or null for none
	 */
	@JsonbProperty
	protected Long getAuthorReference () {
		return this.author == null ? null : this.author.getIdentity();
	}


	/**
	 * Returns the author.
	 * @return the *:0..1 related author
	 */
	@JsonbTransient
	public Person getAuthor () {
		return this.author;
	}


	/**
	 * Sets the author.
	 * @param author the *:0..1 related author
	 */
	public void setAuthor (final Person author) {
		this.author = author;
	}
}
