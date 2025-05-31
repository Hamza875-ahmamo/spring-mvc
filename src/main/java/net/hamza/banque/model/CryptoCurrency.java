package net.hamza.banque.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "crypto_currencies")
public class CryptoCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol; // BTC, ETH, etc.

    @Column(nullable = false)
    private String name; // Bitcoin, Ethereum, etc.

    @Column(nullable = false)
    private BigDecimal currentPrice;

    @Column(name = "price_change_24h")
    private BigDecimal priceChange24h;

    @Column(name = "price_change_percentage_24h")
    private BigDecimal priceChangePercentage24h;

    @Column(name = "market_cap")
    private BigDecimal marketCap;

    @Column(name = "volume_24h")
    private BigDecimal volume24h;

    @Column(name = "high_24h")
    private BigDecimal high24h;

    @Column(name = "low_24h")
    private BigDecimal low24h;

    @Column(name = "circulating_supply")
    private BigDecimal circulatingSupply;

    @Column(name = "total_supply")
    private BigDecimal totalSupply;

    @Column(name = "max_supply")
    private BigDecimal maxSupply;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        isActive = true;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
} 