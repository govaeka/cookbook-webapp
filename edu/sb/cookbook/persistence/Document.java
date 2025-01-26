package edu.sb.cookbook.persistence;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.persistence.annotations.CacheIndex;
import edu.sb.tool.Copyright;
import edu.sb.tool.HashCodes;
import edu.sb.tool.JsonProtectedPropertyStrategy;


/**
 * JPA based entity type representing documents. Note that appending
 * the MariaDB/MySQL configuration in /etc/mysql/mysql.c or
 * /etc/mysql/mariadb.c is required in order to enable transport of
 * BLOB content larger than 16MB:<pre>
 * # Allow huge blob packets
 * max_allowed_packet=2G
 * </pre>
 */
@Entity
@Table(schema="cookbook", name="Document", indexes=@Index(columnList="hash", unique=true))
@PrimaryKeyJoinColumn(name="documentIdentity")
@DiscriminatorValue("Document")
@JsonbVisibility(JsonProtectedPropertyStrategy.class)
@Copyright(year=2005, holders="Sascha Baumeister")
public class Document extends AbstractEntity {

	@NotNull @Size(min=64, max=64)
	@Column(nullable=false, updatable=false, length=64, unique=true)
	@CacheIndex(updateable=false)
	private String hash;

	@NotNull @Size(min=3, max=63)
	@Column(nullable=false, updatable=true, length=63)
	private String type;

	@Size(min=1, max=127)
	@Column(nullable=true, updatable=true, length=127)
	private String description;

	@NotNull
	@Column(nullable=false, updatable=false, length=Integer.MAX_VALUE)
	private byte[] content;


	/**
	 * Initializes a new instance.
	 */
	protected Document () {
		this(null);
	}


	/**
	 * Initializes a new instance.
	 * @param content the content
	 */
	public Document (final byte[] content) {
		this.hash = HashCodes.sha2HashText(256, content);
		this.type = "application/octet-stream";
		this.description = null; 
		this.content = content;
	}


	/**
	 * Returns the hash.
	 * @return the content hash
	 */
	@JsonbProperty
	public String getHash () {
		return this.hash;
	}
	

	/**
	 * Sets the hash.
	 * @param hash the content hash
	 */
	protected void setHash (final String hash) {
		this.hash = hash;
	}


	/**
	 * Returns the type.
	 * @return the content type
	 */
	@JsonbProperty
	public String getType () {
		return this.type;
	}


	/**
	 * Sets the type.
	 * @param type the content type
	 */
	public void setType (final String type) {
		this.type = type;
	}

	
	/**
	 * Returns the description.
	 * @return the content description
	 */
	@JsonbProperty
	public String getDescription () {
		return this.description;
	}

	
	/**
	 * Sets the description.
	 * @param description the content description
	 */
	public void setDescription (final String description) {
		this.description = description;
	}
 
	
	/**
	 * Returns the content.
	 * @return the content
	 */
	@JsonbTransient
	public byte[] getContent () {
		return this.content;
	}


	/**
	 * Sets the content.
	 * @param content the content
	 */
	protected void setContent (final byte[] content) {
		this.content = content;
	}


	/**
	 * Returns the size.
	 * @return the content size
	 */
	@JsonbProperty
	protected int getSize () {
		return this.content == null ? 0 : this.content.length;
	}
}
