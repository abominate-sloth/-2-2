package com.bsuir.kirillpastukhou.webappfilms.filter;

import java.io.IOException;
import jakarta.servlet.Filter;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * The LanguageChangeFilter class is a filter that handles language change in the application.
 * This filter intercepts requests and checks if the "changeLocale" parameter is present.
 * If the parameter is present, it toggles the language between Russian ("ru_RU") and English ("en_US").
 * The new chosen language is stored in the session attribute "lang".
 * This filter also sets the character encoding of the request and response to UTF-8.
 */
public class LanguageChangeFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = -5251810194116656749L;
    private static final String RU = "ru_RU";
    private static final String EN = "en_US";

    /**
     * Performs the filtering operation on the request and response.
     * If the "changeLocale" parameter is present, it toggles the language between Russian and English.
     * The new chosen language is stored in the session attribute "lang".
     * The character encoding of the request and response is also set to UTF-8.
     *
     * @param request  the ServletRequest object
     * @param response the ServletResponse object
     * @param chain    the FilterChain object
     * @throws IOException      if an input or output exception occurs
     * @throws ServletException if a servlet-specific exception occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (req.getParameter("changeLocale") != null) {
            String language = (String) req.getSession().getAttribute("lang");
            if (language == RU) {
                language = EN;
            } else {
                language = RU;
            }
            req.getSession().setAttribute("lang", language);
        }

        chain.doFilter(request, response);
    }
}