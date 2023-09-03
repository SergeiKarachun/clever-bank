package by.sergey.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Transaction {
    private Long id;
    private LocalDateTime createdAt;
    private TransactionType transactionType;
    private Long fromBankId;
    private Long toBankId;
    private Long paymentSenderAccount;
    private Long paymentReceiverAccount;
    private BigDecimal amount;

    public Transaction(Long paymentReceiverAccount, BigDecimal amount,TransactionType transactionType) {
        this.paymentReceiverAccount = paymentReceiverAccount;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Transaction(Long paymentSenderAccount, Long paymentReceiverAccount, BigDecimal amount,TransactionType transactionType) {
        this.paymentSenderAccount = paymentSenderAccount;
        this.paymentReceiverAccount = paymentReceiverAccount;
        this.amount = amount;
        this.transactionType = transactionType;
    }

}