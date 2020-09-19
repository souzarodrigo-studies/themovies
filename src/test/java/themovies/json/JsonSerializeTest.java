package themovies.json;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import themovies.model.Sample;

public class JsonSerializeTest {

    @Test
    public void shoudProperlyDeserializeLocalDateIntoJson() throws IOException {
        Sample actual = new ObjectMapper().readValue("{\"orderDate\":" + "\"2015-10-07\"" + "}", Sample.class);

        assertEquals(2015, actual.getOrderDate().getYear());
        assertEquals(10, actual.getOrderDate().getMonthValue());
        assertEquals(7, actual.getOrderDate().getDayOfMonth());
    }

    @Test
    public void shoudProperlySerializeLocalDateToJson() throws IOException {

        String actual = new ObjectMapper().writeValueAsString(new Sample());

        assertEquals("{\"orderDate\":\"2015-10-07\"}", actual);
    }
}
