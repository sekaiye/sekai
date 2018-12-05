package com.sekai.file.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImportExcelController {
    @RequestMapping("/file/importExcel/upload")
    public String upload(Model model, HttpServletRequest request){
        String readExcelAction = request.getParameter("readExcelAction").toString();
        String template = request.getParameter("template").toString();
        model.addAttribute("readExcelAction", readExcelAction);
        model.addAttribute("template", template);
        return "/file/import_excel/upload";
    }

}
