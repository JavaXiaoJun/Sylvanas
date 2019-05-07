package com.study.discovery.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.study.discovery.model.FeedBackMsg;
import com.study.discovery.model.PageQuery;
import com.study.discovery.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jn50 on 2018/2/12.
 */
@Controller
@RequestMapping("/rest/*")
public class FeedBackController {

    @Autowired
    private IFeedbackService feedBackService;

    @RequestMapping(value="/addFeedBack",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse saveFeedBack(@RequestBody FeedBackMsg feedBackMsg){
        feedBackService.addFeedback(feedBackMsg);
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        return cmmResponse;
    }

    @RequestMapping(value="/updateFeedBack",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse updateFeedBack(@RequestBody FeedBackMsg feedBackMsg){
        feedBackService.updateFeedback(feedBackMsg);
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult("");
        return cmmResponse;
    }



    @RequestMapping(value="/deleteFeedBack",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteFeedBack(@RequestParam("id") String id){
        CommonResponse cmmResponse = new CommonResponse();
        feedBackService.deleteFeedBack(id);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult("");
        return cmmResponse;
    }

    @RequestMapping(value="/getFeedBackList",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse getFeedBackList(@RequestBody PageQuery pageQuery){
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        Map<String,Object> result = new HashMap();
        result.put("pageData",feedBackService.getFeedBackList(pageQuery));
        result.put("totalCount",feedBackService.getFeedBackMsgCount());
        cmmResponse.setResult(result);
        return cmmResponse;
    }

}
