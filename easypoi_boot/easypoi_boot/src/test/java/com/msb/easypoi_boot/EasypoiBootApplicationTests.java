package com.msb.easypoi_boot;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.msb.easypoi_boot.pojo.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

@SpringBootTest
class EasypoiBootApplicationTests {
    public List<User> getUser() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(String.valueOf(i));
            user.setName("文渊");
            user.setAge(16 + i);
            user.setBir(new Date());
            user.setStatus(String.valueOf(i % 2));
            user.setHobby(Arrays.asList("smoking", "drinking", "video games"));
            user.setCard(new Card("11023199010113210", "北京朝阳区"));
            //user.setOrderList(Arrays.asList(new Order("1001", "黑色篮球鞋"), new Order("1001", "棒棒糖")));
            user.setPhoto("/Users/user/Downloads/ma.png");
            list.add(user);
        }
        return list;
    }

    @Test
    public void testExportExcel() throws Exception {
        // 1. 配置对象，2. 导出类型，3. 导出数据集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("员工列表", "测试"), User.class, getUser());
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/user/Downloads/user.xls");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
    }

//    @Test
//    public void testImportExcel() throws Exception {
//        ImportParams params = new ImportParams();
//        params.setTitleRows(1); // 标题列占几行
//        params.setHeadRows(1);  // 列名占几行
//        params.setNeedSave(true);
//        params.setSaveUrl("/Users/user/Downloads/msb_hejiayun/easypoi_boot/easypoi_boot/src/main/resources/static");
//        List<Emp> list = ExcelImportUtil.importExcel(new FileInputStream("/Users/user/Downloads/emp.xls"), Emp.class, params);
//        list.forEach(System.out::println);
//    }

    // =============多sheet导入方法=============

    /**
     * 接收excel文件导入的多个sheet页
     *
     * @param filePath   导入文件路径
     * @param sheetIndex 导入sheet索引
     * @param titleRows  表标题行数
     * @param headerRows 表头行数
     * @param pojo       excel实体
     * @param <T>
     * @return
     */
    public static <T> List<T> importMultiSheet(String filePath, int sheetIndex, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        // 根据file得到Workbook,主要是要根据这个对象获取,传过来的excel有几个sheet页
        ImportParams params = new ImportParams();

        // 第几个sheet页
        params.setStartSheetIndex(sheetIndex);
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);

        //是否保存本次上传的excel
        params.setNeedSave(false);

        //表示表头必须包含的字段,不包含就报错
        params.setImportFields(new String[]{"用户ID"});

        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new FileInputStream(filePath), pojoClass, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 测试导入多个sheet页
    @Test
    public void testImportMultiSheet() throws Exception {
        String excelPath = "/Users/user/Downloads/login.xls";
        List<LoginUser> loginUsers = importMultiSheet(excelPath, 0, 1, 1, LoginUser.class);
        loginUsers.forEach(System.out::println);

        List<LoginUrl> loginUrls = importMultiSheet(excelPath, 1, 1, 1, LoginUrl.class);
        loginUrls.forEach(System.out::println);
    }


    // =============多sheet导出方法=============

    /**
     * 导出多个sheet页方法
     *
     * @param objects
     */
    public static void exportMultiSheet(Object... objects) {
        // 创建参数对象,用于设定Excel的sheet页内容等信息
        ExportParams loginUserExportParams = new ExportParams();
        //设置sheet的名称
        loginUserExportParams.setSheetName("登录用户");
        loginUserExportParams.setTitle("登录用户列表");

        //使用map创建sheet1
        HashMap<String, Object> sheet1Map = new HashMap<>();
        //设置title
        sheet1Map.put("title", loginUserExportParams);
        //设置导出的实体类型
        sheet1Map.put("entity", LoginUser.class);
        //sheet中要填充的数据
        sheet1Map.put("data", objects[0]);

        // sheet 2
        //创建参数对象,用于设定Excel的sheet页内容等信息
        ExportParams loginUrlExportParams = new ExportParams();
        //设置sheet的名称
        loginUrlExportParams.setSheetName("URL路径");
        loginUrlExportParams.setTitle("URL路径");

        // 使用map创建sheet2
        HashMap<String, Object> sheet2Map = new HashMap<>();
        // 设置title
        sheet2Map.put("title", loginUrlExportParams);
        // 设置导出的实体类型
        sheet2Map.put("entity", LoginUrl.class);
        // sheet中要填充的数据
        sheet2Map.put("data", objects[1]);

        // 将sheet1和sheet2 进行包装
        List<Map<String, Object>> sheetList = new ArrayList<>();
        sheetList.add(sheet1Map);
        sheetList.add(sheet2Map);

        // 执行方法
        Workbook workbook = ExcelExportUtil.exportExcel(sheetList, ExcelType.HSSF);
        try {
            workbook.write(new FileOutputStream("/Users/user/Downloads/loginExport.xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExportMultiSheet() throws Exception {
        List<LoginUser> sheet1 = new ArrayList<>();
        sheet1.add(new LoginUser("1001", "向阳", "123456", new Date(), "0"));
        sheet1.add(new LoginUser("1002", "文渊", "123456", new Date(), "1"));
        sheet1.add(new LoginUser("1003", "小岳岳", "123456", new Date(), "0"));

        List<LoginUrl> sheet2 = new ArrayList<>();
        sheet2.add(new LoginUrl("1001", "get", "http://127.0.0.1:8080"));
        sheet2.add(new LoginUrl("1001", "post", "http://127.0.0.1:8080/login"));
        exportMultiSheet(sheet1, sheet2);
    }
}
