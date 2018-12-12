package com.great.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.great.bean.Admin;

public interface IAdminService {
	public Admin queryAdmin(Admin admin);//查询管理员-登录
}
