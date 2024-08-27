package pl.maciejsusala.roastbot.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.maciejsusala.roastbot.model.UserModel;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final String secretKey;

    public JwtService(@Value("${JWT_KEY}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String extractLogin(String token) {
        logger.info("Extracting login from token: {}", token);
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken (UserModel userModel) {
        logger.info("Generating token for user: {}", userModel.getUsername());
        return generateToken(new HashMap<>(), userModel);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserModel userModel
    ) {
        logger.info("Generating token with extra claims for user: {}", userModel.getUsername());
        extraClaims.put("role", userModel.getRole().name());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userModel.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractLogin(token);
        logger.info("Validating token for user: {}", username);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        logger.info("Extracting claim from token: {}", token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        logger.info("Extracting all claims from token: {}", token);
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        logger.info("Extracted claims: {}", claims);
        return claims;
    }

    private Key getSignInKey() {
        logger.info("Getting signing key");
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
