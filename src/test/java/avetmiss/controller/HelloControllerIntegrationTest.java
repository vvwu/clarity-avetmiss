package avetmiss.controller;

import avetmiss.BaseControllerIntegrationTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class HelloControllerIntegrationTest extends BaseControllerIntegrationTest {

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertThat(response.getBody(), equalTo("Hello World!"));
    }
}