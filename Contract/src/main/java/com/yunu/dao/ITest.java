package com.yunu.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
@Repository
@Mapper
public interface ITest {
	
	
	@Select("select * from sys_user where id = 1")
	public JSONObject findUser();
}
