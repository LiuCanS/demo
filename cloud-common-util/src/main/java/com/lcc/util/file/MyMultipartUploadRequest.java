package com.lcc.util.file;

import com.qcloud.cos.model.AbortMultipartUploadRequest;

public class MyMultipartUploadRequest  extends AbortMultipartUploadRequest {
    public MyMultipartUploadRequest(String bucketName, String key, String uploadId) {
        super(bucketName, key, uploadId);
    }
}
