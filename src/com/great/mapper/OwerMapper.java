package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Car;
import com.great.bean.Ower;
import com.great.bean.TranSact;

public interface OwerMapper {
	public Ower owerLogin(Ower ower);//车主登录
	public int addOwer(Ower ower);//车主注册
	public List<Map<String,Object>> searchOwersCar(int owerId);//查询车主绑定的车辆
	public int addCars(Car car);//新增绑定车辆
	public int escCars(String carId);//解除绑定车辆
	public List<Map<String,Object>> searchPack();//搜索套餐表的套餐
	public int updateCarMess(Car car);//修改车辆所属车主id
	public int addMessage(Ower ower);//实名认证
	public int changeMeans(Ower ower);//编辑个人资料
	public List<Car> carList(int owerId);//查询登录用户的车辆
	public TranSact tranList(Car car);//根据车辆查车辆办的套餐
	public int addMoney(int owerId,int balance);//用户充值余额
	public List<Map<String, Object>> searchPayNotes(Car car);//缴费记录
	public List<Car> carTypeneone(int owerId);//手机端查询非临时车辆缴费
	public int updateOwerBalance(Map<String,Object> map);//退钱给用户余额
	public int updatePwd(Ower ower);//修改密码
	public int updateOwerLessBalance(Map<String,Object> map);//用户余额扣钱
	public Map<String,Object> CarQueryOwer(String Car);//车牌查用户
	public Ower queryOwerById(int owerId);//id查用户
	

}
