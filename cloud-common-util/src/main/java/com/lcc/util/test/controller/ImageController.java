package com.lcc.util.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 刘灿灿
 */
@Controller
@Api(tags = "图片处理")
@RequestMapping("/image")
public class ImageController {
    @ApiOperation(value = "根据url 下载单个图片")
    @GetMapping("/down")
    public void download(@RequestParam String urlPath, @RequestParam String fileName,HttpServletResponse response) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlPath);
            URLConnection con = url.openConnection();
            inputStream = con.getInputStream();
            outputStream = response.getOutputStream();
            response.setContentType("application/x-download");

            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName +".jpg");
            IOUtils.copy(inputStream, outputStream);

            outputStream.flush();

        } catch (IOException e) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
        }
    }


    @ApiOperation(value = "打包下载图片")
    @GetMapping("/getZipImageFile")
    public void getZipImageFile(@RequestBody List<String> urlPath ,HttpServletResponse response) throws UnsupportedEncodingException {
        String zipName = URLEncoder.encode("桌台码.zip", "UTF-8");
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + zipName);
        response.setCharacterEncoding("utf-8");
        ZipOutputStream zipOut = null;

        try {
            zipOut = new ZipOutputStream(response.getOutputStream());
            for (String imageUrl : urlPath) {
                //桌台码名称
                String fileName =  randomString()+ ".jpg";
                zipOut.putNextEntry(new ZipEntry(fileName));
                //图片url
                URL url = new URL(imageUrl);
                InputStream in = new DataInputStream(url.openStream());

                byte[] buff = new byte[1024];
                int len;
                while ((len = in.read(buff)) != -1) {
                    zipOut.write(buff, 0, len);
                }
                zipOut.closeEntry();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipOut != null) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String randomString() {
        //将所有的大小写字母和0-9数字存入字符串中
        String str = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789";
        Random random = new Random();
        String strArray[] = new String[10];
        //生成10条长度为1-10的随机字符串

        StringBuffer stringBuffer = new StringBuffer();
        //确定字符串长度
        int stringLength = (int) (Math.random() * 10);
        for (int j = 0; j < stringLength; j++) {
            //先随机生成初始定义的字符串 str 的某个索引，以获取相应的字符
            int index = random.nextInt(str.length());
            char c = str.charAt(index);
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

}
