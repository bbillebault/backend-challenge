package fr.bbillebault.apitest.backend.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fr.bbillebault.apitest.backend.service.TokenUserDetailsService;
import fr.bbillebault.apitest.backend.util.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenRequestFilter extends OncePerRequestFilter {
	@Autowired
	private TokenUserDetailsService tokenUserDetailsService;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String requestTokenHeader = request.getHeader("Authorization");
		
		System.out.println("Validating token");
		
		if(requestTokenHeader != null) {
			// Remove "Bearer " prefix
			String token = requestTokenHeader.substring(7);
			String username = this.tokenUtil.getTokenUsername(token);
			UserDetails userDetails = this.tokenUserDetailsService.loadUserByUsername(username);
			
			if(this.tokenUtil.isTokenValid(token, userDetails)) {
				System.out.println("Valid token");
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
