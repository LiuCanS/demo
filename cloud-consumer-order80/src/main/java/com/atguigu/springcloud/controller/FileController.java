package com.atguigu.springcloud.controller;


import com.alibaba.excel.EasyExcel;
import com.atguigu.excel.DemoData;
import com.atguigu.excel.ExcelListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘灿灿
 */
@Slf4j
@RestController("/file")
@Api(value = "文件上传")
public class FileController {


    @ApiOperation(value="文件上传")
    @PostMapping("/upload")
    public void fileUpload(MultipartFile file){

        System.out.println("this");
        System.out.println(file);
    }


    @ApiOperation(value="文件上传")
    @PostMapping("/download")
    public void download(MultipartFile file){
        //实现excel写的操作
        //1 设置写入文件夹地址和excel文件名称
        String filename = "C:\\Users\\aus\\write.xlsx";
        // 2 调用easyexcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());

        System.out.println("this");
        System.out.println(file);
    }


        @ApiOperation(value="文件上传")
        @PostMapping("/upload/excel")
        public void uploadExcel(MultipartFile file){

        //实现excel读操作
//            String filename = "C:\\Users\\aus\\write.xlsx";
//            EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

            try{
                InputStream in = file.getInputStream();
                EasyExcel.read(in,DemoData.class,new ExcelListener());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    //创建方法返回list集合
    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }

}
