/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cmb.services;

//import com.nibssplc.icadservice.exceptions.SecException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author waliu faleye
 */
@Service
public class SampleAESCrypter {

	// private String iv = "uE:Fh8jToN186*fY";//
	// bytesToHex("uE:Fh8jToN186*fY".getBytes());
	// //"uE:Fh8jToN186*fY"//1234567890123456";
	private String iv = "RwoAH0eRx8JndvNH";
	private String secretkey = "yR37JfD5n8XipRg+";// !654XYZ_#9abcdEF";
	private IvParameterSpec ivspec;
	private SecretKeySpec keyspec;
	private Cipher cipher;

	// a45020b63a38a92ab702b69c1b41e32a
	// d4b3e9f4b7b3a294a35b890d64a1819d
	// 7778280145c9d0b14ba0f5102ae96aa8
	public static void main(String args[]) throws UnsupportedEncodingException, Exception {
		// af0fbb97b45cd873aee4c4e81af858aa
		System.out.println(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		SampleAESCrypter crypta = new SampleAESCrypter();
		// String toenc = "{ \"accountName\":\"ISA AYO OBI \",
		// \"requestID\":\"999559201702271354520000000001\",
		// \"dateOfBirth\":\"20001210\", \"email\":\"adamu@you.com\",
		// \"dateOpened\":\"20170211\", \"accountNumber\":\"2990002740\",
		// \"phoneNumber\":\"2348031234567\",
		// \"phoneNumber2\":\"2348031234567\", \"currency\":1,
		// \"firstName\":\"ISA\", \"middleName\":\"AYO\", \"lastName\":\"OBI\",
		// \"branchCode\":\"559999999\", \"accountStatus\":1,
		// \"institutionCode\":\"559\", \"accountType\":1,
		// \"accountDesignation\":1, \"oldAccountNumber\":\"NA\",
		// \"uniqueCustomerId\":\"2333\", \"dateModified\":\"20170227\",
		// \"rcNo\":\"NA\", \"bvns\":[ \"22876534321\" ] }";// "0693621939";
		// working
		// String toenc = "[ { \"accountName\":\"Test Account\",
		// \"requestID\":\"999559201702282008370000000002\",
		// \"dateOfBirth\":\"20101012\", \"email\":\"test@test.com\",
		// \"dateOpened\":\"20170216\", \"accountNumber\":\"2990002718\",
		// \"phoneNumber\":\"2348033474913\",
		// \"phoneNumber2\":\"2348033474913\", \"currency\":1,
		// \"firstName\":\"Test\", \"middleName\":\"\", \"lastName\":\"Test\",
		// \"branchCode\":\"559999999\", \"accountStatus\":1,
		// \"institutionCode\":\"559\", \"accountType\":1,
		// \"accountDesignation\":1, \"oldAccountNumber\":\"NA\",
		// \"uniqueCustomerId\":\"24\", \"dateModified\":\"20170227\",
		// \"rcNo\":\"880696\", \"bvns\":[ { \"bvn\":\"22150655028\" } ] },"
		// + "{ \"accountName\":\"Sample
		// Test\",\"requestID\":\"999559201702282008370000000003\",
		// \"dateOfBirth\":\"20101012\", \"email\":\"sample@test.com\",
		// \"dateOpened\":\"20170216\", \"accountNumber\":\"2990002719\",
		// \"phoneNumber\":\"2348053953344\",
		// \"phoneNumber2\":\"2348033474913\", \"currency\":1,
		// \"firstName\":\"Sample Test\", \"middleName\":\"test\",
		// \"lastName\":\" Sample Test \", \"branchCode\":\"559999999\",
		// \"accountStatus\":1, \"institutionCode\":\"559\", \"accountType\":1,
		// \"accountDesignation\":1, \"oldAccountNumber\":\"NA\",
		// \"uniqueCustomerId\":\"20457994\", \"dateModified\":\"20170227\",
		// \"rcNo\":\"880697\", \"bvns\":[ { \"bvn\":\"22150655028\" } ] } ]";
		String toenc = "password";

		String enc = "";// d4b3e9f4b7b3a294a35b890d64a1819d";
		String dec = "";
		String todec = "268d27d08334aa0e4c65b0e4645c2ae3";
		// 7ef8f476ff84428d013e777b5afd5a0e

		enc = crypta.encrypt(toenc);
		System.out.println(" ENCED : " + enc);
		System.out.println(" dycrypted =" + crypta.decrypt(todec));
		// System.out.println("url == "+getUrlDetails("123", "admin"));

		// dec = crypta.decrypt(toenc);

		dec = crypta.decrypt(enc);
		dec = URLDecoder.decode(dec, "UTF-8");
		System.out.println(" DECED : " + dec);
	}

	public SampleAESCrypter(String keyz, String ivStr) {
		ivspec = new IvParameterSpec(ivStr.getBytes());
		keyspec = new SecretKeySpec(keyz.getBytes(), "AES");

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public SampleAESCrypter() {
		ivspec = new IvParameterSpec(iv.getBytes());
		keyspec = new SecretKeySpec(secretkey.getBytes(), "AES");

		System.out.println("this ivspec = " + ivspec);

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String text) throws Exception {

		System.out.println("text = " + text);

		if (text == null || text.length() == 0) {
			throw new Exception("Empty string");
		}
		byte[] encrypted = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new Exception("[encrypt] " + e.getMessage());
		}
		return bytesToHex(encrypted);
	}

	public String decrypt(String code) throws Exception, UnsupportedEncodingException {
		if (code == null || code.length() == 0) {
			throw new Exception("Empty string");
		}
		byte[] decrypted = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
			decrypted = cipher.doFinal(hexToBytes(code));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("[decrypt] " + e.getMessage());
		}
		return new String(decrypted, "UTF-8");
	}

	public static String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		}
		int len = data.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			if ((data[i] & 0xFF) < 16) {
				str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
			} else {
				str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
			}
		}
		return str;
	}

	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	// public static String getUrlDetails(String username, String sessionId)
	public static String getUrlDetails() {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);

		String sessionId = session.getId();
		String username = (String) session.getAttribute("username");

		SampleAESCrypter sac = new SampleAESCrypter();
		String response = "";
		String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		try {
			response = sac.encrypt(sessionId + "," + username + "," + timeStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	public static String getMarketUrlDetails() {

		String fundType = "Coronation Money Market Fund";
		SampleAESCrypter sac = new SampleAESCrypter();
		String response = "";
		try {
			response = sac.encrypt(fundType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	public static String getBalancedUrlDetails() {

		String fundType = "Coronation Balanced Fund";
		SampleAESCrypter sac = new SampleAESCrypter();
		String response = "";
		try {
			response = sac.encrypt(fundType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	public static String getIncomeUrlDetails() {

		String fundType = "Coronation Fixed Income Fund";
		SampleAESCrypter sac = new SampleAESCrypter();
		String response = "";
		try {
			response = sac.encrypt(fundType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
}
