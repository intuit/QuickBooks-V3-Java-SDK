package com.intuit.payment.data;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Date;

/**
 * @author stephendoyle1992
 * 
 */

public class CardTest {
	private String id;
	private String number;
	private String name;
	private Address address;
	private Date created;
	private Date updated;
	private String commercialCardCode;
	private CvcVerification cvcVerification;
	private String cardType;
	private String expMonth;
	private String expYear;
	private Boolean isBusiness;
	private Boolean isLevel3Eligible;
	private Boolean defaultValue;
	private String cvc;
	private String entityId;
	private String entityType;
	private String entityVersion;
	private CardPresent cardPresent;
	private String idNew;
	private String numberNew;
	private String nameNew;
	private Address addressNew;
	private Date createdNew;
	private Date updatedNew;
	private String commercialCardCodeNew;
	private CvcVerification cvcVerificationNew;
	private String cardTypeNew;
	private String expMonthNew;
	private String expYearNew;
	private Boolean isBusinessNew;
	private Boolean isLevel3EligibleNew;
	private Boolean defaultValueNew;
	private String cvcNew;
	private String entityIdNew;
	private String entityTypeNew;
	private String entityVersionNew;
	private CardPresent cardPresentNew;

	private Card card;

	@BeforeTest
	public void init() {
		// values for testing getters
		id = "testid";
		number = "000";
		name = "testname";
		created = new Date(1220000000);
		updated = new Date(1229999999);
		address = new Address.Builder()
			.streetAddress("test street")
			.city("test city")
			.region("test region")
			.country("test country")
			.postalCode("A1A2B2")
			.build();
		commercialCardCode = "test card code";
		cvcVerification = new CvcVerification.Builder()
			.result("test result")
			.date(created)
			.build();
		cardType = "test card type";
		expMonth = "JANUARY";
		expYear = "2019";
		isBusiness = true;
		isLevel3Eligible = true;
		defaultValue = true;
		cvc = "test cvc";
		entityId = "123";
		entityType = "test type";
		entityVersion = "v1";
		cardPresent = new CardPresent.Builder()
			.track1("test track 1")
			.track2("test track 2")
			.ksn("ksn 1")
			.ksn("ksn 2")
			.pinBlock("pb 1")
			.build();

		// values for testing setters
		idNew = "testid new";
		numberNew = "111";
		nameNew = "testname new";
		createdNew = new Date(1221111111);
		updatedNew = new Date(1228888888);
		addressNew = new Address.Builder()
			.streetAddress("test street new")
			.city("test city new")
			.region("test region new")
			.country("test country new")
			.postalCode("B2BA1A")
			.build();
		commercialCardCodeNew = "test card code new";
		cvcVerificationNew = new CvcVerification.Builder()
			.result("test result new")
			.date(createdNew)
			.build();
		cardTypeNew = "test card type new";
		expMonthNew = "DECEMBER";
		expYearNew = "2020";
		isBusinessNew = false;
		isLevel3EligibleNew = false;
		defaultValueNew = false;
		cvcNew = "test cvc new";
		entityIdNew = "456";
		entityTypeNew = "test type new";
		entityVersionNew = "v2";
		cardPresentNew = new CardPresent.Builder()
			.track1("test track 3")
			.track2("test track 4")
			.ksn("ksn 3")
			.ksn("ksn 4")
			.pinBlock("pb 2")
			.build();
	}

	@BeforeMethod
	public void setUp() {
		card = new Card.Builder()
			.id(id)
			.number(number)
			.name(name)
			.createdDate(created)
			.updatedDate(updated)
			.address(address)
			.commercialCardCode(commercialCardCode)
			.cvcVerification(cvcVerification)
			.cardType(cardType)
			.expMonth(expMonth)
			.expYear(expYear)
			.isBusiness(isBusiness)
			.isLevel3Eligible(isLevel3Eligible)
			.defaultValue(defaultValue)
			.cvc(cvc)
			.entityId(entityId)
			.entityType(entityType)
			.entityVersion(entityVersion)
			.cardPresent(cardPresent)
			.build();
	}

	@Test
	public void testGetters() {
		Assert.assertEquals(card.getId(), id);
		Assert.assertEquals(card.getNumber(), number);
		Assert.assertEquals(card.getName(), name);
		Assert.assertEquals(card.getCreated(), created);
		Assert.assertEquals(card.getUpdated(), updated);
		Assert.assertEquals(card.getAddress(), address);
		Assert.assertEquals(card.getCommercialCardCode(), commercialCardCode);
		Assert.assertEquals(card.getCvcVerification(), cvcVerification);
		Assert.assertEquals(card.getCardType(), cardType);
		Assert.assertEquals(card.getExpMonth(), expMonth);
		Assert.assertEquals(card.getExpYear(), expYear);
		Assert.assertEquals(card.getIsBusiness(), isBusiness);
		Assert.assertEquals(card.getIsLevel3Eligible(), isLevel3Eligible);
		Assert.assertEquals(card.getDefaultValue(), defaultValue);
		Assert.assertEquals(card.getCvc(), cvc);
		Assert.assertEquals(card.getEntityId(), entityId);
		Assert.assertEquals(card.getEntityType(), entityType);
		Assert.assertEquals(card.getEntityVersion(), entityVersion);
		Assert.assertEquals(card.getCardPresent(), cardPresent);
	}

	@Test
	public void testSetters() {
		card.setId(idNew);
		card.setNumber(numberNew);
		card.setName(nameNew);
		card.setCreated(createdNew);
		card.setUpdated(updatedNew);
		card.setAddress(addressNew);
		card.setCommercialCardCode(commercialCardCodeNew);
		card.setCvcVerification(cvcVerificationNew);
		card.setCardType(cardTypeNew);
		card.setExpMonth(expMonthNew);
		card.setExpYear(expYearNew);
		card.setIsBusiness(isBusinessNew);
		card.setIsLevel3Eligible(isLevel3EligibleNew);
		card.setDefaultValue(defaultValueNew);
		card.setCvc(cvcNew);
		card.setEntityId(entityIdNew);
		card.setEntityType(entityTypeNew);
		card.setEntityVersion(entityVersionNew);
		card.setCardPresent(cardPresentNew);

		Assert.assertEquals(card.getId(), idNew);
		Assert.assertEquals(card.getNumber(), numberNew);
		Assert.assertEquals(card.getName(), nameNew);
		Assert.assertEquals(card.getCreated(), createdNew);
		Assert.assertEquals(card.getUpdated(), updatedNew);
		Assert.assertEquals(card.getAddress(), addressNew);
		Assert.assertEquals(card.getCommercialCardCode(), commercialCardCodeNew);
		Assert.assertEquals(card.getCvcVerification(), cvcVerificationNew);
		Assert.assertEquals(card.getCardType(), cardTypeNew);
		Assert.assertEquals(card.getExpMonth(), expMonthNew);
		Assert.assertEquals(card.getExpYear(), expYearNew);
		Assert.assertEquals(card.getIsBusiness(), isBusinessNew);
		Assert.assertEquals(card.getIsLevel3Eligible(), isLevel3EligibleNew);
		Assert.assertEquals(card.getDefaultValue(), defaultValueNew);
		Assert.assertEquals(card.getCvc(), cvcNew);
		Assert.assertEquals(card.getEntityId(), entityIdNew);
		Assert.assertEquals(card.getEntityType(), entityTypeNew);
		Assert.assertEquals(card.getEntityVersion(), entityVersionNew);
		Assert.assertEquals(card.getCardPresent(), cardPresentNew);
	}


}