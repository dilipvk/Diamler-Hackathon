
package com.secure.whatsapp;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Meal 
 */

@ParseClassName("SellCarObject")
public class SellCarObject extends ParseObject {

	public SellCarObject() {
		// A default constructor is required.
	}

	public String getName() {
		return getString("Name");
	}

	public void setName(String title) {
		put("Name", title);
	}

	public String getAddress() {
		return getString("Address");
	}

	public void setAddress(String user) {
		put("Address", user);
	}

	public String getContact() {
		return getString("Contact");
	}

	public void setContact(String rating) {
		put("Contact", rating);
	}

	public ParseFile getPhotoFile() {
		return getParseFile("photo");
	}

	public void setPhotoFile(ParseFile file) {
		put("photo", file);
	}

    public String getrating() {
        return getString("rating");
    }

    public void setrating(String rating) {
        put("rating", rating);
    }

    public String getDescription() {
        return getString("Description");
    }

    public void setDescription(String rating) {
        put("Description", rating);
    }

    public ParseUser getAuthor() {
        return getParseUser("Author");
    }

    public void setAuthor(ParseUser rating) {
        put("Author", rating);
    }


}
