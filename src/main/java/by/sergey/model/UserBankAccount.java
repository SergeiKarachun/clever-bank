package by.sergey.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserBankAccount {
    private String username;
    private Long bankAccountNumber;
    private BigDecimal balance;
    private Currency currencyId;
    private Long bankId;
    private String createdAt;

}
