package com.estsoft.utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class FileUpload {
	
	public static String genSaveFileName( String extName ) {
		
        Calendar calendar = Calendar.getInstance();
		String fileName = "";
        
        fileName += calendar.get( Calendar.YEAR );
        fileName += calendar.get( Calendar.MONTH );
        fileName += calendar.get( Calendar.DATE );
        fileName += calendar.get( Calendar.HOUR );
        fileName += calendar.get( Calendar.MINUTE );
        fileName += calendar.get( Calendar.SECOND );
        fileName += calendar.get( Calendar.MILLISECOND );
        fileName += ( "." + extName );
        
        return fileName;
	}
	
	public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File( multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
	}
	
	public static void putAWS(String saveFileName, File file){
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIBGMDSPZWRZSIR5A", "T8L6lP94vqaTz+ZeMMh88oia6xz3Hg5lrtPx6CsM");
		AmazonS3 s3Client = new AmazonS3Client(awsCreds);
		s3Client.setEndpoint("https://donkrazy.s3.ap-northeast-2.amazonaws.com");
	    try {
		    System.out.println("Uploading a new object to S3 from a file\n");
		    s3Client.putObject(new PutObjectRequest("/jblog", saveFileName, file));
	    } catch (AmazonServiceException ase) {
	    	System.out.println("Caught an AmazonServiceException, which " +
	    		"means your request made it " +
	            "to Amazon S3, but was rejected with an error response" +
	            " for some reason.");
		    System.out.println("Error Message:    " + ase.getMessage());
		    System.out.println("HTTP Status Code: " + ase.getStatusCode());
		    System.out.println("AWS Error Code:   " + ase.getErrorCode());
		    System.out.println("Error Type:       " + ase.getErrorType());
		    System.out.println("Request ID:       " + ase.getRequestId());
	    } catch (AmazonClientException ace) {
	    	System.out.println("Caught an AmazonClientException, which " +
	    		"means the client encountered " +
	            "an internal error while trying to " +
	            "communicate with S3, " +
	            "such as not being able to access the network.");
	    	System.out.println("Error Message: " + ace.getMessage());
	    }
	}
}