package com.study.discovery.controller;

import com.newegg.ec.base.model.CommonResponse;
import com.study.discovery.model.Collection;
import com.study.discovery.service.ICollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jay.H.Zou
 * @date 2018/8/1
 */
@Controller
@RequestMapping("/rest/*")
public class CollectionController {
    @Autowired
    private ICollectionService collectionService;

    @RequestMapping(value = "/addCollection", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public CommonResponse addCollection(@RequestParam("appName") String appName, HttpServletRequest request) {
        CommonResponse response = new CommonResponse();
        Collection result = collectionService.addCollection(appName);
        if (result != null) {
            response.setStatus("1");
            response.setInfo(result.getCollectionId());
        } else  {
            response.setStatus("0");
        }
        return response;
    }

    @RequestMapping(value = "/deleteCollection", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public CommonResponse deleteCollection(@RequestParam("collectionId") String collectionId) {
        CommonResponse response = new CommonResponse();
        int row = collectionService.deleteCollection(collectionId);
        response.setStatus(String.valueOf(row));
        return response;
    }
}
