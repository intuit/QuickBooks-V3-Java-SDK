package com.intuit.payment;

import java.math.BigDecimal;
import java.util.List;

import com.intuit.payment.config.RequestContext;
import com.intuit.payment.config.RequestContext.Environment;
import com.intuit.payment.data.Address;
import com.intuit.payment.data.BankAccount;
import com.intuit.payment.data.BankAccount.AccountType;
import com.intuit.payment.data.Capture;
import com.intuit.payment.data.Card;
import com.intuit.payment.data.Charge;
import com.intuit.payment.data.CheckContext;
import com.intuit.payment.data.DeviceInfo;
import com.intuit.payment.data.ECheck;
import com.intuit.payment.data.ECheck.PaymentModeType;
import com.intuit.payment.data.PaymentContext;
import com.intuit.payment.data.QueryResponse;
import com.intuit.payment.data.Refund;
import com.intuit.payment.data.Token;
import com.intuit.payment.exception.BaseException;
import com.intuit.payment.services.BankAccountService;
import com.intuit.payment.services.CardService;
import com.intuit.payment.services.ChargeService;
import com.intuit.payment.services.ECheckService;
import com.intuit.payment.services.TokenService;

public class PaymentTest {
	
	private static final String accessToken = "eyJlbmMiOiJBMTI4Q0JDLUhTMjU2IiwiYWxnIjoiZGlyIn0..UBarBODprXk7iiVjenhdEQ.ngI6UY-EMuhR7TVntT3UDG_-APpy9TDnEDcLE1JgofwVVBCyPigAs6YkHY1_m5F8sFoyEuLDjyWaTDQFBA27hcHxGjTdcVfoIQJpUVPBh4oU9y-ReLdjkv1VZTciOFZmLLAuO8z-sYoPrmW8aN78O_srhQQHk3XJVNpfIY6xFyjpBsQrx1qvV7z28NW_Ar-AszYFb58avKKgIHrzT3ZX5raMIPBLYmPRstfBrODXMMXwUAt2-iofqAKCEQBgnw9YGg7ZaYyy3ElvqFWus9u5fLfKBplKPQBEVKNKzC5-6K_uihnOMvYgo9xUBNcibOm4wNmuJTGuzSIme4PCzmNU4NVDus-DfdIQxIpWNPoPNg-phzKbZjA_1bGA8MJmV3DyOtzvNwlXZag7o2i6KPIeNcorqWN53zbcQ-SXrZmPU1KQQy3miUlhCbx7zlronSArnlJWEOLsM_fHk_uchbNl1Pttevp4jIxX-q8y-Qu00itAgQ5f5gXqo3xAumk3UDt6FLcnOBQOuNPay2X6H-Vi6gabg_6MYF16L3v-uzGElN8xkjmoRK6MzEiW_1f3mzlY6vTZngYkgaL9n1Lf4Wu6bPeLSk2xADp8rw6w-85G-meoMrzcGNkgoEwYjG9UACozR2uiRrJ-FNOlZOCx3Z6l7UgqK3kiARPIORH9Xc4cE4AGjRx6KtrfuQ1l6dCvNQ9X.JX_F2D9hl_T-HJ7WZPXTkw"; 
	
