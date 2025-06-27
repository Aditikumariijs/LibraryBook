package com.library.book.service;

import com.library.book.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO userDTO);
    List<UserDTO> findAll();
    UserDTO findById(Long id);
    UserDTO update(Long id, UserDTO userDTO);
    void delete(Long id);
}
