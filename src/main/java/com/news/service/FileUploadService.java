package com.news.service;

import com.news.common.page.PageRecord;
import com.news.common.page.Pagination;
import com.news.model.Video;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileUploadService {
	
	private static final String DOMAIN = "http://cdn.news.me/";

	@Value("#{appProperties['snapshow.s3.path.prefix']}")
	private String prefix;

	@Value("#{appProperties['snapshow.south.s3.path.prefix']}")
	private String southPrefix;

	/**
	 * 上传用户的头像和背景图
	 * @Description: 
	 * @param file
	 * @param userId
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public String uploadVideo(MultipartFile file, String userId,String fileType) throws Exception {
//		String key = createImageKey(file);
////		String filename = prefix + "avatar/" + userId + "/" + key;
////		if(!StringUtils.isEmpty(fileType)){
////			filename = prefix + "cover/" + userId + "/" + key;
////		}
////		return DOMAIN + UploadFileToS3.uploadFile(file.getInputStream(), filename, file.getSize());
		return "";
	}



	/**
	 * 上传视频封面
	 * 
	 * @param file
	 * @param isPress
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String uploadVideoThumbnail(MultipartFile uploadFile,String userId, String key)
			throws Exception {
//		long timestamp = new Date().getTime();
//		String filename = key.substring(0, 19) + timestamp + "." + getSuffixOfFile(uploadFile).toLowerCase();
//		return UploadFileToS3.uploadFile(uploadFile.getInputStream(), prefix + "image/" + userId + "/" + filename, uploadFile.getSize());
//	}
		return "";

	}


}
