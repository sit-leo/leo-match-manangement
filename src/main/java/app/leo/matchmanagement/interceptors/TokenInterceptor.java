package app.leo.matchmanagement.interceptors;

import app.leo.matchmanagement.adapters.UserAdapter;
import app.leo.matchmanagement.dto.GetTokenRequest;
import app.leo.matchmanagement.dto.TokenDTO;
import app.leo.matchmanagement.dto.User;
import app.leo.matchmanagement.exceptions.BadRequestException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


public class TokenInterceptor implements HandlerInterceptor {
    private String SECRET;

    private UserAdapter userAdapter = new UserAdapter();

    public TokenInterceptor(String secret) {
        this.SECRET = secret;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BadRequestException    {
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
        } else if (!token.startsWith("Bearer") ||token.length() < 7) {
            return false;
        } else if(isExpires(token)){
            return false;
        }
        return true;
    }

    private boolean isExpires(String token){
        String username = getUsernameFromToken(token);
        GetTokenRequest getTokenRequest = new GetTokenRequest();
        getTokenRequest.setToken(token);
        getTokenRequest.setUsername(username);

        TokenDTO tokenDTO = userAdapter.getTokenByUsernameAndToken(getTokenRequest.getToken());
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(new Date(System.currentTimeMillis()).after(tokenDTO.getExpiresTime()));
        return new Date(System.currentTimeMillis()).after(tokenDTO.getExpiresTime());
    }

    private String getToken (HttpServletRequest httpServletRequest) throws BadRequestException {
        String token = httpServletRequest.getHeader("Authorization");
        if (!this.isValidToken(token)) {
            userAdapter.deleteAllTokenByUsername(getUsernameFromToken(token));
            throw new BadRequestException("Invalid authorization provided.");
        }
        return token;
    }

    private User getUserFromToken(String token) {
        String tokenFormat = token.substring(7);

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

    private String getUsernameFromToken(String token){
        String tokenFormat = token.substring(7);
        Jws<Claims> claims = Jwts.parser().setSigningKey(this.SECRET).parseClaimsJws(tokenFormat);
        return (String) claims.getBody().get("sub");
    }
}