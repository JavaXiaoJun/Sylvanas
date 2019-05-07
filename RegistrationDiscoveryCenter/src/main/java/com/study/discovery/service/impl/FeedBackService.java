package com.study.discovery.service.impl;

import com.newegg.ec.base.util.UUIDUtil;
import com.study.discovery.dao.mysql.IFeedBackDao;
import com.study.discovery.model.FeedBackMsg;
import com.study.discovery.model.PageQuery;
import com.study.discovery.service.IFeedbackService;
import com.study.discovery.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jn50 on 2018/2/12.
 */
@Service
public class FeedBackService implements IFeedbackService {

    @Autowired
    private IFeedBackDao feedBackDao;

    @Override
    public void addFeedback(FeedBackMsg feedBackMsg) {
       if(feedBackMsg != null){
           feedBackMsg.setUpdateTime(CommonUtils.getNowFormatTime());
           feedBackMsg.setId(UUIDUtil.getUUID());
           feedBackDao.addFeedBackMsg(feedBackMsg);
       }
    }

    @Override
    public void updateFeedback(FeedBackMsg feedBackMsg) {
        if (feedBackMsg != null) {
            feedBackMsg.setUpdateTime(CommonUtils.getNowFormatTime());
            feedBackDao.updateFeedBackMsg(feedBackMsg);
        }
    }

    @Override
    public void deleteFeedBack(String id) {
        feedBackDao.deleteFeedBackMsg(id);
    }

    @Override
    public int getFeedBackMsgCount() {
        return feedBackDao.getFeedBackMsgCount();
    }

    @Override
    public List<FeedBackMsg> getFeedBackList(PageQuery pageQuery) {
        return feedBackDao.getFeedBackMsgList(pageQuery);
    }

}
