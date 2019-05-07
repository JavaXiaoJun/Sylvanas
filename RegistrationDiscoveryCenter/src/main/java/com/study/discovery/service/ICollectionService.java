package com.study.discovery.service;

import com.study.discovery.model.Collection;

/**
 * @author Jay.H.Zou
 * @date 2018/8/1
 */
public interface ICollectionService {

    Collection addCollection(String appName);

    int deleteCollection(String collectionId);
}
