package com.gleb.Finance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "wallet_id")
    private int walletId;

    @Column(name = "category_id")
    private int categoryId;


}
