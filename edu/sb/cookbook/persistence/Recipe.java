package edu.sb.cookbook.persistence;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import edu.sb.cookbook.persistence.Victual.Diet;
import edu.sb.tool.Copyright;
import edu.sb.tool.JsonProtectedPropertyStrategy;
import edu.sb.tool.Representable;


/**
 * JPA based entity type representing recipes.
 */
@Entity
@Table(schema="cookbook", name="Recipe", indexes=@Index(columnList="title", unique=true))
@PrimaryKeyJoinColumn(name="recipeIdentity")
@DiscriminatorValue("Recipe")
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2022, holders="Sascha Baumeister")
public class Recipe extends AbstractEntity implements Representable {
	static public enum Category { MAIN_COURSE, APPETIZER, SNACK, DESSERT, BREAKFAST, BUFFET, BARBEQUE, ADOLESCENT, INFANT }
	static public final Comparator<Recipe> TITLE_COMPARATOR = Comparator.comparing(Recipe::getTitle);

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, updatable=true)
	private Category category;

	@NotNull @Size(min=1, max=128)
	@Column(nullable=false, updatable=true, length=128, unique=true)
	private String title;

	@Size(min=1, max=4094)
	@Column(nullable=true, updatable=true, length=4094)
	private String description;

	@Size(min=1, max=4094)
	@Column(nullable=true, updatable=true, length=4094)
	private String instruction;

	// *:1 relationship
	// do not specify @NotNull because of web-service context
	@ManyToOne(optional=false)
	@JoinColumn(nullable=false, updatable=true, name="avatarReference")
	private Document avatar;

	// *:0..1 relationship
	@ManyToOne(optional=true)
	@JoinColumn(nullable=true, updatable=true, name="authorReference")
	private Person author;

	// 1:* mirror relationship
	@NotNull
	@OneToMany(mappedBy="recipe", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})	// mirror
	private Set<Ingredient> ingredients;

	// *:* relationship
	@NotNull
	@ManyToMany
	@JoinTable(
	   schema="cookbook",
	   name="RecipeIllustrationAssociation",
	   joinColumns=@JoinColumn(nullable=false, updatable=false, name="recipeReference"),
	   inverseJoinColumns=@JoinColumn(nullable=false, updatable=false, name="illustrationReference"),
	   uniqueConstraints=@UniqueConstraint(columnNames={"recipeReference", "illustrationReference"})
	)
	private Set<Document> illustrations;


	/**
	 * Initializes a new instance.
	 */
	public Recipe () {
		this.category = Category.MAIN_COURSE;   
		this.title = null; 
		this.description = null; 
		this.instruction = null; 
		this.avatar = null; 
		this.author = null;
		this.ingredients = Collections.emptySet();
		this.illustrations = new HashSet<>();
	}


	/**
	 * Returns the title.
	 * @return the title
	 */
	@JsonbProperty
	public String getTitle () {
		return this.title;
	}

	
	/**
	 * Sets the title.
	 * @param title the title
	 */
	public void setTitle (final String title) {
		this.title = title;
	}

	
	/**
	 * Returns the description.
	 * @return the description, or null for none
	 */
	@JsonbProperty
	public String getDescription () {
		return this.description ;
	}

	
	/**
	 * Sets the description.
	 * @param description the description, or null for none
	 */
	public void setDescription (final String description) {
		this.description = description;
	}

	
	/**
	 * Returns the instruction.
	 * @return the instruction, or null for none
	 */
	@JsonbProperty
	public String getInstruction () {
		return this.instruction;
	}

	
	/**
	 * Sets the instruction.
	 * @param instruction the instruction, or null for none
	 */
	public void setInstruction (final String instruction) {
		this.instruction = instruction;
	}

	
	/**
	 * Returns the category.
	 * @return the category
	 */
	@JsonbProperty
	public Category getCategory () {
		return this.category;
	}

	
	/**
	 * Sets the category.
	 * @param category the category
	 */
	public void setCategory (Category category) {
		this.category = category;
	}


	/**
	 * Returns the ingredient count.
	 * @return the ingredient count
	 */
	@JsonbProperty
	protected int getIngredientCount () {
		return this.ingredients.size();
	}


	/**
	 * Returns the illustration count.
	 * @return the illustration count
	 */
	@JsonbProperty
	protected int getIllustrationCount () {
		return this.illustrations.size();
	}


	/**
	 * Returns the diet.
	 * @return the diet
	 */
	@JsonbProperty
	public Diet getDiet () {
		return this.ingredients.stream()
			.map(Ingredient::getVictual)
			.map(Victual::getDiet)
			.min(Comparator.naturalOrder())
			.orElse(Diet.VEGAN);
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
	 * @return the *:0..1 related author's identity
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


	/**
	 * Returns the ingredients.
	 * @return the 1:* related ingredients
	 */
	@JsonbTransient
	public Set<Ingredient> getIngredients () {
		return this.ingredients;
	}


	/**
	 * Sets the ingredients.
	 * @param ingredients the 1:* related ingredients
	 */
	protected void setIngredients (final Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}


	/**
	 * Returns the illustrations.
	 * @return the 1:* related illustrations
	 */
	@JsonbTransient
	public Set<Document> getIllustrations () {
		return this.illustrations;
	}

	
	/**
	 * Sets the illustrations.
	 * @param illustrations the 1:* related illustrations
	 */
	protected void setIllustrations (final Set<Document> illustrations) {
		this.illustrations = illustrations;
	}
}
