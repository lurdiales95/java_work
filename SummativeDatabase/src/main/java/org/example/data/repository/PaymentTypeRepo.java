package org.example.data.repository;

import org.example.data.exceptions.InternalErrorException;
import org.example.model.PaymentType;

import java.util.List;

public interface PaymentTypeRepo {
    List<PaymentType> getAll() throws InternalErrorException;
}
