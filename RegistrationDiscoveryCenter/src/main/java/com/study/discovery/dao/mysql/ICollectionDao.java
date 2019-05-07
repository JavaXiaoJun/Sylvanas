package com.study.discovery.dao.mysql;


import com.study.discovery.model.Collection;

/**
 * @author Jay.H.Zou
 * @date 2018/8/1
 */
public interface ICollectionDao {

    Collection selectCollection(String appName);

    int addCollection(Collection collection);

    int deleteCollection(String collectionId);
}
