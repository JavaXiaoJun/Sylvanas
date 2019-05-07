package com.study.discovery.dao.mysql;

import com.study.discovery.model.FeedBackMsg;
import com.study.discovery.model.PageQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jn50 on 2018/2/12.
 */
@Repository
public interface IFeedBackDao {

    public void addFeedBackMsg(FeedBackMsg feedBackMsg);

    public void  updateFeedBackMsg(FeedBackMsg feedBackMsg);

    public void deleteFeedBackMsg(String id);

    public Integer getFeedBackMsgCount();

    public List<FeedBackMsg> getFeedBackMsgList(PageQuery pageQuery);
}
