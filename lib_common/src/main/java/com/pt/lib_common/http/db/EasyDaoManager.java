package com.pt.lib_common.http.db;


import com.pt.lib_common.http.download.EasyDownloadManager;
import com.pt.lib_common.http.download.EasyTaskEntity;
import com.pt.lib_common.http.download.EasyTaskEntityDao;

import java.util.List;

public class EasyDaoManager {
	private static EasyDaoManager mInstance;

	private EasyDaoManager() {
	}

	public static EasyDaoManager instance() {
		synchronized (EasyDaoManager.class) {
			if (mInstance == null) {
				mInstance = new EasyDaoManager();
			}
		}
		return mInstance;
	}

	public void insertOrReplace(EasyTaskEntity entity) {
		EasyDownloadManager.getInstance().getDaoSession().insertOrReplace(entity);
	}

	public EasyTaskEntity queryWithId(String taskId) {
		return EasyDownloadManager
				.getInstance()
				.getDaoSession()
				.getEasyTaskEntityDao()
				.queryBuilder()
				.where(EasyTaskEntityDao.Properties.TaskId.eq(taskId))
				.unique();
	}

	public List<EasyTaskEntity> queryAll() {
		return EasyDownloadManager
				.getInstance()
				.getDaoSession()
				.getEasyTaskEntityDao()
				.loadAll();
	}

	public void update(EasyTaskEntity entity) {
		EasyDownloadManager
			.getInstance()
			.getDaoSession()
			.getEasyTaskEntityDao()
			.update(entity);
	}

	public void delete(EasyTaskEntity entity) {
		EasyDownloadManager
			.getInstance()
			.getDaoSession()
			.getEasyTaskEntityDao()
			.delete(entity);
	}
}
