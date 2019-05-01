package app.leo.matchmanagement.adapters;

import app.leo.matchmanagement.dto.ApplicantMatch;
import app.leo.matchmanagement.dto.RecruiterMatch;
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

    public List<Long> getMatchIdByApplicantId(String token){
        List<Long> matchIdList = new ArrayList<>();
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(matchingApiUrl + "/applicant/matches");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println(entity.getHeaders());
            ResponseEntity<List<ApplicantMatch>> responseEntity =restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<ApplicantMatch>>(){}
            );
            List<ApplicantMatch> applicantMatchList = responseEntity.getBody();
            for(ApplicantMatch applicantMatch:applicantMatchList){
                matchIdList.add(applicantMatch.getMatchId());
            }
        }catch (NullPointerException exception){
            logger.warn(exception.getMessage());
        }
        return matchIdList;
    }

    public List<Long> getMatchIdByRecruiterId(String token){
        System.out.println(token);
        List<Long> matchIdList = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(matchingApiUrl + "/recruiter/matches");
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println(entity.getHeaders());
            ResponseEntity<List<RecruiterMatch>> responseEntity =restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<RecruiterMatch>>(){}
            );
            List<RecruiterMatch> recruiterMatchList = responseEntity.getBody();
            for(RecruiterMatch recruiterMatch:recruiterMatchList){
                matchIdList.add(recruiterMatch.getMatchId());
            }
        }catch (NullPointerException exception){
            logger.warn(exception.getMessage());
        }
        return matchIdList;
    }
}
