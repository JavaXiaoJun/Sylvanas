package com.study.discovery.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.newegg.ec.base.model.CommonResponse;
import com.study.discovery.dao.mysql.IActivityDao;
import com.study.discovery.model.Activity;
import com.study.discovery.model.App;
import com.study.discovery.service.IManagerService;
import com.study.discovery.service.INewsService;
import com.study.discovery.service.ITeamService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jn50 on 2018/1/31.
 */
@Controller
@RequestMapping("/rest/*")
public class RestController {

    @Autowired
    IManagerService managerService;

    @Autowired
    ITeamService teamService;

    @Autowired
    IActivityDao activityDao;

    @Autowired
    private INewsService newsService;

    private EurekaServerContext getServerContext() {
        return EurekaServerContextHolder.getInstance().getServerContext();
    }

    @RequestMapping(value="/getRegistryApps",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getRegistryApps(){
        List<Application> sortedApplications = getServerContext().getRegistry().getSortedApplications();
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(sortedApplications);
        return cmmResponse;
    }

    @RequestMapping(value="/getApps",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getApps(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = session.getAttribute("id").toString();
        List<App> appList = managerService.getApps(userId);
        //获取所有app下可用的节点个数
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        if (appList != null && appList.size() > 0) {
            setAvailableNodes(appList);
            cmmResponse.setResult(appList);
        }
        return cmmResponse;
    }

    @RequestMapping(value="/getAppCollectedList",method= {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public CommonResponse getAppCollectedList(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = session.getAttribute("id").toString();
        List<App> appList = managerService.getAppCollectedList(userId);
        CommonResponse cmmResponse = new CommonResponse();

        cmmResponse.setStatus("1");
        if (appList != null && appList.size() > 0) {
            setAvailableNodes(appList);
            cmmResponse.setResult(appList);
        }
        return cmmResponse;
    }

    private void setAvailableNodes(List<App> appList) {
        appList.forEach(app ->{
            Application application = getServerContext().getRegistry().getApplication(app.getName());
            if (application != null){
                List<InstanceInfo> nodeList = application.getInstancesAsIsFromEureka();
                app.setAvailableNodes(nodeList.size());
            }else{
                app.setAvailableNodes(0);
            }

        });
    }

    @RequestMapping(value="/addApp",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse addApp(@RequestBody App app){
        CommonResponse cmmResponse = new CommonResponse();
        managerService.addApp(app);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult("");
        return cmmResponse;
    }

    @RequestMapping(value="/dropApp",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse dropApp(@RequestParam("appName") String appName){
        CommonResponse cmmResponse = new CommonResponse();
        managerService.dropApp(appName);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult("");
        return cmmResponse;
    }

    @RequestMapping(value="/getApp",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getApp(@RequestParam("appName") String appName, HttpServletRequest request){
        HttpSession session = request.getSession();
        CommonResponse cmmResponse = new CommonResponse();
        App app = managerService.getApp(appName);
        cmmResponse.setStatus("1");
        cmmResponse.setResult(app);
        return cmmResponse;
    }

    @RequestMapping(value="/getAppsByName",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getAppsByName(@RequestParam("appName") String appName){
        Application application = getServerContext().getRegistry().getApplication(appName);
        CommonResponse cmmResponse = new CommonResponse();
        if(application != null){
            cmmResponse.setStatus("1");
            cmmResponse.setInfo("");
            cmmResponse.setResult(application);
        } else {
            cmmResponse.setStatus("0");
        }
        return cmmResponse;
    }

    @RequestMapping(value="/appShutDown",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse appShutDown(@RequestBody JSONObject json){
        CommonResponse cmmResponse = new CommonResponse();
        managerService.appShutDown(json.getString("theUrl"));
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult("");
        return cmmResponse;
    }

    @RequestMapping(value="/homePage",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse homePage(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<App> appList = managerService.getApps(null);

        CommonResponse cmmResponse = new CommonResponse();
        if (appList != null && appList.size() > 0) {
            int apps = appList.size();
            int teams = teamService.getTeamList().size();
            int instances = 0;
            //获取所有app下可用的节点个数
            for(App app : appList){
                Application application = getServerContext().getRegistry().getApplication(app.getName());
                if (application != null){
                    List<InstanceInfo> nodeList = application.getInstancesAsIsFromEureka();
                    instances = instances + nodeList.size();
                }
            }
            Map<String,Integer> result = new HashMap();
            result.put("apps",apps);
            result.put("teams",teams);
            result.put("instances",instances);
            result.put("news", newsService.getNewsCount());
            cmmResponse.setResult(result);
        }
        cmmResponse.setStatus("1");
        return cmmResponse;
    }

    @RequestMapping(value="/getActivity",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getActivity(){

        CommonResponse cmmResponse = new CommonResponse();
        List<Activity> list = activityDao.getActivityList();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(list);
        return cmmResponse;
    }

}
