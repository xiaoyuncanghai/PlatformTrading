package com.pt.lib_common.http.download;


/**
 * Created by yangy on 2017/2/16.
 */
public interface EasyDownloadTaskListener {
	/**
	 * queue
	 * @param downloadTask
	 */
	void onQueue(EasyDownloadTask downloadTask);

	/**
	 * connecting
	 * @param downloadTask
     */
	void onConnecting(EasyDownloadTask downloadTask);

	/**
	 * downloading
	 * @param downloadTask
	 */
	void onDownloading(EasyDownloadTask downloadTask);

	/**
	 * pauseTask
	 * @param downloadTask
	 */
	void onPause(EasyDownloadTask downloadTask);

	/**
	 * cancel
	 * @param downloadTask
	 */
	void onCancel(EasyDownloadTask downloadTask);

	/**
	 * success
	 * @param downloadTask
	 */
	void onFinish(EasyDownloadTask downloadTask);

	/**
	 * failure
	 * @param downloadTask
	 */
	void onError(EasyDownloadTask downloadTask, int code);
}
