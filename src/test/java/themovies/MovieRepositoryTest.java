package themovies;

import themovies.database.MovieManager;
import themovies.models.MovieModel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieRepositoryTest {

    @InjectMocks
    MovieManager resource;

    @Mock
    EntityManager entityManager;

    @Before
    public void begin() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve Testar o repository findbyId")
    public void listAllrepositoryTest() {
        List<MovieModel> movieEntityList = new ArrayList<>();

        MovieModel entityMock = new MovieModel();

        entityMock.setId(1L);
        entityMock.setImdbRate(10l);
        entityMock.setDuration(178l);
        entityMock.setDirector("Peter Jackson");
        entityMock.setReleaseDate(LocalDate.now());
        entityMock.setTitle("Lord of the rings - The Fellowship of the ring");
        entityMock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        movieEntityList.add(entityMock);

        TypedQuery<MovieModel> query = (TypedQuery<MovieModel>) mock(TypedQuery.class);

        when(entityManager.createQuery(" SELECT p FROM MovieModel p", MovieModel.class)).thenReturn(query);

        when(query.getResultList()).thenReturn(movieEntityList);

        List<MovieModel> response = resource.findAll();

        assertNotNull(response);
        assertEquals(movieEntityList, response);
    }

    @Test
    @DisplayName("Deve Testar o repository findbyId")
    public void findByIdRepositoryTest() {

        MovieModel entityMock = new MovieModel();

        entityMock.setId(1L);
        entityMock.setImdbRate(10l);
        entityMock.setDuration(178l);
        entityMock.setDirector("Peter Jackson");
        entityMock.setReleaseDate(LocalDate.now());
        entityMock.setTitle("Lord of the rings - The Fellowship of the ring");
        entityMock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        when(entityManager.find(MovieModel.class, 1L)).thenReturn(entityMock);

        Optional<MovieModel> entity = resource.findById(1L);

        assertNotNull(entity);
        assertEquals(Optional.of(entityMock), entity);
    }

}
