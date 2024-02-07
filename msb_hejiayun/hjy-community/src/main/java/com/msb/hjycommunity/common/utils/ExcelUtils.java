package com.msb.hjycommunity.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.msb.hjycommunity.common.core.exception.BaseException;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {
    private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * excel导出
     *
     * @param list         数据列表
     * @param pojoClass    pojo类型
     * @param fileName     导出的excel名称
     * @param response
     * @param exportParams 导出参数（标题，sheet名称，是否创建表头，表格类型）
     */
    public static void exportExcel(List<?> list, Class<?> pojoClass,
                                   String fileName,
                                   HttpServletResponse response,
                                   ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        downloadExcel(fileName, response, workbook);
    }

    /**
     * 下载
     *
     * @param fileName 文件名
     * @param response
     * @param workbook excel数据对象
     */
    public static void downloadExcel(String fileName,
                                     HttpServletResponse response, Workbook workbook) {
        ServletOutputStream outputStream = null;
        try {
            // 设置文件名和下载方式
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            log.error("导出Excel异常：{}", e.getMessage());
            throw new BaseException("500", "导出Excel失败，请联系网站管理员！");
        } finally {
            try {
                outputStream.close();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
