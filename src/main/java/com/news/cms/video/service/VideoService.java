package com.news.cms.video.service;

import com.news.cms.video.model.VideoDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.ushareit.news.cms.video.model.VideoDTO;
import com.ushareit.news.common.page.Pagination;

@Service
public class VideoService {


	public Page<VideoDTO> getItemByPage(Pagination pagination) throws Exception {
		
//		PageRecord<VideoDTO> result = new PageRecord<>(toItemDTO(esResult.getHits(), topic), pagination,esResult.getTotal());
		return null;
	}



	public VideoDTO getItemDTOById(String itemId) {
		return null;
	}



	public VideoDTO saveItem(VideoDTO videoDTO) {

		return null;
	}


	public void delete(String videoId) {

	}




}
