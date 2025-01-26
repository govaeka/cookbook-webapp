package edu.sb.tool;

import edu.sb.cookbook.persistence.Document;


/**
 * Role interface modeling entities that can be iconized using an avatar.
 */
@Copyright(year=2017, holders="Sascha Baumeister")
public interface Representable {

	/**
	 * Returns the avatar.
	 * @return the *:1 related avatar, or {@code null} for none
	 */
	Document getAvatar ();


	/**
	 * Sets the avatar.
	 * @param avatar the *:1 related avatar, or {@code null} for none
	 */
	void setAvatar (Document avatar);
}