package com.great.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Car;
import com.great.bean.Ower;
import com.great.bean.TranSact;

public interface OwerMapper {
	public Ower owerLogin(Ower ower);//������¼
	public int addOwer(Ower ower);//����ע��
	public List<Map<String,Object>> searchOwersCar(int owerId);//��ѯ�����󶨵ĳ���
	public int addCars(Car car);//�����󶨳���
	public int escCars(String carId);//����󶨳���
	public List<Map<String,Object>> searchPack();//�����ײͱ���ײ�
	public int updateCarMess(Car car);//�޸ĳ�����������id
	public int addMessage(Ower ower);//ʵ����֤
	public int changeMeans(Ower ower);//�༭��������
	public List<Car> carList(int owerId);//��ѯ��¼�û��ĳ���
	public TranSact tranList(Car car);//���ݳ����鳵������ײ�
	public int addMoney(int owerId,int balance);//�û���ֵ���
	public Map<String,Object> searchPayNotes(Car car);//�ɷѼ�¼
	public List<Car> carTypeneone(int owerId);//�ֻ��˲�ѯ����ʱ�����ɷ�
	public int updateOwerBalance(Map<String,Object> map);//��Ǯ���û����
	public int updatePwd(Ower ower);//�޸�����
	public int updateOwerLessBalance(Map<String,Object> map);//�û�����Ǯ
	public Map<String,Object>CarQueryOwer(String Car);//���Ʋ��û�

}
