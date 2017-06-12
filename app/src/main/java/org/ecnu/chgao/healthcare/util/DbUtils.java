package org.ecnu.chgao.healthcare.util;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dylan on 2016/1/31.
 */
public class DbUtils {

    public static String DB_NAME;
    public static LiteOrm liteOrm;
    public static Map<String, LiteOrm> dbMap = new HashMap<>();

    public static void createDb(Context _activity, String DB_NAME) {
        DB_NAME = DB_NAME + ".db";
        if (dbMap.get(DB_NAME) == null) {
            liteOrm = LiteOrm.newCascadeInstance(_activity, DB_NAME);
            liteOrm.setDebugged(true);
            dbMap.put(DB_NAME, liteOrm);
        }
    }


    public static LiteOrm getLiteOrm(String DB_NAME) {
        if (forLastDb(DB_NAME))
            return liteOrm;
        return dbMap.get(DB_NAME);
    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t, String DB_NAME) {
        if (forLastDb(DB_NAME)) {
            liteOrm.save(t);
            return;
        }
        dbMap.get(DB_NAME).save(t);

    }

    private static boolean forLastDb(String DB_NAME) {
        return DB_NAME == null || "".equals(DB_NAME);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public static <T> void insertAll(List<T> list, String DB_NAME) {
        if (forLastDb(DB_NAME)) {
            liteOrm.save(list);
            return;
        }
        dbMap.get(DB_NAME).save(list);
    }

    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public static <T> List<T> getQueryAll(Class<T> cla, String DB_NAME) {
        if (forLastDb(DB_NAME))
            return liteOrm.query(cla);
        return dbMap.get(DB_NAME).query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, String[] value, String DB_Name) {
        if (forLastDb(DB_Name))
            return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value));
        return dbMap.get(DB_Name).<T>query(new QueryBuilder(cla).where(field + "=?", value));
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public static <T> List<T> getQueryByWhereLength(Class<T> cla, String field, String[] value, int start, int length, String DB_NAME) {
        if (forLastDb(DB_NAME))
            return liteOrm.<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
        return dbMap.get(DB_NAME).<T>query(new QueryBuilder(cla).where(field + "=?", value).limit(start, length));
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     * @param cla
     * @param field
     * @param value
     */
//        public static <T> void deleteWhere(Class<T> cla,String field,String [] value){
//            liteOrm.delete(cla, WhereBuilder.create().where(field + "=?", value));
//        }

    /**
     * 删除所有
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla, String DB_NAME) {
        if (forLastDb(DB_NAME))
        liteOrm.deleteAll(cla);
//        dbMap.get(DB_NAME).
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     *
     * @param cla
     * @param field
     * @param value
     */
    public static <T> int deleteWhere(Class<T> cla, String field, String[] value) {
        return liteOrm.delete(cla, new WhereBuilder(cla).where(field + "!=?", value));
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public static <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }


    public static <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }

    public static void closeDb() {
        liteOrm.close();
    }

}
