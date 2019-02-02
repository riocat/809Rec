package com.yang.data;


import java.util.ArrayList;
import java.util.List;

/**
 * 808 位置信息汇报 GPS信息
 * 
 * xpm在20131122修改了代码，目的是将附加信息由
 * 原来只能存储一个，修改为为存储List
 * 
 * @author xpm
 *
 */
public class CliGPSReport extends BaseMsg
{
    /**
	 * <p>[内容描述]：</p>
	 */
	private static final long serialVersionUID = -8976908608757121893L;
	/**
     * <p>[内容描述]：报警标志</p>
     * 
     */
    private long alarm = -1;
    /**
     * <p>[内容描述]：状态位标志</p>
     * 
     */
    private long status = -1;
    /**
     * <p>[内容描述]：纬度, 真实纬度 * 1000000后取正整数</p>
     * 
     */
    private long lat = -1;
    /**
     * <p>[内容描述]：经度, 真实经度 * 1000000后取正整数</p>
     * 
     */
    private long lng = -1;
    /**
     * <p>[内容描述]：高度</p>
     * 
     */
    private int altitude = 0;
    /**
     * <p>[内容描述]：车速</p>
     * 
     */
    private int speed = 0;
    /**
     * <p>[内容描述]：方向</p>
     * 
     */
    private int direction = -1;
    /**
     * <p>[内容描述]：时间</p>
     * 
     */
    private BCD BCDTime = null;

    private int requestMsgSN = -1;

	public long getAlarm() {
		return alarm;
	}

	public void setAlarm(long alarm) {
		this.alarm = alarm;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public long getLat() {
		return lat;
	}

	public void setLat(long lat) {
		this.lat = lat;
	}

	public long getLng() {
		return lng;
	}

	public void setLng(long lng) {
		this.lng = lng;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public BCD getBCDTime() {
		return BCDTime;
	}

	public void setBCDTime(BCD bCDTime) {
		BCDTime = bCDTime;
	}

	public int getRequestMsgSN() {
		return requestMsgSN;
	}

	public void setRequestMsgSN(int requestMsgSN) {
		this.requestMsgSN = requestMsgSN;
	}
    
    
}
