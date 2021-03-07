package com.lcc.util.file;

import com.qcloud.cos.model.AbortMultipartUploadRequest;
import org.springframework.stereotype.Component;

/**
 * @author 刘灿灿
 */
@Component
public class QCloudProperties {
    public AbortMultipartUploadRequest getCos() {
        String bucketName = "1";
        String key = "2";
        String uploadId = "3";
        return new MyMultipartUploadRequest(bucketName, key, uploadId);
    }
}
