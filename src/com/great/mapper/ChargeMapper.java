package com.great.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.great.bean.Charge;
/*������@lian shengwei,linanping
 * �������ڣ�20181216
 * ��֧��ϸ
 */
public interface ChargeMapper {
	public int addCharge(Charge charge);//����շѼ�¼
	public int getChargeSeq();//�����շ�id
	public int updateCharge(Charge charge);//�޸��շѱ�
	public Charge queryChargeById(int chargeId);//�����շ�id��ѯ��¼
	public Map<String,Object> queryChargeOrder(Charge charge);//�����ս���
	
	public List<Map<String,Object>> queryAllChargeList();//�����˵��б�
	public List<Map<String,Object>> turnPageChargeList(Map<String,Object> map);//�˵��б�ҳ��ģ����ѯ
	
	public List<Map<String,Object>> queryAllHalfyearChart();//������ͳ����
	public List<Map<String,Object>> querySelfHelpHalfyearChart();//����������ͳ��
	public List<Map<String,Object>> queryLaborHalfyearChart();//�˹�������ͳ��
	public List<Map<String,Object>> queryRechargeHalfyearChart();//�½ɷѽ�����ͳ��
	
	public List<Map<String,Object>> queryAllMouthChart(@Param("mouth") String mouth);//��ͳ����
	public List<Map<String,Object>> querySelfHelpMouthChart(@Param("mouth") String mouth);//������ͳ��
	public List<Map<String,Object>> queryLaborMouthChart(@Param("mouth") String mouth);//�˹���ͳ��
	public List<Map<String,Object>> queryRechargeMouthChart(@Param("mouth") String mouth);//�½ɷ���ͳ��
	
	public List<Map<String,Object>> queryAllWeekChart(Map<String,Object> map);//��ͳ����
	public List<Map<String,Object>> querySelfHelpWeekChart();//������ͳ����
	public List<Map<String,Object>> queryLaborWeekChart();//�˹���ͳ����
	public List<Map<String,Object>> queryRechargeWeekChart();//�½ɳ�ֵ��ͳ����
	public int createjilu(int balance);//��������ֵ
}
