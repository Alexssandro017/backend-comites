package mx.edu.utez.viba22.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtProvider {
    @org.springframework.beans.factory.annotation.Value("${jwt.secret}")
    private String secret;
    @org.springframework.beans.factory.annotation.Value("${jwt.expiration}")
    private long expiration;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_TYPE = "Bearer"; public String generateToken(Authentication auth){
        UserDetails user = (UserDetails) auth.getPrincipal();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getAuthorities());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(
                tokenCreateTime.getTime() + expiration * 1000
        );

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseJwtClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims resolveClaims(HttpServletRequest request){
        try{
            String token = resolveToken(request);
            if (token != null)
                return parseJwtClaims(token);
            return null;
        }catch (ExpiredJwtException e){
            throw e;
        }catch (Exception e) {
            throw e;
        }
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_TYPE))
            return bearerToken.replace(TOKEN_TYPE, "");
        // bearerToken.substring(TOKEN_TYPE.length());
        return null;
    }

    public boolean validateClaims(Claims claims, String token){
        try{
            parseJwtClaims(token);
            return claims.getExpiration().after(new Date());
        }catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
