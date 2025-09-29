package com.gleb.Finance.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletType type = WalletType.CREDIT_CARD;

    @Column(name = "include_in_available_balance", nullable = false)
    private boolean includeInAvailableBalance = true;

    @Column(name = "include_in_net_worth", nullable = false)
    private boolean includeInNetWorth = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomes = new ArrayList<>();

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletBalanceHistory> walletBalanceHistories = new ArrayList<>();

    public Wallet() {
        this.createdAt = LocalDateTime.now();
        setSmartDefaults();
    }

    public Wallet(User user, String name, BigDecimal balance, WalletType type) {
        this();
        this.user = user;
        this.name = name;
        this.balance = balance;
        this.type = type;
        setSmartDefaults();
    }

    private void setSmartDefaults() {
        switch (this.type) {
            case CASH, BANK_ACCOUNT, CREDIT_CARD -> {
                this.includeInAvailableBalance = true;
                this.includeInNetWorth = true;
            }
            case INVESTMENT, DEBT, SAVINGS -> {
                this.includeInAvailableBalance = false;
                this.includeInNetWorth = true;
            }
            case LOAN -> {
                this.includeInAvailableBalance = false;
                this.includeInNetWorth = true;
                if (this.balance.compareTo(BigDecimal.ZERO) > 0) {
                    this.balance = this.balance.negate();
                }
            }
            case OTHER -> {
                this.includeInAvailableBalance = false;
                this.includeInNetWorth = false;
            }
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public WalletType getType() { return type; }
    public void setType(WalletType type) {
        this.type = type;
        setSmartDefaults();
    }

    public boolean isIncludeInAvailableBalance() { return includeInAvailableBalance; }
    public void setIncludeInAvailableBalance(boolean includeInAvailableBalance) {
        this.includeInAvailableBalance = includeInAvailableBalance;
    }

    public boolean isIncludeInNetWorth() { return includeInNetWorth; }
    public void setIncludeInNetWorth(boolean includeInNetWorth) {
        this.includeInNetWorth = includeInNetWorth;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<Income> getIncomes() { return incomes; }
    public void setIncomes(List<Income> incomes) { this.incomes = incomes; }

    public List<Expense> getExpenses() { return expenses; }
    public void setExpenses(List<Expense> expenses) { this.expenses = expenses; }

    public List<WalletBalanceHistory> getWalletBalanceHistories() {
        return walletBalanceHistories;
    }

    public void setWalletBalanceHistories(List<WalletBalanceHistory> walletBalanceHistories) {
        this.walletBalanceHistories = walletBalanceHistories;
    }
}