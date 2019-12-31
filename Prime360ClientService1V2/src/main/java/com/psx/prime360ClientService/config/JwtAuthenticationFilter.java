package com.psx.prime360ClientService.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.psx.prime360ClientService.exception.Prime360CustomException;
import com.psx.prime360ClientService.serviceImpl.UserServiceImpl;
import com.psx.prime360ClientService.utils.AppProperties;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * @author Manish Singh
 *
 * 15-Oct-2018
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private static Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());
	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
    private UserServiceImpl userService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private AppProperties myAppProperties;
	
	@Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(myAppProperties.getHeaderString());
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(myAppProperties.getTokenPrifix())) {
            authToken = header.replace(myAppProperties.getTokenPrifix(),"");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
                throw new Prime360CustomException("Token expired. "+e);
            } catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority("ROLE_"+userService.getUserById(username).getRoleID())));
//                ApplicationListener<AbstractAuthenticationEvent> appListener = new AuthenticationListener(); 
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}
