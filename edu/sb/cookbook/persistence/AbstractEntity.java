package edu.sb.cookbook.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Positive;
import edu.sb.tool.Copyright;
import edu.sb.tool.Correlated;
import edu.sb.tool.Correlated.Operator;
import edu.sb.tool.JsonProtectedPropertyStrategy;


/**
 * JPA based entity type representing entities.
 */
@Entity
@Table(schema="cookbook", name="AbstractEntity", indexes=@Index(columnList="discriminator"))
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="discriminator")
@Correlated(operator=Operator.LESS_EQUAL, leftAccessPath="created", rightAccessPath="modified")
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2005, holders="Sascha Baumeister")
public abstract class AbstractEntity extends Object implements Comparable<AbstractEntity> {

	@PositiveOrZero
	@Id	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long identity;

	@Positive
	@Column(nullable=false, updatable=true) @Version
	private int version;

	@Column(nullable=false, updatable=false)
	private long created;

	@Column(nullable=false, updatable=true)
	private long modified;


	/**
	 * Initializes a new instance.
	 */
	public AbstractEntity () {
		this.identity = 0;
		this.version = 1;
		this.created = System.currentTimeMillis();
		this.modified = this.created;
	}


	/**
	 * Returns the identity.
	 * @return the identity
	 */
	@JsonbProperty
	public long getIdentity () {
		return this.identity;
	}


	/**
	 * Sets the identity.
	 * @param identity the identity
	 */
	protected void setIdentity (final long identity) {
		this.identity = identity;
	}


	/**
	 * Returns the version.
	 * @return the version
	 */
	@JsonbProperty
	public int getVersion () {
		return this.version;
	}


	/**
	 * Sets the version.
	 * @param version the version
	 */
	public void setVersion (final int version) {
		this.version = version;
	}


	/**
	 * Returns the created.
	 * @return the creation timestamp in ms since 1/1/1970
	 */
	@JsonbProperty
	public long getCreated () {
		return this.created;
	}


	/**
	 * Sets the created.
	 * @param created the creation timestamp in ms since 1/1/1970
	 */
	protected void setCreated (final long created) {
		this.created = created;
	}


	/**
	 * Returns the modified.
	 * @return the modification timestamp in ms since 1/1/1970
	 */
	@JsonbProperty
	public long getModified () {
		return this.modified;
	}


	/**
	 * Sets the modified.
	 * @param modified the modification timestamp in ms since 1/1/1970
	 */
	public void setModified (final long modified) {
		this.modified = modified;
	}


	/**
	 * Returns the discriminator.
	 * @return the discriminator
	 */
	@JsonbProperty
	protected String getDiscriminator () {
		return this.getClass().getSimpleName();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo (final AbstractEntity other) {
		return Long.compare(this.identity, other.identity);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString () {
		return String.format("%s@%d", this.getClass().getName(), this.identity);
	}
}
