package Iniro.kTrip.jwt;

import Iniro.kTrip.domain.Member;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil
{
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret)
    {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());//프로퍼티에 저장되 ㄴ키 가져오기
    }
    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }
    public String getCategory(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }
    public String getId(String token)
    {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id",String.class);
    }
    public String getEmail(String token)
    {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email",String.class);
    }
    public String getNickname(String token)
    {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("nickname",String.class);
    }
    public String getName(String token)
    {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("name",String.class);
    }
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    public String createJwt(String category, String id, String role,String email,String nickname,String name, Long expiredMs) {

        return Jwts.builder()
                .claim("category",category)
                .claim("id", id)
                .claim("role", role)
                .claim("email", email)
                .claim("nickname", nickname)
                .claim("name", name)
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
    public String createAccessToken(String category,Member member, int expiredMs){
        return Jwts.builder()
                .claim("category",category)
                .claim("id", member.getId())
                .claim("password", member.getPassword())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }


}
