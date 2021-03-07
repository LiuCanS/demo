package com.lcc.util.test.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lcc.util.excel.EasyExcelUtil;
import com.lcc.util.excel.rowmodel.StoreTableRowModel;
import com.lcc.util.test.dto.StoreTableDto;
import com.lcc.util.test.dto.TableListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@Api(tags = "excel处理")
@RequestMapping("/excel")
public class ExcelController {

    @ApiOperation("批量导入桌台 -- 文件上传")
    @PostMapping("/upload")
    public void importCategory(MultipartFile file) {
        InputStream in = null;
        TableListDto tableListDto = null;
        try {
            in = file.getInputStream();
            List<Map<Integer, String>> mapsList = EasyExcelUtil.excelToData(in);
            int size = mapsList.size();
            if (size < 3) {
            }
            //整表信息
            tableListDto = new TableListDto();

            //表头信息  三行
            List<List<String>> excelHead = new ArrayList<>();
            List<String> rowMessage;
            for (int i = 0; i < 3; i++) {
                Map<Integer, String> map = mapsList.get(i);
                if (CollectionUtil.isNotEmpty(map)) {
                    rowMessage = new LinkedList<>();
                    for (int j = 0; j < 4; j++) {
                        rowMessage.add(map.get(j));
                    }
                    excelHead.add(rowMessage);
                }
            }

            //表信息
            tableListDto.setExcelHead(excelHead);
            List<StoreTableDto> storeTableDtoList = new LinkedList<>();
            StoreTableDto storeTableDto;
            int wrongCount = 0;

            for (int i = 3; i < size; i++) {
                Map<Integer, String> map = mapsList.get(i);
                String name = map.get(1);
                String seats = map.get(3);

                storeTableDto = new StoreTableDto();
                storeTableDto.setName(name);
                storeTableDto.setStoreTableAreaName(map.get(2));
                storeTableDto.setSeats(Integer.parseInt(seats));
                storeTableDtoList.add(storeTableDto);
            }

            tableListDto.setFailCount(wrongCount);
            tableListDto.setSuccessCount(storeTableDtoList.size());
            tableListDto.setStoreTableDtoList(storeTableDtoList);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ObjectUtil.isNotNull(in)) {
                try {
                    in.close();
                } catch (IOException e) {

                }
            }
        }

    }


    @ApiOperation("下载模板")
    @GetMapping("/template/download/{type}")
    /**
     * 下载模板
     * 根据type 确定不同模板的类型  对应不同的RowModel类
     */
    public void downloadImportTemplate(HttpServletResponse response, @PathVariable String type) throws IOException {
        OutputStream outputStream = null;
        ExcelWriter excelWriter = null;
        try {
            outputStream = response.getOutputStream();
            //添加响应头信息
            //设置类型
            response.setContentType("application/msexcel");
            //设置头
            response.setHeader("Pragma", "No-cache");
            //设置头
            response.setHeader("Cache-Control", "no-cache");
            //设置日期头
            response.setDateHeader("Expires", 0);
            response.setCharacterEncoding("UTF-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("模板", "UTF-8");
            excelWriter = EasyExcel.write(outputStream, StoreTableRowModel.class).build();
            fileName = URLEncoder.encode("桌台模板", "UTF-8");
            switch (type) {
                case "s":
                    //设置头信息
                    excelWriter = EasyExcel.write(outputStream, StoreTableRowModel.class).build();
                    fileName = URLEncoder.encode("桌台模板", "UTF-8");
                    break;
                default:
                    break;
            }

            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            WriteSheet writeSheet = EasyExcel.writerSheet(0).build();
            excelWriter.write(null, writeSheet);
            excelWriter.finish();
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ObjectUtil.isNotNull(excelWriter)) {
                excelWriter.finish();
            }
            if (ObjectUtil.isNotNull(outputStream)) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
