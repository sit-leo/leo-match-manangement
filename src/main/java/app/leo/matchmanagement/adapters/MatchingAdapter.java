package app.leo.matchmanagement.adapters;

import app.leo.matchmanagement.dto.ApplicantMatch;
import app.leo.matchmanagement.dto.RecruiterMatch;
import app.leo.matchmanagement.dto.UserMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MatchingAdapter {

    @Value("${matching.api.url}")
    private String matchingApiUrl;

    private Logger logger = LoggerFactory.getLogger(MatchingAdapter.class);

    public List<Long> getMatchIdByUserId(String token){
        List<Long> matchIdList = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(matchingApiUrl + "/user/matches");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println(entity.getHeaders());
            ResponseEntity<List<UserMatch>> responseEntity =restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<UserMatch>>(){}
            );
            List<UserMatch> userMatchList = responseEntity.getBody();
            for(UserMatch userMatch:userMatchList){
                matchIdList.add(userMatch.getMatchId());
            }
        }catch (NullPointerException exception){
            logger.warn(exception.getMessage());
        }
        return matchIdList;
    }
}
