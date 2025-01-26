package edu.sb.cookbook.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import edu.sb.tool.Copyright;
import edu.sb.tool.JsonProtectedPropertyStrategy;


/**
 * JPA based entity type representing ingredients.
 */
@Entity
@Table(schema="cookbook", name="Ingredient", indexes={})
@PrimaryKeyJoinColumn(name="ingredientIdentity")
@DiscriminatorValue("Ingredient")
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2022, holders="Sascha Baumeister")
public class Ingredient extends AbstractEntity {
	static public enum Unit { LITRE, GRAM, TEASPOON, TABLESPOON, PINCH, CUP, CAN, TUBE, BUSHEL, PIECE }

	@PositiveOrZero
	@Column(nullable=false, updatable=true)
	private float amount;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, updatable=true)
	private Unit unit;

	// *:1 relationship
	@NotNull @Valid
	@ManyToOne(optional=false)
	@JoinColumn(nullable=false, updatable=true, name="victualReference")
	private Victual victual;

	// *:1 relationship
	// do not specify @NotNull because of web-service context
	@ManyToOne(optional=false)
	@JoinColumn(nullable=false, updatable=false, name="recipeReference")
	private Recipe recipe;


	/**
	 * Initializes a new instance.
	 */
	protected Ingredient () {
		this(null);
	}


	/**
	 * Initializes a new instance.
	 * @param recipe the recipe
	 */
	public Ingredient (final Recipe recipe) {
		this.amount = 0;
		this.unit = Unit.GRAM;
		this.victual = null;
		this.recipe = recipe;
	}


	/**
	 * Returns the amount.
	 * @return the amount
	 */
	@JsonbProperty
	public float getAmount () {
		return this.amount;
	}


	/**
	 * Sets the amount.
	 * @param amount the amount
	 */
	public void setAmount (final float amount) {
		this.amount = amount;
	}


	/**
	 * Returns the unit.
	 * @return the unit
	 */
	@JsonbProperty
	public Unit getUnit () {
		return this.unit;
	}


	/**
	 * Sets the unit.
	 * @param unit the unit
	 */
	public void setUnit (final Unit unit) {
		this.unit = unit;
	}


	/**
	 * Returns the recipe reference.
	 * @return the *:1 related recipe's identity
	 */
	@JsonbProperty
	protected Long getRecipeReference () {
		return this.recipe == null ? null : this.recipe.getIdentity();
	}


	/**
	 * Returns the recipe.
	 * @return the *:1 related recipe
	 */
	@JsonbTransient
	public Recipe getRecipe () {
		return this.recipe;
	}


	/**
	 * Sets the recipe.
	 * @param recipe the *:1 related recipe
	 */
	protected void setRecipe (final Recipe recipe) {
		this.recipe = recipe;
	}


	/**
	 * Returns the victual.
	 * @return the *:1 related victual
	 */
	@JsonbProperty
	public Victual getVictual () {
		return this.victual;
	}


	/**
	 * Sets the victual.
	 * @param victual the *:1 related victual
	 */
	public void setVictual (final Victual victual) {
		this.victual = victual;
	}
}
