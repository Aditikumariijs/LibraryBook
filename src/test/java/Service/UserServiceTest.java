package Service;


import com.library.book.Impl.UserServiceImpl;
import com.library.book.dto.UserDTO;
import com.library.book.entity.User;
import com.library.book.exception.UserNotFoundException;
import com.library.book.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUser_shouldReturnSavedUserDTO() {
        UserDTO inputDTO = UserDTO.builder().name("Aditi").email("aditi@example.com").build();
        User savedUser = User.builder().id(1L).name("Aditi").email("aditi@example.com").build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.save(inputDTO);

        assertNotNull(result);
        assertEquals("Aditi", result.getName());
        assertEquals("aditi@example.com", result.getEmail());
    }

    @Test
    void findById_shouldReturnUserDTO_whenUserExists() {
        User user = User.builder().id(1L).name("Aditi").email("aditi@example.com").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO result = userService.findById(1L);

        assertNotNull(result);
        assertEquals("Aditi", result.getName());
        assertEquals("aditi@example.com", result.getEmail());
    }

    @Test
    void findById_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userService.findById(1L));
        assertEquals("User not found with id: 1", ex.getMessage());
    }

    @Test
    void findAll_shouldReturnListOfUserDTOs() {
        List<User> users = Arrays.asList(
                User.builder().id(1L).name("Aditi").email("a@example.com").build(),
                User.builder().id(2L).name("Kiran").email("k@example.com").build()
        );

        when(userRepository.findAll()).thenReturn(users);

        List<UserDTO> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals("Kiran", result.get(1).getName());
    }

    @Test
    void update_shouldReturnUpdatedUserDTO() {
        Long id = 1L;
        User existingUser = User.builder().id(id).name("Old Name").email("old@example.com").build();
        User updatedUser = User.builder().id(id).name("New Name").email("new@example.com").build();

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDTO updatedDTO = UserDTO.builder().name("New Name").email("new@example.com").build();
        UserDTO result = userService.update(id, updatedDTO);

        assertEquals("New Name", result.getName());
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
