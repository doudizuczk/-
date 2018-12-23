package com.great.bean;

import java.io.Serializable;

/**
 * 
 * @author 
 *�ײͱ�ʵ�����czk--!
 */
public class Pack implements Serializable{

	private static final long serialVersionUID = 1L;
	/*
	 * �ײ�id
	 * �ײ�����
	 * �ײ�ʱ��/��
	 * �ײͼ۸�
	 * �ײ�����--1�½ɣ�2������
	 * �ײ�״̬
	 * �ײʹ���ʱ��
	 **/

	private int packId; 
	private String packName; 
	private int packTime ;
	private int packCost;
	private int packType;
	private int pactState;
	private String  packCdate ;
	
	
	
	public Pack() {
		super();
	}
	public Pack(int packId, String packName, int packTime, int packCost, int packType, int pactState,
			String packCdate) {
		super();
		this.packId = packId;
		this.packName = packName;
		this.packTime = packTime;
		this.packCost = packCost;
		this.packType = packType;
		this.pactState = pactState;
		this.packCdate = packCdate;
	}
	public int getPackId() {
		return packId;
	}
	public void setPackId(int packId) {
		this.packId = packId;
	}
	public String getPackName() {
		return packName;
	}
	public void setPackName(String packName) {
		this.packName = packName;
	}
	public int getPackTime() {
		return packTime;
	}
	public void setPackTime(int packTime) {
		this.packTime = packTime;
	}
	public int getPackCost() {
		return packCost;
	}
	public void setPackCost(int packCost) {
		this.packCost = packCost;
	}
	public int getPackType() {
		return packType;
	}
	public void setPackType(int packType) {
		this.packType = packType;
	}
	public int getPactState() {
		return pactState;
	}
	public void setPactState(int pactState) {
		this.pactState = pactState;
	}
	public String getPackCdate() {
		return packCdate;
	}
	public void setPackCdate(String packCdate) {
		this.packCdate = packCdate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	
	
}
