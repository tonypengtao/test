package com.pt.s3.test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.xmlpull.v1.XmlPullParserException;

import io.minio.MinioClient;
import io.minio.errors.MinioException;

public class FileUploader {

	public static void main(String[] args)
			throws NoSuchAlgorithmException, IOException, InvalidKeyException, XmlPullParserException {
		try {
			// Create a minioClient with the MinIO Server name, Port, Access key and Secret
			// key.
			MinioClient minioClient = new MinioClient("http://localhost:9000", "AKIAIOSFODNN7EXAMPLE",
					"wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");

			// Check if the bucket already exists.
			boolean isExist = minioClient.bucketExists("testbucket");
			if (isExist) {
				System.out.println("Bucket already exists.");
			} else {
				// Make a new bucket called asiatrip to hold a zip file of photos.
				minioClient.makeBucket("testbucket");
			}

			// Upload the zip file to the bucket with putObject
			minioClient.putObject("testbucket", "语言名称.md", "/Users/pengtao/Downloads/语言名称.md");
			System.out.println(
					"/etc/hosts is successfully uploaded as /etc/hosts to `testbucket` bucket.");
		} catch (MinioException e) {
			System.out.println("Error occurred: " + e);
		}
	}

}
