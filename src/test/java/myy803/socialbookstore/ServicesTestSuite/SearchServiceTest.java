package myy803.socialbookstore.ServicesTestSuite;

import myy803.socialbookstore.datamodel.searchstrategies.SearchFactory;
import myy803.socialbookstore.datamodel.searchstrategies.SearchStrategy;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.mappers.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import myy803.socialbookstore.services.SearchService;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private SearchFactory searchFactory;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private SearchStrategy searchStrategy;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveSearches_ValidSearch() {
        SearchDto searchDto = new SearchDto();
        searchDto.setTitle("Some Book Title");
        searchDto.setSelectedStrategy("Title");

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Some Book Title");

        when(searchFactory.create("Title")).thenReturn(searchStrategy);

        List<BookDto> bookDtoList = new ArrayList<>();
        bookDtoList.add(bookDto);
        when(searchStrategy.search(searchDto, bookMapper)).thenReturn((ArrayList<BookDto>) bookDtoList);

        ArrayList<BookDto> result = searchService.retrieveSearches(searchDto);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Some Book Title", result.get(0).getTitle());

        verify(searchFactory, times(1)).create("Title");
        verify(searchStrategy, times(1)).search(searchDto, bookMapper);
    }

    @Test
    void testRetrieveSearches_EmptyTitle() {
        SearchDto searchDto = new SearchDto();
        searchDto.setTitle("");
        searchDto.setSelectedStrategy("Title");

        ArrayList<BookDto> result = searchService.retrieveSearches(searchDto);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testRetrieveSearches_InvalidStrategy() {
        SearchDto searchDto = new SearchDto();
        searchDto.setTitle("");
        searchDto.setSelectedStrategy("InvalidStrategy");

        ArrayList<BookDto> result = searchService.retrieveSearches(searchDto);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
