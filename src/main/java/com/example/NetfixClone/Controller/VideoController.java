package com.example.NetfixClone.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.jcodec.api.JCodecException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.NetfixClone.Entity.Video;
import com.example.NetfixClone.Repository.VideoRepository;
import com.example.NetfixClone.Service.VideoService;

import lombok.experimental.PackagePrivate;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.EncryptionTypeMismatchException;
import software.amazon.awssdk.services.s3.model.InvalidRequestException;
import software.amazon.awssdk.services.s3.model.InvalidWriteOffsetException;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.TooManyPartsException;

@RestController
@RequestMapping("/videos")
@CrossOrigin( origins = "http://localhost:5173")
public class VideoController {
	
	@Autowired
    private VideoService videoService;
	
	@PostMapping("/upload")
	public String getAll(@RequestParam("file") MultipartFile file,@RequestParam("thumb") MultipartFile thumb,@RequestParam("description") String description) throws InvalidRequestException, InvalidWriteOffsetException, TooManyPartsException, EncryptionTypeMismatchException, S3Exception, AwsServiceException, SdkClientException, IOException, JCodecException, InterruptedException {
		
		
		Video video=new Video();
		video.setTitle(file.getOriginalFilename());
		video.setDescription(description);
		video.setUrl(videoService.uploadToS3( file));
		
		
		video.setThumbnail(videoService.uploadToS3(thumb));
		
		return videoService.save(video);
		
	}
	
	@GetMapping("/getAll")
	public List<Video> getAll(){
		
	return	videoService.getAll();
	}
	
	@GetMapping("/get/{id}")
	public Optional<Video> getById(@PathVariable("id") Long id) {
		return videoService.getById(id);
	}
		
		
	

}
