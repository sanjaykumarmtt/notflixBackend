package com.example.NetfixClone.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.NetfixClone.Entity.Video;
import com.example.NetfixClone.Repository.VideoRepository;

import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.EncryptionTypeMismatchException;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.InvalidRequestException;
import software.amazon.awssdk.services.s3.model.InvalidWriteOffsetException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.TooManyPartsException;

@Service
public class VideoService {
	
	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private S3Client s3Client;
	
	@Value("${aws.s3.bucket-name}")
	private String bucket;
	
	
	

	
	public String uploadToS3(MultipartFile file) throws InvalidRequestException, InvalidWriteOffsetException, TooManyPartsException, EncryptionTypeMismatchException, S3Exception, AwsServiceException, SdkClientException, IOException {
		
		s3Client.putObject(PutObjectRequest.builder().bucket(bucket).key(file.getOriginalFilename()).contentType(file.getContentType()).build(),
				RequestBody.fromBytes(file.getBytes()));
		
		 return "https://"+bucket+".s3.amazonaws.com/"+file.getOriginalFilename();
		
	}





	public String save(Video video) {
		// TODO Auto-generated method stub
		
		videoRepository.save(video);
		
		return "Video Add SuccessFully";
		
	}



	public List<Video> getAll() {
		// TODO Auto-generated method stub
		
	  return videoRepository.findAll();
		
	}





	public Optional<Video> getById(Long id) {
		// TODO Auto-generated method stub
		
		return videoRepository.findById(id);
	}
	
	
	public byte[] downloadFromS3(String bucket,String key) {
		GetObjectRequest request=GetObjectRequest.builder().bucket(bucket).key(key).build();
		
		ResponseBytes<GetObjectResponse> objBytes=s3Client.getObjectAsBytes(request);
		return objBytes.asByteArray();
	}
	
	

}