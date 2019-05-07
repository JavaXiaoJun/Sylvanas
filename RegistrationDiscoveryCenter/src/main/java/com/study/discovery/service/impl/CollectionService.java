package com.study.discovery.service.impl;


import com.study.discovery.dao.mysql.IAppDao;
import com.study.discovery.dao.mysql.ICollectionDao;
import com.study.discovery.model.App;
import com.study.discovery.model.Collection;
import com.study.discovery.service.ICollectionService;
import com.study.discovery.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Jay.H.Zou
 * @date 2018/8/1
 */
@Service
public class CollectionService implements ICollectionService {

    @Autowired
    private ICollectionDao collectionDao;

    @Autowired
    private IAppDao appDao;

    @Override
    public Collection addCollection(String appName) {
        Collection result = null;
        if (appName != null && !"".equals(appName)) {
            Collection collection = collectionDao.selectCollection(appName);
            if (collection != null) {
                return result;
            }
        }
        int row = 0;
        Collection collection = new Collection();
        if (!StringUtils.isEmpty(appName)) {
            App app = appDao.getTeamIdByAppName(appName);
            String teamId = app.getTeamId();
            if (!StringUtils.isEmpty(teamId)) {
                collection.setCollectionId(CommonUtils.getUUID());
                collection.setAppName(appName);
                collection.setTeamId(teamId);
                collection.setCollected(1);
                row = collectionDao.addCollection(collection);
            }
        }
        if (row > 0) {
            result = collection;
        }
        return result;
    }

    @Override
    public int deleteCollection(String collectionId) {
        return collectionDao.deleteCollection(collectionId);
    }
}
