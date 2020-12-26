package com.ymw.love.system.controller.web;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.service.OssFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月23日 
*类说明：文件上传
*/
@Controller
@RequestMapping("/uploading")
@ResponseBody
@Api(tags = { "上传文件" })
public class UploadingFileController {

	@Autowired
	private OssFileService ossFileService;
	
	
	@PostMapping("/img")
	@ApiOperation(value = "上传图片", notes = "files 限制10文件之内")
	@Authority(Resource.enter.user_login_state)
	public Result img(@RequestPart("files") MultipartFile[] files,String name) throws InterruptedException, ExecutionException {
		List<String> list= ossFileService.fileUpload(files,1,name);
		return new Result(list);
	}
	
	
	
	
	@PostMapping("/video")
	@ApiOperation(value = "上传视频", notes = "files 限制10文件之内")
	@Authority(Resource.enter.user_login_state)
	public Result video(@RequestPart("files") MultipartFile[] files) throws InterruptedException, ExecutionException {
		List<String> list= ossFileService.fileUpload(files,2,null);
		return new Result(list);
	}
	
	
	
}
