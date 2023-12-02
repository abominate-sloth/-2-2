package com.bsuir.kirillpastukhou.webappfilms.beans;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jakarta.servlet.http.HttpSession;

/**
 * Class for retrieving the resource bundle based on the language stored in the HttpSession.
 * The resource bundle contains localized messages for different languages.
 */
public class LangBundle {

    /**
     * Retrieves the resource bundle based on the language stored in the HttpSession.
     *
     * @param session HttpSession containing the language information
     * @return Resource bundle with localized messages
     */
    public static ResourceBundle getBundle(HttpSession session) {
        String lang = (String) session.getAttribute("lang");
        Locale locale = null;
        if (lang == "ru_RU") {
            locale = new Locale.Builder().setLanguage("ru").setRegion("RU").build();
        } else {
            locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
        }
        try {
        	return ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
        	return null;
        }
    }
}