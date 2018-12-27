package com.great.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Charge;

/*������@lian shengwei
 * �������ڣ�20181216
 * ��֧��ϸ
 */
public interface IChargeService{
	public double getParkingCost(String carId);//��ʱ�����Զ�����ͣ������
	public int addCharge(Charge charge);//����շѼ�¼
	public int updateCharge(Charge charge);//�޸��շѱ�
	public Charge queryChargeById(int chargeId);
	public BigDecimal queryChargeOrder(Charge charge);//��ѯ���㵥����
	
	public List<Map<String,Object>> queryRechargeWeekChart();//�½ɳ�ֵ��ͳ����
	public List<Map<String,Object>> queryLaborWeekChart();//�˹��ɷ���ͳ��
	public List<Map<String,Object>> querySelfHelpWeekChart();//�����ɷ���ͳ��
	public List<Map<String,Object>> weekChart(Map<String,Object> map);//����ͳ��
    public List<Map<String,Object>> queryAllChargeList();//�����˵��б�
    public List<Map<String,Object>> turnPageChargeList(Map<String,Object> map);//�˵��б�ҳ��ģ����ѯ
    
    public List<Map<String,Object>> mouthChart(String mouth);//����ͳ��
    public List<Map<String,Object>> querySelfHelpMouthChart(String mouth);//������ͳ��
  	public List<Map<String,Object>> queryLaborMouthChart(String mouth);//�˹���ͳ��
  	public List<Map<String,Object>> queryRechargeMouthChart(String mouth);//�½ɷ���ͳ��
  	
  	public List<Map<String,Object>> queryAllHalfyearChart();//��������ͳ��
  	public List<Map<String,Object>> querySelfHelpHalfyearChart();//����������ͳ����
	public List<Map<String,Object>> queryLaborHalfyearChart();//�˹�������ͳ����
	public List<Map<String,Object>> queryRechargeHalfyearChart();//�½ɷѽ�����ͳ����
	
}
