package com.yunu.dao;

import com.alibaba.fastjson.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IUserTDAO {

	@Autowired
	public ITest test;
	
    @Autowired
    public MongoTemplate mongoTemplate;

    @Cacheable(value="IUserT",keyGenerator = "KeyGenerator")
    public JSONObject findUserTById(String pid){
        System.out.println("+++++++++++++++++");
       JSONObject obj = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(new ObjectId(pid))),JSONObject.class);
       return obj;
    }
    

	public JSONObject findTest() {
		return test.findUser();
	}
}
