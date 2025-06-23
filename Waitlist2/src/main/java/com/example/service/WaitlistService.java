package com.example.service;

import com.example.Party;
import com.example.repository.WaitlistRepository;

import java.util.ArrayList

public class WaitlistService {
    WaitlistRepository repository;
    public WaitlistService(WaitlistRepository repository) {
        WaitlistRepository repository;
    }

    public ArrayList<> getList() {
        return this.repository = repository;

    }

    public void addParty(Party party) {
        this.repository.add(party);
    }

    public void callNext() {
        return this.repository.callNext();
    }

    public Party remove(int index) {
        return this.repository.remove(index);
