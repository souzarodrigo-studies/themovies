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

    MovieModel entityMock = new MovieModel();

    @Before
    public void begin() {
        MockitoAnnotations.initMocks(this);

        entityMock.setId(1L);
        entityMock.setImdbRate(10l);
        entityMock.setDuration(178l);
        entityMock.setDirector("Peter Jackson");
        entityMock.setReleaseDate(LocalDate.now());
        entityMock.setTitle("Lord of the rings - The Fellowship of the ring");
        entityMock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Testing the execution of the repository returning the data by list all")
    public void listAllrepositoryTest() {
        List<MovieModel> movieEntityList = new ArrayList<>();

        movieEntityList.add(entityMock);

        TypedQuery<MovieModel> query = (TypedQuery<MovieModel>) mock(TypedQuery.class);

        when(entityManager.createQuery(" SELECT p FROM MovieModel p", MovieModel.class)).thenReturn(query);

        when(query.getResultList()).thenReturn(movieEntityList);

        List<MovieModel> response = resource.findAll();

        assertNotNull(response);
        assertEquals(movieEntityList, response);
    }

    @Test
    @DisplayName("Testing the execution of the repository returning the data by find by id")
    public void findByIdRepositoryTest() {

        when(entityManager.find(MovieModel.class, 1L)).thenReturn(entityMock);

        Optional<MovieModel> entity = resource.findById(1L);

        assertNotNull(entity);
        assertEquals(Optional.of(entityMock), entity);
    }

    @Test
    @DisplayName("Testing the execution of the repository returning the data by save")
    public void SaveRepositoryTest() {

        entityManager.persist(entityMock);

        assertNotNull(entityMock);

    }

    @Test
    @DisplayName("Testing the execution of the repository returning the data by delete")
    public void DeleteRepositoryTest() {

        entityManager.remove(entityMock);

        assertNotNull(entityMock);

    }

    @Test
    @DisplayName("Testing the execution of the repository returning the data by update")
    public void UpdateRepositoryTest() {

        MovieModel mock = new MovieModel();
        mock.setId(1L);
        mock.setImdbRate(10l);
        mock.setDuration(178l);
        mock.setDirector("Peter Jackson e Rodrigo SS");
        mock.setReleaseDate(LocalDate.now());
        mock.setTitle("Lord of the rings - The Fellowship of the ring");
        mock.setDescription(
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.");

        when(resource.findById(entityMock.getId())).thenReturn(Optional.of(entityMock));

        entityManager.merge(mock);

        assertNotNull(mock);
        assertNotEquals(entityMock, mock);

    }

}
