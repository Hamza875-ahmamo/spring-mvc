package net.hamza.banque.repository;

import net.hamza.banque.model.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    Optional<CryptoCurrency> findBySymbol(String symbol);
    Optional<CryptoCurrency> findByName(String name);
    boolean existsBySymbol(String symbol);
} 