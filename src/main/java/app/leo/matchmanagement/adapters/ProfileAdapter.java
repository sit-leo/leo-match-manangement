package app.leo.matchmanagement.adapters;

import app.leo.matchmanagement.dto.ApplicantInMemberList;
import app.leo.matchmanagement.dto.RecruiterInMemberList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class ProfileAdapter {

    @Value("${profile.api.url}")
    private String profileApiUrl;

    public List<ApplicantInMemberList> getApplicantListByIdList(String token,Long[] ids){
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder(profileApiUrl + "/profile/applicants/");
        byte i=0;
        for(long id:ids){
            if(i == 1){
                url.append(",");
                i=0;
            }
            url.append(id);
            i = 1;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<ApplicantInMemberList>> responseEntity = restTemplate.exchange(url.toString(), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<ApplicantInMemberList>>() {
                });
        return responseEntity.getBody();
    }

    public List<RecruiterInMemberList> getRecruiterListByIdList(String token, Long [] ids){
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder url = new StringBuilder(profileApiUrl + "/profile/recruiters/");
        byte i = 0;
        for(long id:ids){
            if(i == 1){
                url.append(",");
                i = 0;
            }
            url.append(id);
            i = 1;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<RecruiterInMemberList>> responseEntity = restTemplate.exchange(url.toString(), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<RecruiterInMemberList>>() {
                });
        return responseEntity.getBody();
    }
}
