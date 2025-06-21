package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;
    //    @Value("${api.security.token.expirationtime}")
//    private Long expirationTime;
    @Value("${api.security.token.tokenprefix}")
    private String tokenPrefix;
    @Value("${api.security.token.emissor}")
    private String emissor;

    public String gerarToken(User username) {
        try {
//            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            String token = JWT.create()
                    .withIssuer(Arrays.toString(emissor.getBytes()))
                    .withSubject(username.getUsername())
                    .withExpiresAt(dataExpiracao())
//                    .withExpiresAt(Instant.ofEpochSecond(expirationTime))
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            return JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer(Arrays.toString(emissor.getBytes()))
                    // reusable verifier instance
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            // Invalid signature/claims
            throw new RuntimeException("Token JWT invalido ou expirado");
        }
    }

    private Instant dataExpiracao() {

        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(
                        ZoneOffset.of("-03:00"));
    }
}
