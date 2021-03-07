package com.lcc.util.test.controller;

import cn.hutool.core.io.FileTypeUtil;
import com.lcc.util.file.QCloudProperties;
import com.lcc.util.file.SpringContextHolder;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件上传下载
 *
 * @author :    zhangjun
 * @version :   1.0
 * @date :      Created in  2020/10/18 15:55:39
 */
@Controller
@Api(tags = "文件")
@RequestMapping("/file")
public class FileController  {

    @Resource
    private QCloudProperties qcloudProperties;

    /**
     * 上传图片到cos
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ResponseBody
    public void uploadFile(MultipartFile file) {
        try {
            fileIsToBigThrowException(file);
            upload(file);
        } catch (IOException e) {

        }
    }


    public String upload(MultipartFile file) throws IOException {
        String fileType = FileTypeUtil.getType(file.getInputStream());
        return upload(file, getKey(fileType));
    }


    public String getKey(String fileType){
        return   "/"
                + SpringContextHolder.getActiveProfile()
                + "/"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM/dd"))
                + "/"
                + UUID.randomUUID().toString().replace("-", "")
                + "."
                + fileType;
    }


    public String upload(MultipartFile file, String key) throws IOException {
        return  upload(file.getInputStream(), FileTypeUtil.getType(file.getInputStream()),key);
    }

    private void fileIsToBigThrowException(MultipartFile file) {
        // 限制上传大小 最大15M
        if (file.getSize() > 15 * 1024 * 1024) {

        }
    }

    public String upload(InputStream inputStream, String fileType, String key) throws IOException {

//        COSClient cosClient = null;
//        try {
//
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//
//            // 默认下载时根据cos路径key的后缀返回响应的content-type, 上传时设置content-type会覆盖默认值
//            objectMetadata.setContentType(fileType);
//
//            // 从输入流上传必须制定content-length, 否则http客户端可能会缓存所有数据，存在内存OOM的情况
//            objectMetadata.setContentLength(Integer.valueOf(inputStream.available()).longValue());
//
//            PutObjectRequest putObjectRequest = new PutObjectRequest(qcloudProperties.getCos().getBucketName(), key, inputStream, objectMetadata);
//            COSCredentials cred = new BasicCOSCredentials(qcloudProperties.getCos().getSecretId(), qcloudProperties.getCos().getSecretKey());
//            // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//            Region region = new Region(qcloudProperties.getCos().getRegionName());
//            ClientConfig clientConfig = new ClientConfig(region);
//            cosClient = new COSClient(cred, clientConfig);
//
//            cosClient.putObject(putObjectRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            if (cosClient != null) {
//                cosClient.shutdown();
//            }
//        }
//
//        // location
//        return "https://"
//                + qcloudProperties.getCos().getBucketName()
//                + ".cos."
//                + qcloudProperties.getCos().getRegionName()
//                + ".myqcloud.com"
//                + key;

        return null;
    }


}
