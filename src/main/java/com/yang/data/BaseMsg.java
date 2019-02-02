package com.yang.data;

import java.io.Serializable;

/**
 * Modified by HXY for new 2011.11.25 09:14 to Add function(parseMsgHead) and
 * attribute(body)
 * 
 * @author computer
 * 
 */
public class BaseMsg implements Serializable
{
	/**
	 * <p>[内容描述]：</p>
	 */
	private static final long serialVersionUID = -1206410097928762456L;
	/**
	 * 消息ID
	 */
	private int msgID = -1;
	/**
	 * 消息属性
	 */
	private int msgProperty = -1;
	/**
	 * 消息体长度
	 */
	private int msgLen = -1;
	/**
	 * 消息是否被加密
	 */
	private boolean encryptTag = false;
	/**
	 * 消息流水号
	 */
	private int msgSN = 0;
	/**
	 * 消息手机号BCD码
	 */
	private BCD msisdn = null;

	/**
	 * xpm 添加，只在cli消息类中使用，原始数据，已经转义且带有前后的7E
	 */
	private byte[] rawdata = null;

	/**
	 * Added by HXY for new 2011.11.25 09:16 为了解析监控端发上来的查岗信息，如果与其他的发生冲突，请找HXY
	 */
	private byte[] body = null;

	public int getMsgID() {
		return msgID;
	}

	public void setMsgID(int msgID) {
		this.msgID = msgID;
	}

	public int getMsgProperty() {
		return msgProperty;
	}

	public void setMsgProperty(int msgProperty) {
		this.msgProperty = msgProperty;
	}

	public int getMsgLen() {
		return msgLen;
	}

	public void setMsgLen(int msgLen) {
		this.msgLen = msgLen;
	}

	public boolean isEncryptTag() {
		return encryptTag;
	}

	public void setEncryptTag(boolean encryptTag) {
		this.encryptTag = encryptTag;
	}

	public int getMsgSN() {
		return msgSN;
	}

	public void setMsgSN(int msgSN) {
		this.msgSN = msgSN;
	}

	public BCD getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(BCD msisdn) {
		this.msisdn = msisdn;
	}

	public byte[] getRawdata() {
		return rawdata;
	}

	public void setRawdata(byte[] rawdata) {
		this.rawdata = rawdata;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
