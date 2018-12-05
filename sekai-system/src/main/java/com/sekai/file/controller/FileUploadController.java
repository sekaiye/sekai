package com.sekai.file.controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sekai.system.service.ConfigService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {
	@Resource
	ConfigService configService;
	@RequestMapping("/file/fileUpload/upload")
    public String upload(Model model, HttpServletRequest request,
						 @RequestParam("saveDir")String saveDir){
		model.addAttribute("saveDir", saveDir);
		return "file/file_upload/upload";
	}
	@RequestMapping("/file/fileUpload/doUpload")
	public String doUpload(Model model, HttpServletRequest request,
						   @RequestParam("file") MultipartFile file,
						   @RequestParam("saveDir")String saveDir) throws Exception{
		String fileUploadPath = configService.getConfigByCode("fileUploadPath").getCfValue();
		String savePath = saveDir + "/" + file.getOriginalFilename();
		String saveFullPath = fileUploadPath +"/"+savePath;
		File filepath = new File(saveFullPath);
		if (!filepath.getParentFile().exists()) {
			filepath.getParentFile().mkdirs();
		}
		file.transferTo(new File(saveFullPath));
		model.addAttribute("savePath", savePath);
		return "file/file_upload/upload_result";
	}
}