package myy803.socialbookstore.ServicesTestSuite;
import myy803.socialbookstore.services.*;

import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsFactory;
import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsStrategy;
import myy803.socialbookstore.formsdata.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecommendationsServiceTest {

    @Mock
    private RecommendationsFactory recommendationsFactory;

    @Mock
    private RecommendationsStrategy recommendationsStrategy;

    @InjectMocks
    private RecommendationsService recommendationsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveRecommendations_SuccessfulRetrieval() {
        String username = "testUser";
        String recomDto = "popularBooks";

        List<BookDto> expectedRecommendations = Arrays.asList(
                new BookDto(1, "Book 1", "Author 1","category 1",null),
                new BookDto(2, "Book 2", "Author 2","category2",null)
        );

        when(recommendationsFactory.create(recomDto))
                .thenReturn(recommendationsStrategy);

        when(recommendationsStrategy.recommend(username))
                .thenReturn(expectedRecommendations);

        List<BookDto> actualRecommendations =
                recommendationsService.retrieveRecomendations(username, recomDto);

        assertNotNull(actualRecommendations);
        assertEquals(expectedRecommendations.size(), actualRecommendations.size());
        assertEquals(expectedRecommendations, actualRecommendations);

        verify(recommendationsFactory).create(recomDto);
        verify(recommendationsStrategy).recommend(username);
    }

    @Test
    void testRetrieveRecommendations_EmptyRecommendations() {
        String username = "testUser";
        String recomDto = "unpopularBooks";

        List<BookDto> emptyRecommendations = Arrays.asList();

        when(recommendationsFactory.create(recomDto))
                .thenReturn(recommendationsStrategy);

        when(recommendationsStrategy.recommend(username))
                .thenReturn(emptyRecommendations);

        List<BookDto> actualRecommendations =
                recommendationsService.retrieveRecomendations(username, recomDto);

        assertNotNull(actualRecommendations);
        assertTrue(actualRecommendations.isEmpty());

        verify(recommendationsFactory).create(recomDto);
        verify(recommendationsStrategy).recommend(username);
    }

    @Test
    void testRetrieveRecommendations_DifferentStrategies() {
        String username = "testUser";

        String[] strategies = {
                "Favourite Categories",
                "Favourite Authors",
                "other"
        };
        for (String recomDto : strategies) {
            RecommendationsStrategy mockStrategy = mock(RecommendationsStrategy.class);
            List<BookDto> expectedRecommendations = Arrays.asList(
                    new BookDto(1, "Book 1", "Author 1","category 1",null),
                    new BookDto(2, "Book 2", "Author 2","category2",null)
            );

            when(recommendationsFactory.create(recomDto))
                    .thenReturn(mockStrategy);
            when(mockStrategy.recommend(username))
                    .thenReturn(expectedRecommendations);

            List<BookDto> actualRecommendations =
                    recommendationsService.retrieveRecomendations(username, recomDto);

            assertNotNull(actualRecommendations);
            assertEquals(expectedRecommendations.size(), actualRecommendations.size());
            assertEquals(expectedRecommendations, actualRecommendations);

            verify(recommendationsFactory).create(recomDto);
            verify(mockStrategy).recommend(username);

            reset(mockStrategy);
        }
    }

    @Test
    void testRetrieveRecommendations_NullInputHandling() {
        String username = null;
        String recomDto = null;

        when(recommendationsFactory.create(null))
                .thenReturn(recommendationsStrategy);
        when(recommendationsStrategy.recommend(null))
                .thenReturn(Arrays.asList());

        assertDoesNotThrow(() -> {
            List<BookDto> recommendations =
                    recommendationsService.retrieveRecomendations(username, recomDto);
            assertTrue(recommendations.isEmpty());
        });

        verify(recommendationsFactory).create(null);
        verify(recommendationsStrategy).recommend(null);
    }
}