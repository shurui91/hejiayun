package com.msb.easypoi_boot.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.msb.easypoi_boot.entity.Course;
import com.msb.easypoi_boot.service.CourseService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        List<Course> courseList = courseService.findAll();
        System.out.println(courseList);
        model.addAttribute("courses", courseList);
        return "index";
    }

    // 导入excel
    @RequestMapping("/importExcel")
    public String importExcel(MultipartFile excelFile) throws Exception {
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);
        List<Course> list = ExcelImportUtil.importExcel(excelFile.getInputStream(), Course.class, params);
        courseService.save(list);
        return "redirect:/course/findAll";
    }

    // 导出excel
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) throws Exception {
        // 查询课程数据
        List<Course> courseList = courseService.findAll();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(
                "课程信息列表", "课程信息"), Course.class, courseList);
        response.setHeader("Content-disposition",
                "attachment;filename=" + URLEncoder.encode("课程信息列表.xls", "UTF" +
                        "-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }
}
