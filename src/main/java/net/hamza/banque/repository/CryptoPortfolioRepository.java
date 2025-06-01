package net.hamza.banque.repository;

import net.hamza.banque.model.CryptoPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CryptoPortfolioRepository extends JpaRepository<CryptoPortfolio, Long> {
    List<CryptoPortfolio> findByUserId(Long userId);
    Optional<CryptoPortfolio> findByUserIdAndCryptoCurrencySymbol(Long userId, String symbol);
    List<CryptoPortfolio> findByUserIdOrderByCurrentValueDesc(Long userId);
    boolean existsByUserIdAndCryptoCurrencySymbol(Long userId, String symbol);
} 