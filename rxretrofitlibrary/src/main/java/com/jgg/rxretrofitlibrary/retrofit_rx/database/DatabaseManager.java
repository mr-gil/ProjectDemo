package com.jgg.rxretrofitlibrary.retrofit_rx.database;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.jgg.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;

import java.util.List;
/**
 * Created by Administrator on 2017/4/13 0013.
 */

public class DatabaseManager {
    private static LiteOrm liteOrm;
    private static DatabaseManager ourInstance = null;

    private DatabaseManager() {
        DataBaseConfig config = new DataBaseConfig(RxRetrofitApp.getApplication(), "game.db");
        config.debugged = true; // open the log
        config.dbVersion = 1; // set database version
        config.onUpdateListener = null; // set database update listener
        liteOrm = LiteOrm.newSingleInstance(config);
        liteOrm.setDebugged(true);
    }


    public synchronized static DatabaseManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DatabaseManager();
        }
        return ourInstance;
    }

    /**
     * 插入一条记录
     * @param t
     */
    public <T> long insert(T t) {
        return liteOrm.save(t);
    }

    /**
     * 仅在以存在时更新
     * @param t
     */
    public <T> long update(T t) {
        return liteOrm.update(t, ConflictAlgorithm.Replace);
    }
    /**
     * 插入所有记录
     * @param list
     */
    public <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }

    /**
     * 查询所有
     * @param cla
     * @return
     */
    public <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 根据uid查找
     * @param cla
     * @return
     */
    public <T> T getQueryByUid(Class<T> cla, long uid) {

        return liteOrm.queryById(uid,cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public <T> List<T> getQueryByWhere(Class<T> cla, String field, String value) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", new Object[]{value}));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String value, int start, int length) {
        return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", new Object[]{value}).limit(start, length));
    }

    /**
     * 删除一个数据
     * @param t
     * @param <T>
     */
    public <T> void delete( T t){
        liteOrm.delete( t ) ;
    }

    /**
     * 删除一个表
     * @param cla
     * @param <T>
     */
    public <T> void delete( Class<T> cla ){
        liteOrm.delete( cla ) ;
    }

    /**
     * 删除集合中的数据
     * @param list
     * @param <T>
     */
    public <T> void deleteList( List<T> list ){
        liteOrm.delete( list ) ;
    }

    /**
     * 删除数据库
     */
    public void deleteDatabase(){
        liteOrm.deleteDatabase() ;
    }

}
