package com.great.service;

import java.util.List;
import java.util.Map;

import com.great.bean.Car;
import com.great.bean.Ower;
import com.great.bean.TranSact;

public interface IOwerService {
	public Ower owerLogin(Ower ower);//������¼
	public int addOwer(Ower ower);//����ע��
	public List<Map<String,Object>> searchOwersCar(int owerId);//��ѯ�����󶨵ĳ���
	public int addCars(Car car);//�����󶨳���
	public int escCars(String carId);//�Ӵ��󶨳���
	public List<Map<String,Object>> searchPack();//�����ײͱ���ײ�
	public int addMessage(Ower ower);//ʵ����֤
	public int changeMeans(Ower ower);//�༭��������
	public List<Car> carList(int owerId);//��ѯ��¼�û��ĳ���
	public TranSact tranList(Car car);//���ݳ����鳵������ײ�
	public int updateCarMess(Car car);//�޸ĳ�����������id
	public int addMoney(int owerId,int balance);//�û���ֵ���
	public Map<String,Object> searchPayNotes(Car car);//�ɷѼ�¼
	public List<Car> carTypeneone(int owerId);//�ֻ��˲�ѯ����ʱ�����ɷ�
	public int updatePwd(Ower ower);//�޸�����
	public Ower queryOwerById(int owerId);//id���û�

}
