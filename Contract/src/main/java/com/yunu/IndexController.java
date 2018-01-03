package com.yunu;

import com.alibaba.fastjson.JSONObject;
import com.yunu.dao.IUserTDAO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public IUserTDAO iUserTDAO;

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/index")
    public String index_default(Model model){
        return "index";
    }

    @RequestMapping(value = "/success")
    public String success_page(Model model){
        return "success";
    }
    @RequestMapping(value = "/failure")
    public String failure_page(Model model){
        model.addAttribute("msg", UUID.randomUUID().toString());
        model.addAttribute("obj",new JSONObject(){{
            this.put("date",new Date());
            this.put("random", Math.random()*1000+1);
        }});
        return "failure";
    }
    @RequestMapping(value = "/login")
    public String login_page(HttpServletRequest request, HttpServletResponse response,Model model){
        request.getSession().setAttribute("UUID",UUID.randomUUID().toString());
//        request.setAttribute("htmlcontent","<input type='button' value='button1234567' />");
//        request.setAttribute("htmlcontent1","");
//        request.setAttribute("htmlcontent2","<input type='button' value='htmlcontent2' onclick='location=\"/success\"'>");
        model.addAttribute("htmlcontent","<input type='button' value='button1234567' />");
        model.addAttribute("htmlcontent1","");
        model.addAttribute("htmlcontent2","<input type='button' value='htmlcontent2' onclick='location=\"/success\"'>");

        return "login";
    }
    @RequestMapping(value = "/saveObject")
    @ResponseBody
    public JSONObject saveJSONObjectToMongo(@RequestBody JSONObject obj)
    {
        obj.put("_id",new ObjectId());
        mongoTemplate.save(obj);
        return obj;
    }

    @RequestMapping(value="/findjsonobject")
    @ResponseBody
    public JSONObject findJSONObject(@RequestParam(value = "pid") String pid){
        return iUserTDAO.findUserTById(pid);
    }
    
    @RequestMapping(value="/findTest")
    @ResponseBody
    public JSONObject findTest(){
        return iUserTDAO.findTest();
    }

}
