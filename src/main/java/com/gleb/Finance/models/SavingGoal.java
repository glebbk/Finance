package com.gleb.Finance.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "saving_goals")
public class SavingGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "target_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal targetAmount;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(nullable = false)
    private boolean completed = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", nullable = false, length = 20)
    private GoalType goalType = GoalType.SAVINGS;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SavingGoal() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Конструктор для целей накоплений (старый функционал)
    public SavingGoal(User user, String name, BigDecimal targetAmount, LocalDate deadline) {
        this();
        this.user = user;
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.goalType = GoalType.SAVINGS;
    }

    // Конструктор для целей по доходам
    public SavingGoal(User user, String name, BigDecimal targetAmount, LocalDate deadline
                        ,GoalType goalType) {
        this();
        this.user = user;
        this.name = name;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.goalType = goalType;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getTargetAmount() { return targetAmount; }
    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.updatedAt = LocalDateTime.now();
    }

    public GoalType getGoalType() { return goalType; }
    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}