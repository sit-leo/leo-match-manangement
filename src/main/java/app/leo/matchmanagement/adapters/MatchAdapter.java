package app.leo.matchmanagement.adapters;

import app.leo.matchmanagement.dto.ApplicantMatch;
import app.leo.matchmanagement.dto.RecruiterMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchAdapter {

    @Value("${matching.api.url}")
    private String matchingApiUrl;

    private Logger logger = LoggerFactory.getLogger(MatchAdapter.class);

    public List<Long> getMatchIdByApplicantId(){
        List<Long> matchIdList = new ArrayList<>();
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(matchingApiUrl + "/applicant/matches");
            ResponseEntity<List<ApplicantMatch>> responseEntity =restTemplate.exchange(url, HttpMethod.GET,null,new ParameterizedTypeReference<List<ApplicantMatch>>(){});
            List<ApplicantMatch> applicantMatchList = responseEntity.getBody();
            for(ApplicantMatch applicantMatch:applicantMatchList){
                matchIdList.add(applicantMatch.getMatchId());
            }
        }catch (NullPointerException exception){
            logger.warn(exception.getMessage());
        }
        return matchIdList;
    }

    public List<Long> getMatchIdByRecruiterId(){
        List<Long> matchIdList = new ArrayList<>();
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = String.format(matchingApiUrl + "/recruiter/matches");
            ResponseEntity<List<RecruiterMatch>> responseEntity =restTemplate.exchange(url, HttpMethod.GET,null,new ParameterizedTypeReference<List<RecruiterMatch>>(){});
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
