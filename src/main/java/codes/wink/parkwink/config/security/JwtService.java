package codes.wink.parkwink.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Service class for managing JSON Web Tokens (JWTs).
 * Provides methods for extracting claims from tokens, generating tokens, and verifying tokens.
 *
 * <p>Annotations:
 * <ul>
 * <li>@Service - Indicates that this class is a Spring service component.</li>
 * </ul>
 * </p>
 */
@Service
public class JwtService {

    /**
     * The secret key used for signing and verifying JWTs.
     * Loaded from the application properties.
     */
    @Value("${secret.key}")
    private String SECRET;

    /**
     * Extracts the username (subject) from the JWT.
     *
     * @param token the JWT from which to extract the username.
     * @return the username (subject) contained in the JWT.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT.
     *
     * @param <T>            the type of the claim to extract.
     * @param token          the JWT from which to extract the claim.
     * @param claimsResolver a function to apply to the claims extracted from the token.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT.
     *
     * @param token the JWT from which to extract the claims.
     * @return the claims contained in the JWT.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generates a JWT with the specified extra claims and subject.
     *
     * @param extraClaims additional claims to include in the JWT.
     * @param mail        the subject (typically the user's email) to include in the JWT.
     * @return the generated JWT as a string.
     */
    public String generate(Map<String, Object> extraClaims, String mail) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(mail)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Generates a JWT with the specified subject and no extra claims.
     *
     * @param mail the subject (typically the user's email) to include in the JWT.
     * @return the generated JWT as a string.
     */
    public String generate(String mail) {
        return generate(new HashMap<>(), mail);
    }

}
