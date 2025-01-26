package edu.sb.cookbook.persistence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.persistence.annotations.CacheIndex;
import edu.sb.tool.Copyright;
import edu.sb.tool.HashCodes;
import edu.sb.tool.JsonProtectedPropertyStrategy;
import edu.sb.tool.Representable; 


/**
 * JPA based entity type representing people.
 */
@Entity
@Table(schema="cookbook", name="Person", indexes=@Index(columnList="email", unique=true))
@PrimaryKeyJoinColumn(name="personIdentity")
@DiscriminatorValue("Person")
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2005, holders="Sascha Baumeister")
public class Person extends AbstractEntity implements Representable {
	static public enum Group { USER, ADMIN }
	static protected final String DEFAULT_PASSWORD_HASH = HashCodes.sha2HashText(256, "changeit");
	static public final Comparator<Person> NAME_COMPARATOR = Comparator.comparing(Person::getName).thenComparing(Person::getEmail);

	@NotNull @Size(min=3, max=128) @Email
	@Column(nullable=false, updatable=true, length=128, unique=true)
	@CacheIndex(updateable=true)
	private String email;

	@NotNull @Size(min=64, max=64)
	@Column(nullable=false, updatable=true, length=64)
	private String passwordHash;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, updatable=true, name="groupAlias")
	private Group group;

	@NotNull @Valid
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name="family", column=@Column(name="surname")),
		@AttributeOverride(name="given", column=@Column(name="forename"))
	})
	private Name name;

	@NotNull @Valid
	@Embedded
	@AttributeOverrides({})
	private Address address;

	@NotNull
	@ElementCollection
	@CollectionTable(
	   schema="cookbook",
	   name="PhoneAssociation",
	   joinColumns=@JoinColumn(nullable=false, updatable=false, name="personReference"),
	   uniqueConstraints=@UniqueConstraint(columnNames={"personReference", "phone"})
	)
	@Column(nullable=false, updatable=false, name="phone", length=16)
	private Set<String> phones;

	// *:1 relationship
	// do not specify @NotNull because of web-service context
	@ManyToOne(optional=false)
	@JoinColumn(nullable=false, updatable=true, name="avatarReference")
	private Document avatar;

	// 1:* mirror relationship
	@NotNull
	@OneToMany(mappedBy="author", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})	// mirror
	private Set<Recipe> recipes;

	// 1:* mirror relationship
	@NotNull
	@OneToMany(mappedBy="author", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})	// mirror
	private Set<Victual> victuals;


	/**
	 * Initializes a new instance.
	 */
	public Person () {
		this.email = null; 
		this.passwordHash = DEFAULT_PASSWORD_HASH;
		this.group = Group.USER;
		this.name = new Name();
		this.address = new Address();
		this.phones = new HashSet<String>();
		this.avatar = null;
		this.recipes = Collections.emptySet();
		this.victuals = Collections.emptySet();
	}


	/**
	 * Returns the email.
	 * @return the email
	 */
	@JsonbProperty
	public String getEmail () {
		return this.email;
	}


	/**
	 * Sets the email.
	 * @param email the email
	 */
	public void setEmail (final String email) {
		this.email = email;
	}


	/**
	 * Returns the password hash.
	 * @return the password hash
	 */
	@JsonbTransient
	public String getPasswordHash () {
		return this.passwordHash;
	}


	/**
	 * Sets the password hash.
	 * @param passwordHash the password hash
	 */
	public void setPasswordHash (final String passwordHash) {
		this.passwordHash = passwordHash;
	}


	/**
	 * Returns the group.
	 * @return the group
	 */
	@JsonbProperty
	public Group getGroup () {
		return this.group;
	}


	/**
	 * Sets the group.
	 * @param group the group
	 */
	public void setGroup (final Group group) {
		this.group = group;
	}


	/**
	 * Returns the name.
	 * @return the name
	 */
	@JsonbProperty
	public Name getName () {
		return this.name;
	}


	/**
	 * Sets the name.
	 * @param name the name
	 */
	protected void setName (final Name name) {
		this.name = name;
	}


	/**
	 * Returns the address.
	 * @return the address
	 */
	@JsonbProperty
	public Address getAddress () {
		return this.address;
	}


	/**
	 * Sets the address.
	 * @param address the address
	 */
	protected void setAddress (final Address address) {
		this.address = address;
	}


	/**
	 * Returns the phones.
	 * @return the phones
	 */
	@JsonbProperty
	public Set<String> getPhones () {
		return this.phones;
	}


	/**
	 * Sets the phones.
	 * @param phones the phones
	 */
	protected void setPhones (final Set<String> phones) {
		this.phones = phones;
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
	 * Returns the recipes.
	 * @return the 1:* related recipes authored
	 */
	@JsonbTransient
	public Set<Recipe> getRecipes () {
		return this.recipes;
	}


	/**
	 * Sets the recipes.
	 * @param recipes the 1:* related recipes authored
	 */
	protected void setRecipes (final Set<Recipe> recipes) {
		this.recipes = recipes;
	}


	/**
	 * Returns the victuals.
	 * @return the 1:* related victuals authored
	 */
	@JsonbTransient
	public Set<Victual> getVictuals () {
		return this.victuals;
	}


	/**
	 * Sets the victuals.
	 * @param victuals the 1:* related victuals authored
	 */
	protected void setVictuals (final Set<Victual> victuals) {
		this.victuals = victuals;
	}
}
