package dsk.otus.softwarearchitect.task4.test;

import dsk.otus.softwarearchitect.task4.test.entity.UserEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class Task4AdapterRest {
    public UserEntity createUser(String uri, UserEntity user) {
        String url = "http://"+uri+"/users/";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<UserEntity> request = new HttpEntity(user, headers);

        ResponseEntity<UserEntity> response = restTemplate
                    .postForEntity(url, request, UserEntity.class);

        return response.getBody();
    }
    public UserEntity getUser(String uri, String id) {
        String url = "http://"+uri+"/users/"+id;

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<UserEntity> response = restTemplate
                .getForEntity(url, UserEntity.class);

        return response.getBody();
    }
    public List<UserEntity> getUser(String uri) {
        String url = "http://"+uri+"/users/";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List> response = restTemplate
                .getForEntity(url, List.class);

        return response.getBody();
    }
    public UserEntity updateUser(String uri, UserEntity user) {
        String url = "http://"+uri+"/users/"+user.getId();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<UserEntity> request = new HttpEntity(user, headers);

        restTemplate.put(url, request, UserEntity.class);

        return user;
    }
    public void deleteUser(String uri, String id) {
        String url = "http://"+uri+"/users/"+id;

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.delete(url);
    }
    public List<UserEntity> getUsers(String uri) {
        String url = "http://"+uri+"/users/";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<UserEntity> request = new HttpEntity(null, headers);

        ResponseEntity<UserEntity[]> response = restTemplate
                .getForEntity(url, UserEntity[].class);
        return Arrays.asList(response.getBody());
    }

}
