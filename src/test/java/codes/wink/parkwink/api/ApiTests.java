package codes.wink.parkwink.api;

import codes.wink.parkwink.utils.response.RestError;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class ApiTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager manager;

    @LocalServerPort
    private int port;

    @Test
    public void contextLoads() {
        assertNotNull(restTemplate);
        assertNotNull(mockMvc);
        assertNotNull(manager);
    }

    @Test
        //test that http requests work with a mock mvc
    void mockRequest() {
        String response = restTemplate.getForObject("http://localhost:" + port, String.class);
        assertFalse(response.isBlank());
    }

    @Test //test that the RestError class can change http status based on the exception
    public void restError404() {
        RestError restError = new RestError(new NoSuchElementException());
        assertEquals(restError.getStatus(), HttpStatus.NOT_FOUND);
    }
}
