package com.sxt.req.po;



/*
 * 请求消息之图片消息
 * 
 */
public class TextMessage extends BaseMessage {

	private String Content;  //消息内容

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
