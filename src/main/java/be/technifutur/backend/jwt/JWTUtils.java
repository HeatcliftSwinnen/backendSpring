package be.technifutur.backend.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;
import java.time.Instant;


public abstract class JWTUtils {


    public static String generateJwt(Authentication auth){
        Assert.notNull(auth, "auth should not be null");

        return JWT.create()
                .withSubject( auth.getName() )
                .withExpiresAt( Instant.ofEpochMilli( System.currentTimeMillis() + 86_400_000 ) )// Expire après 24 heures
                .withClaim("roles", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .sign( Algorithm.HMAC512("_G{9FkZb_iSxv;Z.%(V4gCrL=l'MCq&piuj3uRI5OB%EJJ~1,.") );
    }

    public static String extractToken(HttpServletRequest request){

        String authHeader = request.getHeader("Authorization");
        if( authHeader == null )
            return null;

        if( !authHeader.startsWith("Bearer ") )
            return null;

        String token = authHeader.replace("Bearer ", "").trim();

        return !token.isEmpty() ? token : null;

    }

    public static boolean isTokenValid(String token){
        // Token pas null
        if( token == null )
            return false;
        // token signé correctement
        JWTVerifier verifier = JWT.require( Algorithm.HMAC512( "_G{9FkZb_iSxv;Z.%(V4gCrL=l'MCq&piuj3uRI5OB%EJJ~1,." ) )
                // Expiration pas dépassée
                .acceptExpiresAt( 86_400_000 )
                .build();
        try {
            DecodedJWT decodedJWT = verifier.verify(token);

            return true;
        }
        catch (JWTVerificationException ex){
            return false;
        }

    }

    public static String getSubject(String token){
        return JWT.decode(token).getSubject();
    }

}