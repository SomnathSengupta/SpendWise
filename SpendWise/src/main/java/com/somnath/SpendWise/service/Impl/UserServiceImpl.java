package com.somnath.SpendWise.service.Impl;

import com.somnath.SpendWise.dto.LoginRequestDto;
import com.somnath.SpendWise.dto.UserRequestDto;
import com.somnath.SpendWise.dto.UserResponseDto;
import com.somnath.SpendWise.entity.User;
import com.somnath.SpendWise.repository.UserRepository;
import com.somnath.SpendWise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    // private final PasswordEncoder passwordEncoder; // <-- Inject this for production

    @Override
    public UserResponseDto signup(UserRequestDto userRequestDto) {
        // Check if email already exists to provide a specific error
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        User user = modelMapper.map(userRequestDto, User.class);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (loginRequestDto.getPassword().equals(user.getPassword())) {
            return modelMapper.map(user, UserResponseDto.class);
        } else {
            throw new IllegalArgumentException("Invalid email or password.");
        }
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateMonthlyInHand(Long userId, BigDecimal newMonthlyInHand) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        user.setMonthlyInHand(newMonthlyInHand);
        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponseDto.class);
    }
}