	public static void main (String[] args) {
		
		RequestContext requestContext = new RequestContext.Builder(accessToken, Environment.SANDBOX).build();
	
		/*
		 TOKEN 
		createToken(requestContext);
		
		 ECHECK 
		createECheck(requestContext); 
		retrieveECheck(requestContext); //TODO check why everything comes as declined
		refundECheck(requestContext); //TODO test this once above error is fixed
		getECheckRefund(requestContext); //TODO test this once above error is fixed
		
		 CHARGE 
		createCharge(requestContext); 
		retrieveCharge(requestContext);
		captureCharge(requestContext);
		refundCharge(requestContext);   //TODO test why refund is giving invalid status
		getChargeRefund(requestContext);//TODO test this once above error is fixed
		
		 CARD 
		createCard(requestContext);
		getCard(requestContext);
		deleteCard(requestContext);
		createCardFromToken(requestContext);
		getAllCards(requestContext);
		
		 BANK 
		createBankAccount(requestContext);
		getBankAccount(requestContext);
		deleteBankAccount(requestContext);
		createBankAccountFromToken(requestContext);*/; 
		
	}

	
	private static void createToken(RequestContext requestContext) {
		
		TokenService tokenService = new TokenService(requestContext);
		
		Address address = new Address.Builder().region("CA").postalCode("94086")
				.streetAddress("1130 Kifer Rd").city("Sunnyvale").country("US")
				.build();
		
		Card card = new Card.Builder().expYear("2020").expMonth("02")
				.address(address).name("emulate=0").cvc("123").number("4111111111111111")
				.build();
				
		Token tokenRequest = new Token.Builder().card(card).build();
		
		Token token;
		
		try {
			token = tokenService.createToken(tokenRequest);
			System.out.println("getIntuit_tid:::" + token.getIntuit_tid());
			System.out.println("token:::" + token.getValue());
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void createECheck(RequestContext requestContext) {
		
		ECheckService eCheckService = new ECheckService(requestContext);
		
		
		BankAccount bankAccount = new BankAccount.Builder()
				.name("Fname LName").routingNumber("490000018")
				.accountNumber("11000000333456781").accountType(AccountType.PERSONAL_CHECKING)
				.phone("1234567890").build();
		
		DeviceInfo deviceInfo = new DeviceInfo.Builder()
				.id("1").type("type").longitude("longitude")
				.phoneNumber("phoneNumber").macAddress("macAddress").ipAddress("34")
				.build();
		CheckContext context = new CheckContext.Builder(deviceInfo).build();

		ECheck 	eCheckRequest = new ECheck.Builder()
				.amount(new BigDecimal("5.55")).bankAccount(bankAccount)
				.context(context).paymentMode(PaymentModeType.WEB)
				.checkNumber("12345678").description("Check Auth test call")
				.build();
		
		ECheck eCheck;
		
		try {
			eCheck = eCheckService.create(eCheckRequest);
			System.out.println("getIntuit_tid:::" + eCheck.getIntuit_tid());
			System.out.println("echeck id:::" + eCheck.getId());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
		
	
	}
	
	private static void retrieveECheck(RequestContext requestContext) {
			
		ECheckService eCheckService = new ECheckService(requestContext);		
		ECheck eCheck;
		
		try {
			eCheck = eCheckService.retrieve("aidj1qng");
			System.out.println("getIntuit_tid:::" + eCheck.getIntuit_tid());
			System.out.println("echeck id:::" + eCheck.getId() + " " + eCheck.getStatus().toString());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void refundECheck(RequestContext requestContext) {
		
		ECheckService eCheckService = new ECheckService(requestContext);	
		Refund refundRequest = new Refund();
		refundRequest.setAmount(new BigDecimal("1.11"));
		Refund refund;
		
		try {
			refund = eCheckService.refund("aidj1jv2", refundRequest);
			System.out.println("getIntuit_tid:::" + refund.getIntuit_tid());
			System.out.println("echeck id:::" + refund.getId() + " " + refund.getStatus().toString());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void getECheckRefund(RequestContext requestContext) {
		
		ECheckService eCheckService = new ECheckService(requestContext);	
		Refund refund;
		
		try {
			refund = eCheckService.getRefund("aidj1jv2", "refundId");
			System.out.println("getIntuit_tid:::" + refund.getIntuit_tid());
			System.out.println("echeck id:::" + refund.getId() + " " + refund.getStatus().toString());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}

	private static void createCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);
		
		Address address = new Address.Builder().region("CA")
				.postalCode("94086").streetAddress("1130 Kifer Rd")
				.city("Sunnyvale").country("US").build();
		
		Card card = new Card.Builder()
				.expYear("2020").expMonth("02")
				.address(address).name("emulate=0")
				.cvc("123").number("4111111111111111").build();
		
		PaymentContext context = new PaymentContext.Builder()
				.mobile("false").isEcommerce("true").build();
		
		Charge chargeRequest = new Charge.Builder()
				.amount(new BigDecimal("5.55")).card(card)
				.currency("USD").context(context).build();
		Charge charge;
		
		try {
			charge = chargeService.create(chargeRequest);
			System.out.println("getIntuit_tid:::" + charge.getIntuit_tid());
			System.out.println("charge id:::" + charge.getId());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
		
	
	}

	private static void retrieveCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);		
		Charge charge;
		
		try {
			charge = chargeService.retrieve("EHEU7RYCXRWO");
			System.out.println("getIntuit_tid:::" + charge.getIntuit_tid());
			System.out.println("charge id:::" + charge.getId() + " " + charge.getStatus().toString());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void captureCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);	
		
		PaymentContext context = new PaymentContext.Builder().mobile("false").isEcommerce("true").build();
		Capture capture = new Capture.Builder().amount(new BigDecimal("10.55")).context(context).build();	
		Charge charge;
		
		try {
			charge = chargeService.capture("E4COO4EQG7T4", capture);
			System.out.println("getIntuit_tid:::" + charge.getIntuit_tid());
			System.out.println("charge id:::" + charge.getId() + " " + charge.getStatus().toString());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void refundCharge(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);	
		
		PaymentContext context = new PaymentContext.Builder().recurring(Boolean.FALSE).build();
		Refund refundRequest = new Refund.Builder().amount(new BigDecimal("5.55")).description("first refund").context(context).build();
		Refund refund;
		
		try {
			refund = chargeService.refund("EHEU7RYCXRWO", refundRequest);
			System.out.println("getIntuit_tid:::" + refund.getIntuit_tid());
			System.out.println("refund id:::" + refund.getId() + " " + refund.getStatus().toString());
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void getChargeRefund(RequestContext requestContext) {
		
		ChargeService chargeService = new ChargeService(requestContext);	
		Refund refund;
		
		try {
			refund = chargeService.getRefund("E4COO4EQG7T4", "refundRequest");
			System.out.println("getIntuit_tid:::" + refund.getIntuit_tid());
			System.out.println("refund id:::" + refund.getId() + " " + refund.getStatus().toString());
			
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void createCard(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);
		
		Address address = new Address.Builder().region("VA")
				.postalCode("44112").streetAddress("1245 Hana Rd")
				.city("Richmond").country("US").build();
		
		Card cardRequest = new Card.Builder().expYear("2026").expMonth("12")
				.address(address).name("Test User").number("4408041234567893").build();
		
		Card card;
		
		try {
			card = cardService.create(cardRequest, "1");
			System.out.println("getIntuit_tid:::" + card.getIntuit_tid());
			System.out.println("card:::" + card.getId());
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void getCard(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);	
		Card card;
		
		try {
			card = cardService.getCard("1", "101166415564139279157893");
			System.out.println("getIntuit_tid:::" + card.getIntuit_tid());
			System.out.println("card id:::" + card.getId() );
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteCard(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);
		try {
			Card card = cardService.delete("1", "101131650154139279257893");
			System.out.println("getIntuit_tid:::" + card.getIntuit_tid());
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void createCardFromToken(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);
		TokenService tokenService = new TokenService(requestContext);
		
		Address address = new Address.Builder().region("CA")
				.postalCode("94086").streetAddress("1130 Kifer Rd")
				.city("Sunnyvale").country("US").build();
		
		Card card = new Card.Builder()
				.expYear("2020").expMonth("02")
				.address(address).name("emulate=0")
				.cvc("123").number("4111111111111111").build();
				
		Token tokenRequest = new Token.Builder().card(card).build();
		Card cardResponse;
		
		try {
			Token token = tokenService.createToken(tokenRequest);
			System.out.println("getIntuit_tid:::" + token.getIntuit_tid());
			System.out.println("value:::" + token.getValue());
			
			cardResponse = cardService.createFromToken(token, "1");
			System.out.println("getIntuit_tid:::" + cardResponse.getIntuit_tid());
			System.out.println("card:::" + cardResponse.getId());
		} catch (BaseException e) {
			e.printStackTrace();
		}		
	}
	
	private static void getAllCards(RequestContext requestContext) {
		
		CardService cardService = new CardService(requestContext);	
		
		try {
			QueryResponse response = cardService.getAllCards("1", 0);
			List<Card> cards = response.getCards();
			System.out.println("card list size:::" + cards.size());
			System.out.println("getIntuit_tid:::" + response.getIntuit_tid());
			
		} catch (BaseException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void createBankAccount(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		
		BankAccount bankAccountRequest = new BankAccount.Builder()
				.name("My Checking").routingNumber("091000019")
				.accountNumber("120895674534").accountType(AccountType.PERSONAL_CHECKING)
				.phone("6047296480").build();
		BankAccount bankAccount;
		
		try {
			bankAccount = bankAccountService.create(bankAccountRequest, "1");
			System.out.println("bankAccount:::" + bankAccount.getId());
			System.out.println("bankAccount:::" + bankAccount.getIntuit_tid());
			
			
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void getBankAccount(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		BankAccount bankAccount;
		
		try {
			bankAccount = bankAccountService.getBankAccount("1", "200160963874139279894534");
			System.out.println("getIntuit_tid:::" + bankAccount.getIntuit_tid());
			System.out.println("bankAccount id:::" + bankAccount.getId() );
			
		
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteBankAccount(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		try {
			BankAccount bankAccount = bankAccountService.delete("1", "200149017354139279934534");
			System.out.println("getIntuit_tid:::" + bankAccount.getIntuit_tid());
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}
	
	private static void createBankAccountFromToken(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		TokenService tokenService = new TokenService(requestContext);
		
		BankAccount bankAccountRequest = new BankAccount.Builder()
				.name("My Checking").routingNumber("091000019")
				.accountNumber("120895674534").accountType(AccountType.PERSONAL_CHECKING)
				.phone("6047296480").build();
				
		Token tokenRequest = new Token.Builder().bankAccount(bankAccountRequest).build();
		BankAccount bankAccount;
		
		try {
			Token token = tokenService.createToken(tokenRequest);
			System.out.println("token:::" + token.getIntuit_tid());
			System.out.println("token:::" + token.getValue());
			
			bankAccount = bankAccountService.createFromToken(token, "1");
			System.out.println("getIntuit_tid:::" + bankAccount.getIntuit_tid());
			System.out.println("bankAccount:::" + bankAccount.getId());
		} catch (BaseException e) {
			e.printStackTrace();
		}		
	}
	
	private static void getAllBankAccounts(RequestContext requestContext) {
		
		BankAccountService bankAccountService = new BankAccountService(requestContext);	
		
		try {
			QueryResponse response = bankAccountService.getAllBankAccounts("1");
			List<BankAccount> bankAccounts = response.getBankAccounts();
			System.out.println("bank account list size:::" + bankAccounts.size());
			System.out.println("getIntuit_tid:::" + response.getIntuit_tid());
			
			
		} catch (BaseException e) {
			e.printStackTrace();
		}
		
	}
	
}
