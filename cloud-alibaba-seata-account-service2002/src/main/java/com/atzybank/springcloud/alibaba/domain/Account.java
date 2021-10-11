package com.atzybank.springcloud.alibaba.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_account")
public class Account {
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "used")
    private BigDecimal used;
    @Column(name = "residue")
    private BigDecimal residue;


}
