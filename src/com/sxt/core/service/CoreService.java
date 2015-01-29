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
	 * ����΢�ŷ���������
	 * 
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent = "�������쳣�����Ժ��ԣ�";
			Map<String, String> requestMap = ParseXmlUtil.parseXml(request);
			String fromUserName = requestMap.get("FromUserName");// ���ͷ��ʺţ�open_id��
			String toUserName = requestMap.get("ToUserName");// �����ʺ�
			String msgType = requestMap.get("MsgType");// ��Ϣ����
			
			TextRespMessage resp = new TextRespMessage();
			resp.setToUserName(fromUserName);
			resp.setFromUserName(toUserName);
			resp.setCreateTime(new Date().getTime());
			resp.setMsgType(ParseXmlUtil.RESP_MESSAGE_TYPE_TEXT);//text
			resp.setFuncFlag(0);
			
			//�ı�
			if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_TEXT)){
				//respContent = "�����͵����ı���Ϣ��";
				String content = requestMap.get("Content");
				if("�Ϲ�".equals(content) || "֣����".equals(content)){
					NewsRespMessage newsMessage = new NewsRespMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(ParseXmlUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);
					List<Article> articleList = new ArrayList<Article>();
					Article a = new Article();
					a.setTitle(" We had the best years of our lives !");
					a.setDescription("��/���� �㲻  /NO ��������");
					a.setPicUrl("http://zhenglovepurple.duapp.com/image/love.jpg");
					a.setUrl("http://zhenglovepurple.duapp.com/love.jsp");
					articleList.add(a);
					// ����ͼ����Ϣ����
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = ParseXmlUtil.newsMessageToXml(newsMessage); 
				}else if("С����".equals(content) || "����".equals(content) || "��".equals(content)){
					respContent = "��������"+content+"��/����   /����  ������������� ";
					resp.setContent(respContent);
					respMessage = ParseXmlUtil.textMessageToXml(resp);
				}else {
					resp.setContent("[�ѹ�] /�ѹ� /::(");
					respMessage = ParseXmlUtil.textMessageToXml(resp);
				}
				
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_IMAGE)){
				//ͼƬ
				//respContent = "�����͵���ͼƬ��Ϣ��";
				respContent = "ͼƬ�ܺÿ�����������I /õ�� honeyhua !";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_LOCATION)){
				//����λ����Ϣ
				respContent = "�����͵��ǵ���λ����Ϣ��";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_LINK)){
				respContent = "�����͵���������Ϣ��";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_VOICE)){
				//respContent = "�����͵�����Ƶ��Ϣ��";
				respContent = "/����  ��˵��Ҳ���㣬/��ߺ�   /�Һߺ�";
				resp.setContent(respContent);
				respMessage = ParseXmlUtil.textMessageToXml(resp);
			}else if(msgType.equals(ParseXmlUtil.REQ_MESSAGE_TYPE_EVENT)){
				// �¼����� 
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(ParseXmlUtil.EVENT_TYPE_SUBSCRIBE)) {
					respContent = "лл���Ĺ�ע��";
					resp.setContent(respContent);
					respMessage = ParseXmlUtil.textMessageToXml(resp);
				}else if(eventType.equals(ParseXmlUtil.EVENT_TYPE_UNSUBSCRIBE)){
					// TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ 
				}else if(eventType.equals(ParseXmlUtil.EVENT_TYPE_CLICK)){
					// TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
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
