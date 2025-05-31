package net.hamza.banque.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private String recipientType; // MY_ACCOUNT, OTHER_ACCOUNT, EXTERNAL
    private String recipientName;
    private String recipientAccountNumber;
    private String bankName;
    private String routingNumber;
    private BigDecimal amount;
    private LocalDateTime scheduledDate;
    private String note;
    private boolean isScheduled;
} 