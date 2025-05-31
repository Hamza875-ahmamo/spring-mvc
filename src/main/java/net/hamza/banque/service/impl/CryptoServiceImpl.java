package net.hamza.banque.service.impl;

import lombok.RequiredArgsConstructor;
import net.hamza.banque.model.CryptoCurrency;
import net.hamza.banque.model.CryptoPortfolio;
import net.hamza.banque.model.CryptoTransaction;
import net.hamza.banque.model.User;
import net.hamza.banque.repository.CryptoCurrencyRepository;
import net.hamza.banque.repository.CryptoPortfolioRepository;
import net.hamza.banque.repository.CryptoTransactionRepository;
import net.hamza.banque.repository.UserRepository;
import net.hamza.banque.service.CryptoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {

    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoTransactionRepository cryptoTransactionRepository;
    private final CryptoPortfolioRepository cryptoPortfolioRepository;
    private final UserRepository userRepository;

    @Override
    public List<CryptoCurrency> getAllCryptocurrencies() {
        return cryptoCurrencyRepository.findAll();
    }

    @Override
    public CryptoCurrency getCryptoById(Long id) {
        return cryptoCurrencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crypto not found"));
    }

    @Override
    public CryptoCurrency getCryptoBySymbol(String symbol) {
        return cryptoCurrencyRepository.findBySymbol(symbol)
                .orElseThrow(() -> new RuntimeException("Crypto not found"));
    }

    @Override
    @Transactional
    public CryptoTransaction buyCrypto(Long userId, String cryptoSymbol, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        CryptoCurrency crypto = getCryptoBySymbol(cryptoSymbol);
        BigDecimal currentPrice = crypto.getCurrentPrice();
        BigDecimal cryptoAmount = amount.divide(currentPrice, 8, RoundingMode.HALF_UP);

        CryptoTransaction transaction = CryptoTransaction.builder()
                .user(user)
                .cryptoCurrency(crypto)
                .type("BUY")
                .amount(amount)
                .cryptoAmount(cryptoAmount)
                .priceAtTransaction(currentPrice)
                .status("COMPLETED")
                .transactionDate(LocalDateTime.now())
                .build();

        transaction = cryptoTransactionRepository.save(transaction);

        // Mettre à jour le portfolio
        updatePortfolio(user, crypto, cryptoAmount, currentPrice, true);

        return transaction;
    }

    @Override
    @Transactional
    public CryptoTransaction sellCrypto(Long userId, String cryptoSymbol, BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        CryptoCurrency crypto = getCryptoBySymbol(cryptoSymbol);
        BigDecimal currentPrice = crypto.getCurrentPrice();
        BigDecimal cryptoAmount = amount.divide(currentPrice, 8, RoundingMode.HALF_UP);

        // Vérifier si l'utilisateur a assez de crypto
        CryptoPortfolio portfolio = cryptoPortfolioRepository.findByUserIdAndCryptoCurrencySymbol(userId, cryptoSymbol)
                .orElseThrow(() -> new RuntimeException("No crypto balance found"));
        
        if (portfolio.getBalance().compareTo(cryptoAmount) < 0) {
            throw new RuntimeException("Insufficient crypto balance");
        }

        CryptoTransaction transaction = CryptoTransaction.builder()
                .user(user)
                .cryptoCurrency(crypto)
                .type("SELL")
                .amount(amount)
                .cryptoAmount(cryptoAmount)
                .priceAtTransaction(currentPrice)
                .status("COMPLETED")
                .transactionDate(LocalDateTime.now())
                .build();

        transaction = cryptoTransactionRepository.save(transaction);

        // Mettre à jour le portfolio
        updatePortfolio(user, crypto, cryptoAmount, currentPrice, false);

        return transaction;
    }

    @Override
    public CryptoPortfolio getUserPortfolio(Long userId) {
        List<CryptoPortfolio> portfolios = cryptoPortfolioRepository.findByUserId(userId);
        if (portfolios.isEmpty()) {
            return null;
        }
        return portfolios.get(0);
    }

    @Override
    public BigDecimal getPortfolioValue(Long userId) {
        List<CryptoPortfolio> portfolios = cryptoPortfolioRepository.findByUserId(userId);
        return portfolios.stream()
                .map(CryptoPortfolio::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getCryptoBalance(Long userId, String cryptoSymbol) {
        return cryptoPortfolioRepository.findByUserIdAndCryptoCurrencySymbol(userId, cryptoSymbol)
                .map(CryptoPortfolio::getBalance)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getCurrentPrice(String cryptoSymbol) {
        return getCryptoBySymbol(cryptoSymbol).getCurrentPrice();
    }

    @Override
    public BigDecimal get24hHigh(String cryptoSymbol) {
        return getCryptoBySymbol(cryptoSymbol).getHigh24h();
    }

    @Override
    public BigDecimal get24hLow(String cryptoSymbol) {
        return getCryptoBySymbol(cryptoSymbol).getLow24h();
    }

    @Override
    public BigDecimal getMarketCap(String cryptoSymbol) {
        return getCryptoBySymbol(cryptoSymbol).getMarketCap();
    }

    @Override
    public BigDecimal get24hVolume(String cryptoSymbol) {
        return getCryptoBySymbol(cryptoSymbol).getVolume24h();
    }

    @Override
    public List<CryptoTransaction> getUserTransactions(Long userId) {
        return cryptoTransactionRepository.findByUserId(userId);
    }

    @Override
    public List<CryptoTransaction> getCryptoTransactions(Long userId, String cryptoSymbol) {
        return cryptoTransactionRepository.findByUserIdAndCryptoCurrencySymbol(userId, cryptoSymbol);
    }

    private void updatePortfolio(User user, CryptoCurrency crypto, BigDecimal amount, BigDecimal price, boolean isBuy) {
        CryptoPortfolio portfolio = cryptoPortfolioRepository.findByUserIdAndCryptoCurrencySymbol(user.getId(), crypto.getSymbol())
                .orElse(CryptoPortfolio.builder()
                        .user(user)
                        .cryptoCurrency(crypto)
                        .balance(BigDecimal.ZERO)
                        .averageBuyPrice(BigDecimal.ZERO)
                        .totalInvested(BigDecimal.ZERO)
                        .currentValue(BigDecimal.ZERO)
                        .profitLoss(BigDecimal.ZERO)
                        .profitLossPercentage(BigDecimal.ZERO)
                        .build());

        if (isBuy) {
            // Mise à jour pour un achat
            BigDecimal newBalance = portfolio.getBalance().add(amount);
            BigDecimal newTotalInvested = portfolio.getTotalInvested().add(amount.multiply(price));
            BigDecimal newAveragePrice = newTotalInvested.divide(newBalance, 8, RoundingMode.HALF_UP);
            
            portfolio.setBalance(newBalance);
            portfolio.setAverageBuyPrice(newAveragePrice);
            portfolio.setTotalInvested(newTotalInvested);
        } else {
            // Mise à jour pour une vente
            BigDecimal newBalance = portfolio.getBalance().subtract(amount);
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("Insufficient balance");
            }
            portfolio.setBalance(newBalance);
        }

        // Mise à jour des valeurs actuelles
        BigDecimal currentValue = portfolio.getBalance().multiply(price);
        portfolio.setCurrentValue(currentValue);
        
        // Calcul du profit/perte
        BigDecimal profitLoss = currentValue.subtract(portfolio.getTotalInvested());
        portfolio.setProfitLoss(profitLoss);
        
        if (portfolio.getTotalInvested().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal profitLossPercentage = profitLoss.divide(portfolio.getTotalInvested(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            portfolio.setProfitLossPercentage(profitLossPercentage);
        }

        cryptoPortfolioRepository.save(portfolio);
    }
} 