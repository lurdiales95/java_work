package com.example.repository;

import com.example.model.Party;

import java.util.List;


public interface WaitlistRepository {
    List<Party> load();
    void save();
    void add(Party party);
    void remove(int index);
    Party callNext();


}

