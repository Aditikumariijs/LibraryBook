package Service;

import com.library.book.Impl.BorrowingRecordServiceImpl;
import com.library.book.dto.BorrowingRecordDTO;
import com.library.book.entity.Book;
import com.library.book.entity.BorrowingRecord;
import com.library.book.entity.User;
import com.library.book.repository.BookRepository;
import com.library.book.repository.BorrowingRecordRepository;
import com.library.book.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BorrowingRecordServiceImpl borrowingRecordService;

    private Book book;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        book = Book.builder()
                .id(1L)
                .title("Sample Book")
                .author("Author A")
                .isbn("1234567890")
                .build();

        user = User.builder()
                .id(1L)
                .name("Aditi")
                .email("aditi@example.com")
                .build();
    }

    @Test
    void save_ValidDTO_ReturnsSavedDTO() {
        BorrowingRecordDTO dto = BorrowingRecordDTO.builder()
                .bookId(1L)
                .userId(1L)
                .borrowDate(LocalDate.of(2024, 1, 1))
                .returnDate(LocalDate.of(2024, 2, 1))
                .build();

        BorrowingRecord record = BorrowingRecord.builder()
                .id(1L)
                .book(book)
                .user(user)
                .borrowDate(dto.getBorrowDate())
                .returnDate(dto.getReturnDate())
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(record);

        BorrowingRecordDTO result = borrowingRecordService.save(dto);

        assertNotNull(result);
        assertEquals(1L, result.getBookId());
        assertEquals(1L, result.getUserId());
    }

    @Test
    void findById_RecordExists_ReturnsDTO() {
        BorrowingRecord record = BorrowingRecord.builder()
                .id(1L)
                .book(book)
                .user(user)
                .borrowDate(LocalDate.now())
                .returnDate(LocalDate.now().plusDays(7))
                .build();

        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.of(record));

        BorrowingRecordDTO result = borrowingRecordService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBookId());
        assertEquals(1L, result.getUserId());
    }

    @Test
    void findAll_RecordsExist_ReturnsListOfDTOs() {
        List<BorrowingRecord> records = Arrays.asList(
                BorrowingRecord.builder().id(1L).book(book).user(user).borrowDate(LocalDate.now()).returnDate(LocalDate.now().plusDays(7)).build(),
                BorrowingRecord.builder().id(2L).book(book).user(user).borrowDate(LocalDate.now()).returnDate(LocalDate.now().plusDays(10)).build()
        );

        when(borrowingRecordRepository.findAll()).thenReturn(records);

        List<BorrowingRecordDTO> result = borrowingRecordService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void update_RecordExists_ReturnsUpdatedDTO() {
        BorrowingRecord existing = BorrowingRecord.builder()
                .id(1L)
                .book(book)
                .user(user)
                .borrowDate(LocalDate.of(2024, 1, 1))
                .returnDate(LocalDate.of(2024, 1, 10))
                .build();

        BorrowingRecordDTO updateDTO = BorrowingRecordDTO.builder()
                .bookId(1L)
                .userId(1L)
                .borrowDate(LocalDate.of(2024, 1, 5))
                .returnDate(LocalDate.of(2024, 1, 15))
                .build();

        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(existing);

        BorrowingRecordDTO result = borrowingRecordService.update(1L, updateDTO);

        assertEquals(LocalDate.of(2024, 1, 5), result.getBorrowDate());
        assertEquals(LocalDate.of(2024, 1, 15), result.getReturnDate());
    }

    @Test
    void delete_ValidId_DeletesRecord() {
        borrowingRecordService.delete(1L);
        verify(borrowingRecordRepository, times(1)).deleteById(1L);
    }
}

