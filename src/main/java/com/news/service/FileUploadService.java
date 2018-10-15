package com.news.service;

import com.aliyun.oss.OSSClient;
import com.google.common.base.Joiner;
import com.news.common.util.AliYunUtil;
import com.news.common.util.Hex64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class FileUploadService {

	@Value("#{appProperties['news.oss.endpoint']}")
	private String endpoint;
	
	private static final String DOMAIN = "https://communication-emperor.oss-cn-beijing.aliyuncs.com/";

	private static final String BUCKET_NAME = "communication-emperor";

	private final static String IMAGE_PATH = "image/";

	private final static String VIDEO_PATH = "video/";

	private final static String FILE_PATH = "file/";

	public String uploadVideo(MultipartFile file) throws Exception {
		String key = VIDEO_PATH+createKey(file);
		OSSClient ossClient = AliYunUtil.getOSSClient(endpoint);
		ossClient.putObject(BUCKET_NAME, key, file.getInputStream());
		return DOMAIN+key;
	}



	public String uploadImage(MultipartFile file) throws Exception {
		String key = IMAGE_PATH+createKey(file);
		OSSClient ossClient = AliYunUtil.getOSSClient(endpoint);
		ossClient.putObject(BUCKET_NAME, key, file.getInputStream());
		return DOMAIN+key;

	}

	public String uploadFile(MultipartFile file) throws Exception {
		String key = FILE_PATH+createKey(file);
		OSSClient ossClient = AliYunUtil.getOSSClient(endpoint);
		ossClient.putObject(BUCKET_NAME, key, file.getInputStream());
		return DOMAIN+key;
	}


	private String getSuffixOfFile(MultipartFile file) {
		return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
	}


	private String createKey(MultipartFile file) {
		return Joiner.on(".").join(initFileName(), getSuffixOfFile(file));
	}

	private String initFileName() {
		return Hex64.parse64Encode(Long.valueOf(String.valueOf(new Date().getTime()) + Math.round(Math.random() * 99)));
	}

}
