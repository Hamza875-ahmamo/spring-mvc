package net.hamza.banque.service;

import net.hamza.banque.model.CryptoCurrency;
import net.hamza.banque.model.CryptoTransaction;
import net.hamza.banque.model.CryptoPortfolio;

import java.math.BigDecimal;
import java.util.List;

public interface CryptoService {
    // Opérations sur les crypto-monnaies
    List<CryptoCurrency> getAllCryptocurrencies();
    CryptoCurrency getCryptoById(Long id);
    CryptoCurrency getCryptoBySymbol(String symbol);
    
    // Opérations de trading
    CryptoTransaction buyCrypto(Long userId, String cryptoSymbol, BigDecimal amount);
    CryptoTransaction sellCrypto(Long userId, String cryptoSymbol, BigDecimal amount);
    
    // Gestion du portfolio
    CryptoPortfolio getUserPortfolio(Long userId);
    BigDecimal getPortfolioValue(Long userId);
    BigDecimal getCryptoBalance(Long userId, String cryptoSymbol);
    
    // Prix et statistiques
    BigDecimal getCurrentPrice(String cryptoSymbol);
    BigDecimal get24hHigh(String cryptoSymbol);
    BigDecimal get24hLow(String cryptoSymbol);
    BigDecimal getMarketCap(String cryptoSymbol);
    BigDecimal get24hVolume(String cryptoSymbol);
    
    // Historique des transactions
    List<CryptoTransaction> getUserTransactions(Long userId);
    List<CryptoTransaction> getCryptoTransactions(Long userId, String cryptoSymbol);
} 