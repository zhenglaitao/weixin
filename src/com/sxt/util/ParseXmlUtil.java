package com.sxt.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sxt.req.po.TextMessage;
import com.sxt.resp.po.Article;
import com.sxt.resp.po.MusicRespMessage;
import com.sxt.resp.po.NewsRespMessage;
import com.sxt.resp.po.TextRespMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class ParseXmlUtil {

	public static final String RESP_MESSAGE_TYPE_TEXT = "text";//������Ϣ���ͣ��ı�
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music"; //������Ϣ���ͣ�����
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";//������Ϣ���ͣ�ͼ��
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";//������Ϣ���ͣ��ı�
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";//������Ϣ���ͣ�ͼƬ
	public static final String REQ_MESSAGE_TYPE_LINK = "link";//������Ϣ���ͣ�����
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";//������Ϣ���ͣ�����λ��
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";//������Ϣ���ͣ���Ƶ
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";//������Ϣ���ͣ�����
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";//�¼����ͣ�subscribe(����)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";//�¼����ͣ�unsubscribe(ȡ������) 
	public static final String EVENT_TYPE_CLICK = "CLICK";//�¼����ͣ�CLICK(�Զ���˵�����¼�) 
	
	//static XStream xstream = new XStream();
	
	
	/*
	 * ����΢�ŷ���������XML��
	 */
	public static Map<String ,String> parseXml(HttpServletRequest request) throws Exception{
		// ����������洢��HashMap��
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement(); 
		List<Element> elementList = root.elements();
		for (Element element : elementList) {
			map.put(element.getName(), element.getText());
		}
		inputStream.close();// �ͷ���Դ
		inputStream = null;
		return map;
	}
	
	/*
	 * �ı���Ϣת����xml����
	 */
	public static String textMessageToXml(TextRespMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/*
	 * ������Ϣת����xml����
	 */
	public static String musicMessageToXml(MusicRespMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/*
	 * ͼ����Ϣת����xml����
	 */
	public static String newsMessageToXml(NewsRespMessage textMessage) {
		xstream.alias("xml", textMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(textMessage);
	}
	
	/*
	 * ��չxstream��ʹ��֧��CDATA�� 
	 * @date 2015-01-29
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				boolean cdata = true;
				public void startNode(String name, Class clazz) { 
					super.startNode(name, clazz);
				}
				protected void writeText(QuickWriter writer, String text) {  
					if (cdata) { 
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else { 
						writer.write(text);
					}
				}
			};
		}
	});
}
