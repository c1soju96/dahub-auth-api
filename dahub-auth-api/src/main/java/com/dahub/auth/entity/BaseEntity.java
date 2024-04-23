package com.dahub.auth.entity;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.MDC;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    private String deleteYn;
    @Column(updatable = false)
    private LocalDateTime insertDate;
    
    @LastModifiedDate
    private LocalDateTime updateDate;
    
    private String insertOperator;
    private String updateOperator;

    @PrePersist
    public void before() {
        LocalDateTime now = LocalDateTime.now();
        this.insertDate = now;
        this.updateDate = now;
        this.insertOperator = MDC.get("user") ==null ? "PARTNER" : MDC.get("user");
        this.updateOperator = MDC.get("user") ==null ? "PARTNER" : MDC.get("user");
        this.deleteYn = "N";
    }

    @PreUpdate
    public void always() {
        this.updateDate = LocalDateTime.now();
        this.updateOperator = MDC.get("user") == null ? "PARTNER" : MDC.get("user");
    }

}