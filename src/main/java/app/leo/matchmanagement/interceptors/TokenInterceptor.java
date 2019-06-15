package app.leo.matchmanagement.interceptors;

import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.exceptions.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    private String SECRET;

    public TokenInterceptor(String secret) {
        this.SECRET = secret;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BadRequestException {
        if (this.isOptionMethod(request)) {
            return true;
        }
        String token = getToken(request);
        User user = this.getUserFromToken(token);
        request.setAttribute("user", user);
        request.setAttribute("token", token);
        return true;
    }

    private boolean isValidToken (String token){
        if (token == null) {
            return false;
        } else if (token.startsWith("Bearer") == false || token.equals("")||token.length()< 7) {
            return false;
        }
        return true;
    }

    private String getToken (HttpServletRequest httpServletRequest) throws BadRequestException{
        String token = httpServletRequest.getHeader("Authorization");
        if (this.isValidToken(token) == false) {
            throw new BadRequestException("Invalid authorization provided.");
        }
        return token;
    }

    private String parseToken(String token) {
        return token.substring(7);
    }

    private User getUserFromToken(String token) {
        String tokenFormat = this.parseToken(token);

        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(this.SECRET)
                .parseClaimsJws(tokenFormat);

        long userId = Long.parseLong((String) claims.getBody().get("userId"));
        String role = (String) claims.getBody().get("role");

        return new User(userId, role);
    }

    private boolean isOptionMethod(HttpServletRequest request) {
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }
}