package com.xiangri.dongdong.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xiangri.dongdong.entity.HistoryEntity;
import com.xiangri.dongdong.greendao.DaoMaster;
import com.xiangri.dongdong.greendao.DaoSession;
import com.xiangri.dongdong.greendao.HistoryEntityDao;

import java.util.List;

public class SqlUtil {
    private static SqlUtil sqlUtil;
    private HistoryEntityDao historyEntityDao;


    private SqlUtil() {

    }

    public static SqlUtil getInstens() {
        if (sqlUtil == null) {
            synchronized (SqlUtil.class) {
                sqlUtil = new SqlUtil();
            }
        }
        return sqlUtil;
    }

    public void init(Context context, String dbName) {
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoSession daoSession = new DaoMaster(db).newSession();
        historyEntityDao = daoSession.getHistoryEntityDao();
    }

    public void insert(String type, String data) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setTpye(type);
        historyEntity.setHistory(data);
        historyEntityDao.insert(historyEntity);
    }

    public void deleteAll() {
        historyEntityDao.deleteAll();
    }

    public  List<HistoryEntity> queryAll(){
       return historyEntityDao.loadAll();
    }

    public  HistoryEntity queryByType(String type){
        List<HistoryEntity> historyEntities = historyEntityDao.loadAll();
        for (int i = 0 ; i < historyEntityDao.loadAll().size() ; i ++){
            if (type.equals(historyEntities.get(i).getTpye())){
                return historyEntities.get(i);
            }
        }
        return null;
    }

    public void deleteByKey(Long key){
        historyEntityDao.deleteByKey(key);
    }

}