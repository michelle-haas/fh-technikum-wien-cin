package at.technikumwien.personwebapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest // spring container wird hochgefahren
@ActiveProfiles("test")
@AutoConfigureMockMvc // mockt die Web Schicht weg
@Tag("integration-test")
public class PersonResourceTest {
    @Autowired
    private MockMvc mockMvc; // mockt die Web Schicht

    @Autowired
    private ObjectMapper om;

    @Test // wieder wird JUnit verwendet
    public void testRetrieve() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/resources/persons/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testRetrieveAll() throws Exception {
        var requestBuilder = MockMvcRequestBuilders
                .get("/resources/persons")
                .accept(MediaType.APPLICATION_JSON);

        // andere Variante als oben
        var response = mockMvc.perform(requestBuilder)
                .andReturn().getResponse(); // liefert ein Response Objekt, von der wir die Daten holen

        // bei dieser Variante brauchen wir einen Objectmapper
        var persons = om.readValue(
                response.getContentAsString(),
                // was wollen wir zurück:
                new TypeReference<List<Person>>(){} // List<Person>.class -> das geht in Java nicht
        );

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertThat(response.getContentType()).containsIgnoringCase(MediaType.APPLICATION_JSON_VALUE);
        assertEquals(1, persons.size());
    }

    // add further tests here
}
