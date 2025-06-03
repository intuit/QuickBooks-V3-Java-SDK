package com.intuit.payment.data;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author crystal-chung
 */
public class QueryResponseTest {

    private List<BankAccount> bankAccounts;
    private List<Card> cards;
    private QueryResponse queryResponse;

    @BeforeTest
    public void init() {
        bankAccounts = new ArrayList<>();
        cards = new ArrayList<>();
    }

    @BeforeMethod
    public void setUp() {
        queryResponse = new QueryResponse.Builder()
                .bankAccounts(bankAccounts)
                .cards(cards)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(queryResponse.getBankAccounts(), bankAccounts);
        Assert.assertEquals(queryResponse.getCards(), cards);
    }

    @Test
    public void testAllSetters() {
        List<BankAccount> newBankAccounts = new ArrayList<>();
        List<Card> newCards = new ArrayList<>();

        BankAccount newBankAccount = new BankAccount();
        Card newCard = new Card.Builder().expYear("2020").expMonth("02").build();

        newBankAccounts.add(newBankAccount);
        newCards.add(newCard);

        queryResponse.setBankAccounts(newBankAccounts);
        queryResponse.setCards(newCards);

        Assert.assertEquals(queryResponse.getBankAccounts(), newBankAccounts);
        Assert.assertEquals(queryResponse.getCards(), newCards);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(queryResponse);
        String actualResult = queryResponse.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
