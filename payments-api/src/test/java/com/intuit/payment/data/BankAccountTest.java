package com.intuit.payment.data;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Date;
import com.intuit.payment.data.BankAccount.BankAccountInputTypeEnum;
import com.intuit.payment.data.BankAccount.AccountType;

/**
 * @author enzozafra
 */
public class BankAccountTest {
    private String id;
    private String name;
    private Date created;
    private Date updated;
    private BankAccountInputTypeEnum inputType;
    private String routingNumber;
    private String accountNumber;
    private AccountType accountType;
    private String phone;
    private Boolean defaultValue;
    private String country;
    private String bankCode;
    private String entityId;
    private String entityType;
    private String entityVersion;

    private BankAccount bankAccount;

    @BeforeTest
    public void init() {
        id = "id";
        name = "name";
        created = new Date(1220227200);
        updated = new Date(1220832000);
        inputType = BankAccountInputTypeEnum.KEYED;
        routingNumber = "12311";
        accountNumber = "123123123";
        accountType = AccountType.PERSONAL_CHECKING;
        phone = "5871231234";
        defaultValue = true;
        country = "country";
        bankCode = "1221";
        entityId = "entityId";
        entityType = "entityType";
        entityVersion = "entityVersion";
    }

    @BeforeMethod
    public void setUp() {
        bankAccount = new BankAccount.Builder()
                .id(id)
                .name(name)
                .created(created)
                .updated(updated)
                .inputType(inputType)
                .routingNumber(routingNumber)
                .accountNumber(accountNumber)
                .accountType(accountType)
                .phone(phone)
                .defaultValue(defaultValue)
                .country(country)
                .bankCode(bankCode)
                .entityId(entityId)
                .entityType(entityType)
                .entityVersion(entityVersion)
                .build();

    }

    @Test
    public void testAllGetters() {
        Assert.assertEquals(bankAccount.getId(), id);
        Assert.assertEquals(bankAccount.getName(), name);
        Assert.assertEquals(bankAccount.getCreated(), created);
        Assert.assertEquals(bankAccount.getUpdated(), updated);
        Assert.assertEquals(bankAccount.getInputType(), inputType);
        Assert.assertEquals(bankAccount.getRoutingNumber(), routingNumber);
        Assert.assertEquals(bankAccount.getAccountNumber(), accountNumber);
        Assert.assertEquals(bankAccount.getPhone(), phone);
        Assert.assertEquals(bankAccount.getDefaultValue(), defaultValue);
        Assert.assertEquals(bankAccount.getCountry(), country);
        Assert.assertEquals(bankAccount.getBankCode(), bankCode);
        Assert.assertEquals(bankAccount.getEntityId(), entityId);
        Assert.assertEquals(bankAccount.getEntityType(), entityType);
        Assert.assertEquals(bankAccount.getEntityVersion(), entityVersion);
    }

    @Test
    public void testAllSetters() {
        String newId = "new id";
        String newName = "new name";
        Date newCreated = new Date(1220832000);
        Date newUpdated = new Date(1220227200);
        // set to null because there is no second option
        BankAccountInputTypeEnum newInputType = null;
        String newRoutingNumber = "11321";
        String newAccountNumber = "45655623";
        AccountType newAccountType = AccountType.PERSONAL_SAVINGS;
        String newPhone = "1231231231";
        Boolean newDefaultValue = false;
        String newCountry = "new country";
        String newBankCode = "1234";
        String newEntityId = "new entityId";
        String newEntityType = "new entityType";
        String newEntityVersion = "new entityVersion";

        bankAccount.setId(newId);
        bankAccount.setName(newName);
        bankAccount.setCreated(newCreated);
        bankAccount.setUpdated(newUpdated);
        bankAccount.setInputType(newInputType);
        bankAccount.setRoutingNumber(newRoutingNumber);
        bankAccount.setAccountNumber(newAccountNumber);
        bankAccount.setAccountType(newAccountType);
        bankAccount.setPhone(newPhone);
        bankAccount.setDefaultValue(newDefaultValue);
        bankAccount.setCountry(newCountry);
        bankAccount.setBankCode(newBankCode);
        bankAccount.setEntityId(newEntityId);
        bankAccount.setEntityType(newEntityType);
        bankAccount.setEntityVersion(newEntityVersion);

        Assert.assertEquals(bankAccount.getId(), newId);
        Assert.assertEquals(bankAccount.getName(), newName);
        Assert.assertEquals(bankAccount.getCreated(), newCreated);
        Assert.assertEquals(bankAccount.getUpdated(), newUpdated);
        Assert.assertEquals(bankAccount.getInputType(), newInputType);
        Assert.assertEquals(bankAccount.getRoutingNumber(), newRoutingNumber);
        Assert.assertEquals(bankAccount.getAccountNumber(), newAccountNumber);
        Assert.assertEquals(bankAccount.getPhone(), newPhone);
        Assert.assertEquals(bankAccount.getDefaultValue(), newDefaultValue);
        Assert.assertEquals(bankAccount.getCountry(), newCountry);
        Assert.assertEquals(bankAccount.getBankCode(), newBankCode);
        Assert.assertEquals(bankAccount.getEntityId(), newEntityId);
        Assert.assertEquals(bankAccount.getEntityType(), newEntityType);
        Assert.assertEquals(bankAccount.getEntityVersion(), newEntityVersion);
    }

    @Test
    public void testToString() {
        // Since we cant mock ReflectionToStringBuilder without powermock, just check if it includes below
        String expectedResult = "[id=id,name=name,created=Wed Jan 14 19:57:07 MST 1970,updated=Wed Jan 14 20:07:12 MST 1970,inputType=KEYED,routingNumber=12311,accountNumber=123123123,accountType=PERSONAL_CHECKING,phone=5871231234,defaultValue=true,country=country,bankCode=1221,entityId=entityId,entityType=entityType,entityVersion=entityVersion,intuit_tid=<null>,requestId=<null>]";
        String actualResult = bankAccount.toString();
        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}