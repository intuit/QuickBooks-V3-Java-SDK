package com.intuit.ipp.example;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.intuit.ipp.core.Context;
import com.intuit.ipp.core.ServiceType;
import com.intuit.ipp.data.Attachable;
import com.intuit.ipp.data.AttachableResponse;
import com.intuit.ipp.exception.FMSException;
import com.intuit.ipp.security.OAuthAuthorizer;
import com.intuit.ipp.services.CallbackHandler;
import com.intuit.ipp.services.CallbackMessage;
import com.intuit.ipp.services.DataService;

public class UploadTest {

	public static DataService service;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String appToken = "replace app token here";

				String consumerKey = "replace consumer key here";
				String consumerSecret = "replace consumer secret here";
				String accessToken = "replace acces token  here";
				String accessTokenSecret = "replace access token secret here";

		OAuthAuthorizer oauth = new OAuthAuthorizer(consumerKey, consumerSecret, accessToken, accessTokenSecret);

		//String appDBId = "b7nmwqt6hr";
		String realmID = "replace company id here";
		Context context = null;

		try {
			context = new Context(oauth, appToken, ServiceType.QBO, realmID);

			service = new DataService(context);

			//executeUploadForImageFile(service);
			//executeUploadForTxt(service);
			//executeUpload(service);
			
			//executeUploadForImageFileAsync(service);
			executeDownloadForImageFile(service);
			//executeUploadForString(service);
		} catch (FMSException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void executeUploadForString(DataService service) throws ParseException, FMSException, FileNotFoundException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc.txt");
		attachable.setContentType("text/plain");
		
		String s = "This is just test...";
		InputStream in = new ByteArrayInputStream(s.getBytes());
		
		//Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		//Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
		
		Attachable attachableOutput = service.upload(attachable, in);
		System.out.println(attachableOutput);
		
		//String data = getContent(in);
		//writeContent(data);
		
		InputStream output = service.download(attachableOutput);
		System.out.println(getStringContent(output));
	}
	
	public static void executeDownloadForImageFile(DataService service) throws ParseException, FMSException, FileNotFoundException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("images.jpg");
		attachable.setContentType("image/jpeg");
		
		File file = new File("d:/images.jpg");
		InputStream in = new FileInputStream(file);
		
		//Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		//Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
		
		Attachable attachableOutput = service.upload(attachable, in);
		System.out.println(attachableOutput);
		
		//String data = getContent(in);
		//writeContent(data);
		
		service.downloadAsync(attachableOutput, new AsyncCallBackDownload());
		
	}
	
	public static void executeUploadForImageFileAsync(DataService service) throws ParseException, FMSException, FileNotFoundException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("images.jpg");
		attachable.setContentType("image/jpeg");
		
		File file = new File("d:/images.jpg");
		InputStream in = new FileInputStream(file);
		
		//Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		//Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
		
		service.uploadAsync(attachable, in, new AsyncCallBackUpload());
	}
	
	public static void executeUploadForImageFile(DataService service) throws ParseException, FMSException, FileNotFoundException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("images.jpg");
		attachable.setContentType("image/jpeg");
		
		File file = new File("d:/images.jpg");
		InputStream in = new FileInputStream(file);
		
		//Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		//Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
		
		Attachable attachableOutput = service.upload(attachable, in);
		System.out.println(attachableOutput);
		
		//String data = getContent(in);
		//writeContent(data);
		
		InputStream output = service.download(attachableOutput);
		writeImageContent(output);
	}

	public static void executeUploadForTxt(DataService service) throws ParseException, FMSException, FileNotFoundException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("abc.txt");
		attachable.setContentType("text/plain");
		
		File file = new File("d:/abc.txt");
		InputStream in = new FileInputStream(file);
		
		//Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		//Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
		
		Attachable attachableOutput = service.upload(attachable, in);
		System.out.println(attachableOutput);
		
		InputStream output = service.download(attachableOutput);
		writeTextContent(output);
	}
	
	public static void executeUpload(DataService service) throws ParseException, FMSException, FileNotFoundException {
		Attachable attachable = getAttachableFields();
		attachable.setFileName("XmlData.xml");
		attachable.setContentType("application/xml");
		
		File file = new File("d:/XmlData.xml");
		InputStream in = new FileInputStream(file);
		
		//Config.setProperty(Config.SERIALIZATION_REQUEST_FORMAT, ContentTypes.JSON.name().toLowerCase());
		//Config.setProperty(Config.SERIALIZATION_RESPONSE_FORMAT, ContentTypes.JSON.name().toLowerCase());
		
		Attachable attachableOutput = service.upload(attachable, in);
		System.out.println(attachableOutput);
		
		InputStream output = service.download(attachableOutput);
		writeContent(output);
	}
	
	public static Attachable getAttachableFields() {
		String uuid = UUID.randomUUID().toString().substring(0, 8);
		Attachable attachable = new Attachable();
		//attachable.setSize(new Long("34234"));
		attachable.setLat("25.293112341223");
		attachable.setLong("-21.3253249834");
		attachable.setPlaceName("Fake Place");
		attachable.setNote("Attachable note " + uuid);
		attachable.setTag("Attachable tag " + uuid);
		return attachable;
	}
	
	public static String getContent(InputStream in) throws FMSException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = in.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
			buffer.flush();
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		}
		return new String(buffer.toByteArray());
	}
	
	public static void writeTextContent(InputStream input) throws FMSException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = input.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
			
			OutputStream out = new FileOutputStream("d:/abc_new.txt");
			buffer.writeTo(out);
			
			out.close();
			buffer.flush();
			
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		}
	}
	
	public static void writeContent(InputStream input) throws FMSException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = input.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
			
			OutputStream out = new FileOutputStream("d:/XmlData_new.xml");
			buffer.writeTo(out);
			
			out.close();
			buffer.flush();
			
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		}
	}
	
	public static void writeImageContent(InputStream in) throws FMSException {
		try {
			BufferedImage bImageFromConvert = ImageIO.read(in);
 
			ImageIO.write(bImageFromConvert, "jpg", new File("d:/images_new.jpg"));
			
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		}
		
	}
	
	public static String getStringContent(InputStream input) throws FMSException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			int nRead;
			byte[] data = new byte[16384];
			while ((nRead = input.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
			
			buffer.flush();
			
		} catch (IOException e) {
			throw new FMSException("Error while reading the upload file.", e);
		}
		
		return new String(buffer.toByteArray());
	}
}

class AsyncCallBackUpload implements CallbackHandler {

	@Override
	public void execute(CallbackMessage callbackMessage) {
		System.out.println("In AsyncCallBackUpload callback...");
		List<AttachableResponse> attachableResponses = callbackMessage.getAttachableResponse();
		AttachableResponse attachableResponse = attachableResponses.get(0);
		
		Attachable attachableOutput = attachableResponse.getAttachable();
		
		System.out.println("callback output " + attachableOutput);
		
		try {
			InputStream output = UploadTest.service.download(attachableOutput);
			UploadTest.writeImageContent(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


class AsyncCallBackDownload implements CallbackHandler {

	@Override
	public void execute(CallbackMessage callbackMessage) {
		System.out.println("In AsyncCallBackDownload callback...");
		InputStream output = callbackMessage.getDownloadedFile();
		System.out.println("inoutstream : " + output);
		try {
			UploadTest.writeImageContent(output);
			System.out.println("Download complete!...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
