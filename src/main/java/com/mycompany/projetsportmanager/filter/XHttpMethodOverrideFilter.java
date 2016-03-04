/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * {@link javax.servlet.Filter} that converts X-HTTP-Method-Override headers method names into HTTP methods,
 * retrievable via {@link HttpServletRequest#getMethod()}.
 *
 * @author m414758
 */
public class XHttpMethodOverrideFilter extends OncePerRequestFilter {

	/** Default header name*/
	public static final String DEFAULT_HEADER_NAME = "X-HTTP-Method-Override";

	private String headerParam = DEFAULT_HEADER_NAME;

	/**
	 * Set the header name to look for HTTP methods.
	 * @see #DEFAULT_HEADER_NAME
	 */
	public void setMethodParam(String headerParam) {
		Assert.hasText(headerParam, "'headerParam' must not be empty");
		this.headerParam = headerParam;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String headerValue = request.getHeader(this.headerParam);
		if ("POST".equals(request.getMethod()) && StringUtils.hasLength(headerValue)) {
			String method = headerValue.toUpperCase(Locale.ENGLISH);
			HttpServletRequest wrapper = new HttpMethodRequestWrapper(request, method);
			filterChain.doFilter(wrapper, response);
		}
		else {
			filterChain.doFilter(request, response);
		}
	}


	/**
	 * Simple {@link HttpServletRequest} wrapper that returns the supplied method for
	 * {@link HttpServletRequest#getMethod()}.
	 */
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}

}

