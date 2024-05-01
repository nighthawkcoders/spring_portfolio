package com.nighthawk.spring_portfolio.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private PersonDetailsService personDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	/**
	 * This method is responsible for filtering incoming HTTP requests. It extracts the JWT token from the cookies,
	 * validates the token, and sets the authentication in the security context if the token is valid.
	 *
	 * @param request  the incoming HTTP request
	 * @param response the HTTP response
	 * @param chain    the filter chain
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
		Optional<String> jwtToken = getJwtTokenFromCookies(request.getCookies());
		String origin = request.getHeader("X-Origin");
		
		// If the request is coming from the client, check if there is a JWT token
    	if (origin != null && origin.equals("client")) {
			// If there is no JWT token, leave method and go to filter chain
			if (!jwtToken.isPresent()) {
				logger.warn("doFilterInternal client request, no cookie: " + request.getRequestURI() + " " + request.getMethod() + " " + request.getRemoteAddr() + " " + request.getRemoteHost() + " " + request.getRemotePort());
				chain.doFilter(request, response);
				return;
			}
		// Else the request is coming from the server, leave method and go to filter chain
		} else {
			logger.warn("doFilterInternal server request: " + request.getRequestURI() + " " + request.getMethod() + " " + request.getRemoteAddr() + " " + request.getRemoteHost() + " " + request.getRemotePort());
			chain.doFilter(request, response);
			return;
		}

		// If there is a JWT token, extract the username and set the authentication
		logger.warn("doFilterInternal client request, with cookie: " + request.getRequestURI() + " " + request.getMethod() + " " + request.getRemoteAddr() + " " + request.getRemoteHost() + " " + request.getRemotePort());
		try {
			String username = jwtTokenUtil.getUsernameFromToken(jwtToken.get());
	
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.personDetailsService.loadUserByUsername(username);
	
				if (jwtTokenUtil.validateToken(jwtToken.get(), userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					logger.warn( userDetails.getUsername() + " " + userDetails.getAuthorities() + " " + request.getRequestURI());
				}
			}
		} catch (IllegalArgumentException e) {
			logger.error("Unable to get JWT Token", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unable to get JWT Token");
			return;
		} catch (ExpiredJwtException e) {
			logger.error("JWT Token has expired", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
			return;
		} catch (Exception e) {
			logger.error("An error occurred", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "An error occurred");
			return;
		}
	
		chain.doFilter(request, response);
	}
	
	/**
	 * This method is responsible for extracting the JWT token from the cookies. It returns an Optional<String> that
	 * contains the JWT token if it exists, or an empty Optional if it doesn't exist.
	 *
	 * @param cookies the array of cookies from the HTTP request
	 * @return an Optional<String> containing the JWT token, or an empty Optional if the token doesn't exist
	 */
	private Optional<String> getJwtTokenFromCookies(Cookie[] cookies) {
		if (cookies == null || cookies.length == 0) {
			//logger.warn("No cookies");
			return Optional.empty();
		}
	
		return Arrays.stream(cookies)
			.filter(cookie -> cookie.getName().equals("jwt"))
			.map(Cookie::getValue)
			.findFirst();
	}
}