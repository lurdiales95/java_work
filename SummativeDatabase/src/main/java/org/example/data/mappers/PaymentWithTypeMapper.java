package org.example.data.mappers;

import org.example.model.Payment;
import org.example.model.PaymentType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaymentWithTypeMapper implements RowMapper<Payment> {

    @Override
    public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Create and populate the Payment
        Payment payment = new Payment();
        payment.setPaymentID(rs.getInt("PaymentID"));
        payment.setPaymentTypeID(rs.getInt("PaymentTypeID"));
        payment.setOrderID(rs.getInt("OrderID"));
        payment.setAmount(rs.getBigDecimal("Amount"));

        // Create and populate the PaymentType from the joined data
        PaymentType paymentType = new PaymentType();
        paymentType.setPaymentTypeID(rs.getInt("PaymentTypeID"));
        paymentType.setPaymentTypeName(rs.getString("PaymentTypeName"));

        // Set the complete PaymentType object on the Payment
        payment.setPaymentType(paymentType);

        return payment;
    }
}