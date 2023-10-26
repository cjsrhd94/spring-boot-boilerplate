package com.cjsrhd94.boilerplate.global.s3;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.cjsrhd94.boilerplate.global.error.business.FileUploadException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Service {

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	private final AmazonS3Client amazonS3Client;

	public String upload(MultipartFile file, String dirPath) {
		if (file == null || file.isEmpty()) {
			throw new FileUploadException();
		}

		String filePath = dirPath + "/" + UUID.randomUUID()
			.toString().concat(extractFileExtension(file.getOriginalFilename()));

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentLength(file.getSize());
		objectMetadata.setContentType(file.getContentType());

		try (InputStream inputStream = file.getInputStream()) {
			amazonS3Client.putObject(new PutObjectRequest(bucket, filePath, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		} catch (IOException e) {
			throw new FileUploadException();
		}

		return "/" + filePath;
	}

	private String extractFileExtension(String imgName) {
		return imgName.substring(imgName.lastIndexOf("."));
	}

	public byte[] download(String fileUrl) {
		S3Object object = amazonS3Client.getObject(new GetObjectRequest(bucket, fileUrl));
		S3ObjectInputStream objectInputStream = object.getObjectContent();
		try {
			return IOUtils.toByteArray(objectInputStream);
		} catch (IOException e) {
			throw new FileUploadException();
		}
	}

	public String delete(String fileUrl) {
		amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileUrl));
		return "/" + fileUrl;
	}
}
