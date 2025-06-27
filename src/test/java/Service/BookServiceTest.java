package Service;

import com.library.book.Impl.BookServiceImpl;
import com.library.book.exception.BookNotFoundException;
import com.library.book.repository.BookRepository;
import com.library.book.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testFindByIdThrowsException() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findById(bookId);
        });

        assertEquals("Book not found with id: 1", exception.getMessage());
        verify(bookRepository, times(1)).findById(bookId);
    }
}