package com.news.cms.video.mapper;

import com.news.common.base.BaseMapper;
import com.ushareit.news.cms.video.model.VideoDTO;
import com.ushareit.news.common.base.BaseMapper;

import java.util.List;

public interface VideoMapper extends BaseMapper<VideoDTO> {

	public List<VideoDTO> queryVideoList();

	public boolean saveVideo();

	public boolean deleteVideo(long videoId);
}
