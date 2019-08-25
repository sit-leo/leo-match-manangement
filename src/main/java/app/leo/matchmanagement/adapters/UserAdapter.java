package app.leo.matchmanagement.adapters;

import app.leo.matchmanagement.dto.GetTokenRequest;
import app.leo.matchmanagement.dto.TokenDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserAdapter {
    @Value("${user.api.url}")
    private String userApiUrl;

    public TokenDTO getTokenByUsernameAndToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(userApiUrl + "getToken");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<GetTokenRequest> entity = new HttpEntity<GetTokenRequest>(headers);
        ResponseEntity<TokenDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, TokenDTO.class);
        return responseEntity.getBody();
    }

    public void deleteAllTokenByUsername(String username) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(userApiUrl + "/logout" + "/" + username);
        restTemplate.delete(url);
    }
}