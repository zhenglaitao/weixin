package com.sxt.resp.po;

public class BaseRespMessage {

	private String ToUserName;// ���շ��ʺţ��յ���OpenID��
	private String FromUserName;// ������΢�ź�
	private long CreateTime;// ��Ϣ����ʱ�� �����ͣ�
	private String MsgType;// ��Ϣ���ͣ�text/music/news��
	private int FuncFlag;// λ0x0001����־ʱ���Ǳ���յ�����Ϣ
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public int getFuncFlag() {
		return FuncFlag;
	}
	public void setFuncFlag(int funcFlag) {
		FuncFlag = funcFlag;
	}
}
