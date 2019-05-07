package com.study.discovery.aop;


import com.study.discovery.dao.mysql.IActivityDao;
import com.study.discovery.dao.mysql.ITeamDao;
import com.study.discovery.model.Activity;
import com.study.discovery.model.App;
import com.study.discovery.model.Team;
import com.study.discovery.util.DateUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * Created by lf52 on 2018/5/23.
 */
@Aspect
@Component
public class LogInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    public final static String SESSION_USER_KEY = "name";

    public final static String[] filterUrls = {"addApp","dropApp","appShutDown","saveTeam","deleteTeam"};

    @Autowired
    IActivityDao activityDao;

    @Autowired
    ITeamDao teamDao;

    @Pointcut("execution(public * com.newegg.spring.eureka.manager.controller.*.*(..))")
    public void optionLog() {
    }

    @Before("optionLog()")
    public void doBefore(JoinPoint joinPoint) throws IOException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String url = request.getRequestURL().toString();
        String path =  StringUtils.substringAfterLast(url, "/");
        String userName =  (String)session.getAttribute(SESSION_USER_KEY);
        String queryString = request.getQueryString();

        for(String filterUrl : filterUrls){

            if(path.equals(filterUrl)){

                Object[] params = joinPoint.getArgs();
                if(queryString == null || "".equals(queryString)){
                    if( null != params && params.length != 0 ){
                        Object param = params[0];
                        queryString =  getQueryString(param);
                    }
                }

                Activity activity = new Activity();
                activity.setUsername(userName);
                activity.setDescription(generateDescription(filterUrl,params[0], queryString));
                activity.setTime(DateUtils.formatDate(new Date()));
                activityDao.addActivity(activity);
                break;
            }
        }

    }

    private String getQueryString(Object param) {
        String paramkey = param.toString();
        if(paramkey.contains("Team")){
            return ((Team)param).getName();
        }else if(paramkey.contains("App")){
            return ((App)param).getName();
        }else{
            return JSONObject.fromObject(paramkey).getString("name");
        }
    }

    @AfterReturning("optionLog()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }

    /**
     * 生成activity的描述
     * @param path
     * @param params
     * @param name  @return
     */
    private String generateDescription(String path, Object params, String name){

        String result = "";

        if(name != null && name.contains("=")){
            name = name.split("\\=")[1];
        }

        switch(path){

            case "addApp":
                result = "Add A New Service : " + name;
                break;
            case "dropApp":
                result = "Delete The Service : " + name;
                break;
            case "appShutDown":
                result = "Stop The Service : " + name;
                break;
            case "saveTeam":
                String id =  ((Team)params).getId();
                if("".equals(id)){
                    result = "Add A New Team : " + name;
                }else{
                    result = "Update The Team : " + name;
                }
                break;
            case "deleteTeam":
                Team team = teamDao.getTeam(name);
                result = "Delete The Team : " + team.getName();
                break;
        }

        return result;
    }

}
