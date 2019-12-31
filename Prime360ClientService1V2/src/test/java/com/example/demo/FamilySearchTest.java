package com.example.demo;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.psx.prime360ClientService.entity.Profile;

@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FamilySearchTest {

	@Test
	public void agetRelationalProfiles() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:2056/requestPosting/getRelationalProfiles";
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		ResponseEntity<List<Profile>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Profile>>() {});
		List<Profile> profiles = responseEntity.getBody();
		for (Profile profile : profiles) {
			System.out.println(profile);
		}
		assert (true);
	}
}
