package net.cilution.sg.restfulwebservice;

import org.json.JSONException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfulwebserviceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private ApplicationContext applicationContext;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() throws Exception {
        GreetingController controller = applicationContext.getBean(GreetingController.class);
        controller.counter = new AtomicLong();
    }

    @Test
    public void testGreetingWithNoParameters() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/greeting",
                HttpMethod.GET, entity, String.class);

        String expected = "{id:1,content:\"Hello, World\"}";

        assertEquals(200, response.getStatusCodeValue());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGreetingWithNameParameter() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/greeting?name=Charlie",
                HttpMethod.GET, entity, String.class);

        String expected = "{id:1,content:\"Hello, Charlie\"}";

        assertEquals(200, response.getStatusCodeValue());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void testGreetingIdIncrementsWithMultipleCalls() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String>[] responses = new ResponseEntity[3];
        for (int i = 0; i < responses.length; i++) {
            responses[i] = restTemplate.exchange(
                    "http://localhost:" + port + "/greeting",
                    HttpMethod.GET, entity, String.class);
        }

        JSONAssert.assertEquals("{id:1,content:\"Hello, World\"}", responses[0].getBody(), false);
        JSONAssert.assertEquals("{id:2,content:\"Hello, World\"}", responses[1].getBody(), false);
        JSONAssert.assertEquals("{id:3,content:\"Hello, World\"}", responses[2].getBody(), false);
    }
}