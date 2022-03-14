package com.example.liquorstore.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.example.liquorstore.security.SecurityConstants.*;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


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

    if (token != null) {
      String user =
              JWT.require(Algorithm.HMAC256(SECRET))
                      .build()
                      .verify(token.replace(TOKEN_PREFIX, ""))
                      .getSubject();

      String authority = JWT.require(Algorithm.HMAC256(SECRET))
              .build()
              .verify(token.replace(TOKEN_PREFIX, ""))
              .getClaim("role").asString();



      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + authority.toUpperCase())));
      }

      return null;
    }

    return null;
  }
}
