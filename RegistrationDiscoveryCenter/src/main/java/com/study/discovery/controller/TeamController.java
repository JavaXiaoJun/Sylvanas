package com.study.discovery.controller;

import com.newegg.ec.base.dao.mysql.IUserDao;
import com.newegg.ec.base.model.CommonResponse;
import com.newegg.ec.base.model.User;
import com.study.discovery.model.Team;
import com.study.discovery.service.ITeamService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jn50 on 2018/5/4.
 */
@Controller
@RequestMapping("/rest/*")
public class TeamController {

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IUserDao userDao;

    @RequestMapping(value="/saveTeam",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse saveTeam(@RequestBody Team team){
        teamService.addOrUpdate(team);
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult("");
        return cmmResponse;
    }

    @RequestMapping(value="/deleteTeam",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse deleteTeam(@RequestParam("id") String id){
        CommonResponse cmmResponse = new CommonResponse();
        teamService.deleteTeam(id);
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult("");
        return cmmResponse;
    }

    @RequestMapping(value="/getTeamList",method= RequestMethod.GET)
    @ResponseBody
    public CommonResponse getTeamList(){
        CommonResponse cmmResponse = new CommonResponse();
        cmmResponse.setStatus("1");
        cmmResponse.setInfo("");
        cmmResponse.setResult(teamService.getTeamList());
        return cmmResponse;
    }

    @RequestMapping(value="/getUserList",method= RequestMethod.POST)
    @ResponseBody
    public CommonResponse getUserList(String teamId){
        CommonResponse response = new CommonResponse();

        List<User> userListByTeamId = teamService.getUserListByTeamId(teamId);
        List<String> userIdList = null;
        if (userListByTeamId != null && !userListByTeamId.isEmpty()) {
            userIdList = new ArrayList<>();
            for (User user : userListByTeamId) {
                userIdList.add(user.getId());
            }
        }

        List<User> allUser = userDao.getAllUser();
        response.setStatus("1");
        if (userIdList == null) {
            response.setResult(allUser);
            return response;
        }

        List<JSONObject> resultList = new ArrayList<>();
        for (User user : allUser) {
            JSONObject jsonObject = JSONObject.fromObject(user);
            if (userIdList.contains(user.getId())) {
                jsonObject.put("inTeam", 1);
            }
            resultList.add(jsonObject);
        }
        response.setResult(resultList);
        return response;
    }

    @RequestMapping(value = "updateUsersOfTeam", method = {RequestMethod.POST})
    @ResponseBody
    public CommonResponse updateUsersOfTeam( String teamId, String userIds) {
        CommonResponse response = new CommonResponse();
        if (StringUtils.isEmpty(teamId)) {
            return response;
        }
        List<String> userIdList = null;
        if (!StringUtils.isEmpty(userIds)) {
            String[] split = userIds.split(",");
            userIdList = Arrays.asList(split);
        }
        teamService.updateUsersOfTeam(teamId, userIdList);
        response.setStatus("1");
        return response;
    }
}
