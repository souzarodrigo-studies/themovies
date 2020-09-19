package themovies.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import themovies.helpers.convert.LocalDateDeserializer;
import themovies.helpers.convert.LocalDateSerializer;

public class Sample {

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate orderDate = LocalDate.parse("2015-10-07");

    public LocalDate getOrderDate() {
        return orderDate;
    }
}
