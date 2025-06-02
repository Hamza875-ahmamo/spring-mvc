package net.hamza.banque.dto;

import lombok.Builder;

@Builder
public class ResponseTransaction {
    private boolean status;
    private String message;
}
