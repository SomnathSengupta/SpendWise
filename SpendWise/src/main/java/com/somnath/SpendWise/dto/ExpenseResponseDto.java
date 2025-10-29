package com.somnath.SpendWise.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDto {

    private Long id;
    private String purpose;
    private BigDecimal amount;
    private LocalDateTime date;
}
