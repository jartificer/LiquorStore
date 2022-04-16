package dev.jartificer.liquorstore.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.jartificer.liquorstore.service.EndUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static dev.jartificer.liquorstore.security.SecurityConstants.*;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final EndUserService endUserService;

  public JwtRequestFilter(EndUserService endUserService) {
    this.endUserService = endUserService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String header = request.getHeader(HEADER_STRING);

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);

    DecodedJWT decodedJwt =
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token.replace(TOKEN_PREFIX, ""));

    String user = decodedJwt.getSubject();
    String authority = decodedJwt.getClaim("role").asString();

    UserDetails userDetails = endUserService.loadUserByUsername(user);

    if (user == null) {
      return null;
    }
    return new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + authority.toUpperCase())));
  }
}
