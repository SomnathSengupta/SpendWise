package com.somnath.SpendWise.service;

import com.somnath.SpendWise.dto.LoginRequestDto;
import com.somnath.SpendWise.dto.UserRequestDto;
import com.somnath.SpendWise.dto.UserResponseDto;
import java.math.BigDecimal;

public interface UserService {
    /**
     * Registers a new user in the system.
     * @param userRequestDto DTO containing new user details.
     * @return DTO of the created user.
     */
    UserResponseDto signup(UserRequestDto userRequestDto);

    /**
     * Authenticates a user based on their credentials.
     * @param loginRequestDto DTO containing login credentials.
     * @return DTO of the authenticated user if credentials are valid.
     */
    UserResponseDto login(LoginRequestDto loginRequestDto);

    UserResponseDto getUserById(Long userId);

    UserResponseDto updateMonthlyInHand(Long userId, BigDecimal newMonthlyInHand);
}
