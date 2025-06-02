package net.hamza.banque.dto;

import lombok.Builder;

@Builder
public class ResponseTransaction {
    private boolean statue;
    private String message;
}
