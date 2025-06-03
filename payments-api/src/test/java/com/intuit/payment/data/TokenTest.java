package com.intuit.payment.data;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author crystal-chung
 */
public class TokenTest {
    private Card card;
    private BankAccount bankAccount;
    private String value;
    private Token token;

    @BeforeTest
    public void init() {
        card = new Card();
        bankAccount = new BankAccount();
        value = "token value";
    }

    @BeforeMethod
    public void setUp() {
        token = new Token.Builder()
                .card(card)
                .bankAccount(bankAccount)
                .value(value)
                .build();
    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(token.getCard(), card);
        Assert.assertEquals(token.getBankAccount(), bankAccount);
        Assert.assertEquals(token.getValue(), value);
    }

    @Test
    public void testAllSetters() {
        Card newCard = new Card.Builder().expYear("2020").expMonth("02").build();
        BankAccount newBankAccount = new BankAccount();
        String newValue = "new token value";

        token.setCard(newCard);
        token.setBankAccount(newBankAccount);
        token.setValue(newValue);

        Assert.assertEquals(token.getCard(), newCard);
        Assert.assertEquals(token.getBankAccount(), newBankAccount);
        Assert.assertEquals(token.getValue(), newValue);
    }

    @Test
    public void testToString() {
        String expectedResult = ReflectionToStringBuilder.toString(token);
        String actualResult = token.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
