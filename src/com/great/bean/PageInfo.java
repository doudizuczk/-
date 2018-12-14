package com.great.bean;

import java.io.Serializable;
import java.util.Map;

/*
 * ��ҳ��Ϣʵ����� @linanping
 * */
public class PageInfo implements Serializable {
	public static final  long serialVersionUID=527652543L;
	/*��ǰҳ��
	 *��ҳ��
	 *�ܼ�¼��
	 *�������
	 * */
	private Integer curePage;
	private Integer totalPage;
	private Integer totalNum;
	private Map<String, Object> dates;
	
	public PageInfo() {
		super();
	}

	public PageInfo(Integer curePage, Integer totalPage, Integer totalNum, Map<String, Object> dates) {
		super();
		this.curePage = curePage;
		this.totalPage = totalPage;
		this.totalNum = totalNum;
		this.dates = dates;
	}

	public Integer getCurePage() {
		return curePage;
	}

	public void setCurePage(Integer curePage) {
		this.curePage = curePage;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Map<String, Object> getDates() {
		return dates;
	}

	public void setDates(Map<String, Object> dates) {
		this.dates = dates;
	}

}
