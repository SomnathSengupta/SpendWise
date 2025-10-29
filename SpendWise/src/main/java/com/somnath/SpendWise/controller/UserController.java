package com.somnath.SpendWise.controller;

import com.somnath.SpendWise.dto.LoginRequestDto;
import com.somnath.SpendWise.dto.UserRequestDto;
import com.somnath.SpendWise.dto.UserResponseDto;
import com.somnath.SpendWise.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /*
    * Signup API
    * */
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto createdUser = userService.signup(userRequestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /*
     * Login API
     * */
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        UserResponseDto loggedInUser = userService.login(loginRequestDto);
        return ResponseEntity.ok(loggedInUser);
    }

    /*
     * Get User By ID
     * */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /*
     * Update Monthly In-hand Salary
     * */
    @PutMapping("/{id}/update-salary")
    public ResponseEntity<UserResponseDto> updateMonthlyInHand(
            @PathVariable Long id,
            @RequestParam BigDecimal newMonthlyInHand) {

        UserResponseDto updatedUser = userService.updateMonthlyInHand(id, newMonthlyInHand);
        return ResponseEntity.ok(updatedUser);
    }
}
