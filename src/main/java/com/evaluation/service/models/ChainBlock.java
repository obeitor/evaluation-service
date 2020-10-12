package com.evaluation.service.models;

import javax.persistence.*;

@Entity
@Table(name = "chain_block")
public class ChainBlock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currentHash;
    private String previousHash;

    public ChainBlock() {
    }

    public ChainBlock(String currentHash, String previousHash) {
        this.currentHash = currentHash;
        this.previousHash = previousHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentHash() {
        return currentHash;
    }

    public void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
}
