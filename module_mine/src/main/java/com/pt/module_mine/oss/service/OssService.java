package com.pt.module_mine.oss.service;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.OSSRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.apkfuns.logutils.LogUtils;
import com.pt.lib_common.rxEasyhttp.cache.stategy.OnlyCacheStrategy;

import java.io.File;
import java.util.HashMap;

/**
 * Created by mOss on 2015/12/7 0007.
 * 支持普通上传，普通下载
 */
public class OssService {

    public OSS mOss;
    private String mBucket;
    private String mCallbackAddress;
    private static String mResumableObjectKey = "resumableObject";

    public OssService(OSS oss, String bucket) {
        this.mOss = oss;
        this.mBucket = bucket;
    }

    public void setBucketName(String bucket) {
        this.mBucket = bucket;
    }

    public void initOss(OSS _oss) {
        this.mOss = _oss;
    }

    public void setCallbackAddress(String callbackAddress) {
        this.mCallbackAddress = callbackAddress;
    }

    /**
     *
     * @param object: 上传的名称
     * @param localFile: 本地文件
     */
    public void asyncPutImage(String object, String dirtory, String localFile, int position,
                              final OnUploadListener listener) {
        final long upload_start = System.currentTimeMillis();
        OSSLog.logDebug("Lion, upload start");

        if (object.equals("")) {
            Log.w("AsyncPutImage", "ObjectNull");
            return;
        }

        File file = new File(localFile);
        if (!file.exists()) {
            Log.w("AsyncPutImage", "FileNotExist");
            Log.w("LocalFile", localFile);
            return;
        }

        // 构造上传请求
        OSSLog.logDebug("Lion,  create PutObjectRequest ");
        PutObjectRequest put = new PutObjectRequest(mBucket, dirtory + object, localFile);
        put.setCRC64(OSSRequest.CRC64Config.YES);
        if (mCallbackAddress != null) {
            // 传入对应的上传回调参数，这里默认使用OSS提供的公共测试回调服务器地址
            put.setCallbackParam(new HashMap<String, String>() {
                {
                    put("callbackUrl", mCallbackAddress);
                    //callbackBody可以自定义传入的信息
                    put("callbackBody", "filename=${object}");
                }
            });
        }

        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //OSSLog.logDebug("PutObject, currentSize: " + currentSize + " totalSize: " + totalSize);
                int progress = (int) (100 * currentSize / totalSize);
               // OSSLog.logDebug("Lion, progress = " + progress);
                /*mDisplayer.updateProgress(progress);
                mDisplayer.displayInfo("上传进度: " + String.valueOf(progress) + "%");*/
                if (listener == null) {
                    return;
                }
                listener.onProgress(position, currentSize, totalSize);
            }
        });

        OSSLog.logDebug(" asyncPutObject ");
        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                OSSLog.logDebug("Lion, PutObject, UploadSuccess");

                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

                long upload_end = System.currentTimeMillis();
                OSSLog.logDebug("upload cost: " + (upload_end - upload_start) / 1000f);
                /*mDisplayer.uploadComplete();
                mDisplayer.displayInfo("Bucket: " + mBucket
                        + "\nObject: " + request.getObjectKey()
                        + "\nETag: " + result.getETag()
                        + "\nRequestId: " + result.getRequestId()
                        + "\nCallback: " + result.getServerCallbackReturnBody());*/
                LogUtils.d("complete");
                if (listener == null) {
                    return;
                }
                String imageUrl = request.getObjectKey();
                listener.onSuccess(position, imageUrl);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                String info = "";
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                    info = clientExcepion.toString();
                }
                if (serviceException != null) {
                    // 服务异常
                    OSSLog.logDebug("Lion, ErrorCode", serviceException.getErrorCode());
                    OSSLog.logDebug("Lion,RequestId", serviceException.getRequestId());
                    OSSLog.logDebug("Lion,HostId", serviceException.getHostId());
                    OSSLog.logDebug("Lion,RawMessage", serviceException.getRawMessage());
                    info = serviceException.toString();
                }
                /*mDisplayer.uploadFail(info);
                mDisplayer.displayInfo(info);*/
                OSSLog.logDebug("Lion, info = "+info);
                OSSLog.logDebug("Lion, PutObject, Uploadfailed");
                if (listener == null) {
                    return;
                }
                listener.onFailure(position);
            }
        });
    }

    public interface OnUploadListener {
        /**
         * 上传的进度
         */
        void onProgress(int position, long currentSize, long totalSize);

        /**
         * 成功上传
         */
        void onSuccess(int position, String imageUrl);

        /**
         * 上传失败
         */
        void onFailure(int position);
    }
}
