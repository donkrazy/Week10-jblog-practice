package com.estsoft.utils;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;
 
@Component
public class DecryptDataSource extends BasicDataSource{
	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
 
	@Override
	public void setPassword(String password) {
		super.setPassword(encryptor(password));
	}
 
	@Override
	public synchronized void setUrl(String url) {
		super.setUrl(encryptor(url));
	}
 
	@Override
	public void setUsername(String username) {
		super.setUsername(encryptor(username));
	}
 
	public String encryptor(String param){
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("encKey");//암호화키
		
		return encryptor.decrypt(param);
	}
}