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
	private Integer curPage;
	private Integer totalPage;
	private Integer totalNum;
	private Map<String, Object> dates;
	
	public PageInfo() {
		super();
	}

	public PageInfo(Integer curPage, Integer totalPage, Integer totalNum, Map<String, Object> dates) {
		super();
		this.curPage = curPage;
		this.totalPage = totalPage;
		this.totalNum = totalNum;
		this.dates = dates;
	}

	public Integer getcurPage() {
		return curPage;
	}

	public void setcurPage(Integer curPage) {
		this.curPage = curPage;
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
