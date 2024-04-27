package com.nighthawk.spring_portfolio.mvc.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;


@Controller
public class JwtViewController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PersonDetailsService personDetailsService;

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@GetMapping("/login")
    public String personForm(Model model) {
        model.addAttribute("person", new Person());
        return "login";
    }

	@PostMapping("/authenticateForm")
	public String createAuthenticationTokenForm(@ModelAttribute Person authenticationRequest, Model model) throws Exception {
		try {
			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
			final UserDetails userDetails = personDetailsService
					.loadUserByUsername(authenticationRequest.getEmail());

			// Get the roles of the user
			List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

			// Generate the token with the roles
			final String token = jwtTokenUtil.generateToken(userDetails, roles);

			if (token == null) {
				model.addAttribute("error", "Token generation failed");
				return "login";
			}

			// If you want to set a cookie, you'll need to do it in the client's browser
			// You can add the token to the model and set the cookie in JavaScript
			model.addAttribute("token", token);
			return "greet"; // redirect to the home page after successful login
		} catch (Exception e) {
			model.addAttribute("error", "Invalid email or password");
			return "login"; // go back to the login page if authentication fails
		}
	}
}