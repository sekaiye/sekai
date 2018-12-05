package com.sekai.file.controller;

import com.sekai.system.service.ConfigService;
//import com.sun.deploy.net.URLEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@Controller
public class FileDownLoadController {
	@Resource
	ConfigService configService;
	@RequestMapping("/fileDownLoad/images")
    public String images(HttpServletResponse response,HttpServletRequest request,
						 @RequestParam("fileName")String fileName) throws IOException {
		String downloadFileName=fileName.substring(fileName.lastIndexOf("/"),fileName.length());
		//response.setContentType("image/jpeg/jpg/png/gif/bmp/tiff/svg"); // 设置返回内容格式
		response.setContentType("application/octet-stream;charset=UTF-8");
		System.out.println(fileName);
		//downloadFileName= URLEncoder.encode(downloadFileName,"UTF-8");

		downloadFileName= URLDecoder.decode(downloadFileName,"iso-8859-1");

		response.setHeader("Content-Disposition","attachment;filename="+downloadFileName);
		String fileUploadPath = configService.getConfigByCode("fileUploadPath").getCfValue();
		String path=fileUploadPath+"/"+fileName;
		//path=new String(path.getBytes("ISO-8859-1"),"UTF-8");
		File file = new File(path);       //括号里参数为文件图片路径
		System.out.println("path:"+path);
		if(file.exists()){   //如果文件存在
			System.out.println("文件存在");
			InputStream in = new FileInputStream(path);   //用该文件创建一个输入流
			OutputStream os = response.getOutputStream();  //创建输出流
			byte[] b = new byte[1024];
			while( in.read(b)!= -1){
				os.write(b);
			}
			in.close();
			os.flush();
			os.close();
		}else{
			System.out.println("文件【不】存在");
		}
		return null;
	}

}