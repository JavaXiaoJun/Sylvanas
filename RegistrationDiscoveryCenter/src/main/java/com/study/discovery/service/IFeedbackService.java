package com.study.discovery.service;


import com.study.discovery.model.FeedBackMsg;
import com.study.discovery.model.PageQuery;

import java.util.List;

/**
 * Created by jn50 on 2018/2/12.
 */
public interface IFeedbackService {

    void addFeedback(FeedBackMsg feedBackMsg);

    void deleteFeedBack(String id);

    int getFeedBackMsgCount();

    List<FeedBackMsg> getFeedBackList(PageQuery pageQuery);

    void updateFeedback(FeedBackMsg feedBackMsg);
}
