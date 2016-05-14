package com.estsoft.utils;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AWStest {

	public static void main(String[] args) {
		//System.getProperties().list(System.out);
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAIBGMDSPZWRZSIR5A", "T8L6lP94vqaTz+ZeMMh88oia6xz3Hg5lrtPx6CsM");
		AmazonS3 s3Client = new AmazonS3Client(awsCreds);
		s3Client.setEndpoint("https://donkrazy.s3.ap-northeast-2.amazonaws.com");
		System.out.println(s3Client.listBuckets());
		System.out.println(awsCreds);
//		System.out.println(Region.getRegion(Regions.AP_NORTHEAST_1).isServiceSupported(ServiceAbbreviations.Dynamodb));
		  try {
		    System.out.println("Uploading a new object to S3 from a file\n");
		    File file = new File("/home/kim/test");
		    s3Client.putObject(new PutObjectRequest("/jblog", "zzzz", file));
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
