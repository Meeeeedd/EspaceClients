package com.example.espaceclient.service;

import com.example.espaceclient.dao.DonsDAO;
import com.example.espaceclient.dao.DonsDAOImpl;
import com.example.espaceclient.model.Dons;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DonsService {
    private final DonsDAO donsDAO;

    public DonsService() {
        this.donsDAO = new DonsDAOImpl();
    }

    public List<Dons> getAllDons() {
        return donsDAO.findAll();
    }

    public void createDon(Dons don) {
        donsDAO.save(don);
    }

    public List<Dons> getDonsByClientId(int clientId) {
        return donsDAO.findByClientId(clientId);
    }

    public Payment createPayPalPayment(BigDecimal amount, APIContext apiContext) throws PayPalRESTException, PayPalRESTException {
        Amount paypalAmount = new Amount();
        paypalAmount.setCurrency("USD");
        paypalAmount.setTotal(amount.toString());

        Transaction transaction = new Transaction();
        transaction.setAmount(paypalAmount);
        transaction.setDescription("Donation");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/cancel");
        redirectUrls.setReturnUrl("http://localhost:8080/return");
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public void executePayPalPayment(String paymentId, String payerId, APIContext apiContext) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        payment.execute(apiContext, paymentExecution);
    }
}