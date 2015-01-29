package com.sxt.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sxt.resp.po.Article;
import com.sxt.resp.po.NewsRespMessage;
import com.sxt.resp.po.TextRespMessage;
import com.sxt.util.ParseXmlUtil;

public class CoreService {

	/*
	 * 处理微信发来的请求
	 * 
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";
			Map<String, String> requestMap = ParseXmlUtil.parseXml(request);
			String fromUserName = requestMap.get("FromUserName");// 发送方帐号（open_id）
			String toUserName = requestMap.get("ToUserName");// 公众帐号
			String msgType = requestMap.get("MsgType");// 消息类型
			
			TextRespMessage resp = new TextRespMessage();
			resp.setToUserName(fromUserName);
			resp.setFromUserName(toUserName);
			resp.setCreateTime(new Date().getTime());
			resp.setMsgType(ParseXmlUtil.RESP_MESSAGE_TYPE_TEXT);//text
			resp.setFuncFlag(0);
			
			//文本
			if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_TEXT)){
				//respContent = "您发送的是文本消息！";
				String content = requestMap.get("Content");
				if("老公".equals(content) || "郑来涛".equals(content)){
					NewsRespMessage newsMessage = new NewsRespMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(ParseXmlUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);
					List<Article> articleList = new ArrayList<Article>();
					Article a = new Article();
					a.setTitle(" We had the best years of our lives !");
					a.setDescription("爱/飞吻 你不  /NO 是三两天");
					a.setPicUrl("http://zhenglovepurple.duapp.com/image/love.jpg");
					a.setUrl("http://zhenglovepurple.duapp.com/love.jsp");
					articleList.add(a);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = ParseXmlUtil.newsMessageToXml(newsMessage); 
				}else if("小涛涛".equals(content) || "涛涛".equals(content) || "涛".equals(content)){
					respContent = "恩，我是"+content+"，/害羞   /飞吻  ，爱生活，爱花花 ";
					resp.setContent(respContent);
					respMessage = ParseXmlUtil.textMessageToXml(resp);
				}else {
					resp.setContent("[难过] /难过 /::(");
					respMessage = ParseXmlUtil.textMessageToXml(resp);
				}
				
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_IMAGE)){
				//图片
				//respContent = "您发送的是图片消息！";
				respContent = "图片很好看，恩，不丑，I /玫瑰 honeyhua !";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_LOCATION)){
				//地理位置信息
				respContent = "您发送的是地理位置消息！";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_LINK)){
				respContent = "您发送的是链接消息！";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_VOICE)){
				//respContent = "您发送的是音频消息！";
				respContent = "/咒骂  再说我也爱你，/左哼哼   /右哼哼";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_EVENT)){
				// 事件类型 
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(ParseXmlUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "谢谢您的关注！";
					resp.setContent(respContent);
					respMessage = ParseXmlUtil.textMessageToXml(resp);
				}else if(eventType.equals(ParseXmlUtil.EVENT_TYPE_UNSUBSCRIBE)){
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息 
				}else if(eventType.equals(ParseXmlUtil.EVENT_TYPE_CLICK)){
					// TODO 自定义菜单权没有开放，暂不处理该类消息
				}
			}
			else{
				resp.setContent("nothing");
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return respMessage;
	}
}
