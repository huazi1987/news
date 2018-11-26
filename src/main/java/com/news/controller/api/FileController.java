package com.news.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.news.common.page.Pagination;
import com.news.model.NFile;
import com.news.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@CrossOrigin
@Controller
@RequestMapping(value = "/api/file")
public class FileController {
	
	private final static int PAGE_SIZE = 10;

	@Autowired
	private FileService fileService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request, int pageNo) {
		JSONObject result = new JSONObject();
		result.put("status", false);
		Pagination pagination = new Pagination(pageNo, PAGE_SIZE);

		Page<NFile> data = fileService.queryFileList(pagination);
		try {
			result.put("status", true);
			result.put("msg","ok");
			result.put("data", data);
			return result.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toJSONString();
	}

	@RequestMapping(value = "/download")
	public void download(String id, final HttpServletResponse response){
		try {
			NFile NFile = fileService.getFileById(id);
			if (null != NFile){
				String path = NFile.getPath();

				String suffix = path.substring(path.lastIndexOf(".") + 1);

				File file = new File(path);
				String fileName = URLEncoder.encode(file.getName(), "UTF-8");

				response.reset();
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+"."+suffix + "\"");
				response.addHeader("Content-Length", "" + file.length());
				response.setContentType("application/octet-stream;charset=UTF-8");

				BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				//输出流
				outputStream = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					outputStream.write(buff, 0, bytesRead);
				}

				outputStream.flush();
				outputStream.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
