package com.yang.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
//import java.util.GregorianCalendar;

public class BCD implements Serializable
{
	/**
	 * <p>[内容描述]：</p>
	 */
	private static final long serialVersionUID = -2995495933516110757L;
	
//    private boolean compressed = true;
    private int length = 0;
    private byte[] rawData = null;
//    private String hexString = null;

    public BCD(boolean _compressed, int _size)
    {
//        compressed = _compressed;
        rawData = new byte[_size];
    }
    
    public BCD(byte[] _data)
    {
    	setBytes(_data, _data.length);
        length = _data.length;
    }

    public void set(BCD _input)
    {
        byte[] idata = _input.getBytes();
        length = idata.length;
        System.arraycopy(idata, 0, rawData, 0, idata.length);

//        hexString = toHexString();
    }

    public void setBytes(byte[] _data)
    {
        setBytes(_data, _data.length);
        length = _data.length;

//        hexString = toHexString();
    }


    public void setBytes(byte[] _data, int _size)
    {
        length = _size;
        rawData = new byte[length];
        System.arraycopy(_data, 0, rawData, 0, length);

//        hexString = toHexString();
    }

    public byte[] getBytes()
    {
        return rawData;
    }

    public int getLength()
    {
        return length;
    }
    /**
     * 返回字符串，如BCD码的：0x010203，返回010203字符串
     * @return 
     */
    @Override
    public String toString()
    {
    	byte[] src = this.getBytes();
		StringBuilder stringBuilder = new StringBuilder("");
		if (src != null && src.length > 0) 
		{
			for (int i = 0; i < src.length; i++) {
				int v = src[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					hv = "0" + hv;
				}
				stringBuilder.append(hv);
			}
		}
		return stringBuilder.toString();
    }
    /**
     * 返回带0x的16进制表示的字符串，
     * 这个函数只能用于测试，不要在业务逻辑中调用
     * @return
     */
    @Deprecated
    public String toHexString()
    {
        StringBuffer sb = new StringBuffer(10);
        for(int i = 0; i < rawData.length; i++)
        {
            sb.append("0x" + Integer.toHexString(rawData[i]>=0?rawData[i]:256+rawData[i]) + " ");
        }
        return sb.toString();
    }
    
    /**
     * <p>[功能描述]：将BCD码转换为java.util.Calendar对象，转换失败抛出RuntimeException</p>
     * 
     * @author	熊平民, 2014-11-24上午11:14:25
     * @since	GPS调度管理系统 1.0.1
     *
     * @return 
     */
    public Calendar toCalendar()
    {
    	byte[] time = getBytes();
    	Calendar cal = null;
    	if(time != null && time.length == 6)
    	{
    		int YY = time[0] / 16 * 10 + time[0] % 16;
            int MM = time[1] / 16 * 10 + time[1] % 16;
            int DD = time[2] / 16 * 10 + time[2] % 16;
            int hh = time[3] / 16 * 10 + time[3] % 16;
            int mm = time[4] / 16 * 10 + time[4] % 16;
            int ss = time[5] / 16 * 10 + time[5] % 16;
            cal = Calendar.getInstance();
            cal.set(YY + 2000, MM - 1, DD, hh, mm, ss);
    	}
    	else
    	{
    		throw new RuntimeException("BCD码不是日期时间(141124100548)格式，不能转化为Calendar");
    	}
        return cal;
    }
    
    /**
     * <p>[功能描述]：将BCD码转换为java.util.Date对象</p>
     * 
     * @author	熊平民, 2014-11-24上午11:17:08
     * @since	GPS调度管理系统 1.0.1
     *
     * @return 
     */
    public Date toDate()
    {
    	Calendar cal = toCalendar();
        return cal.getTime();
    }
    
    public long getLongValue(){
    	return toCalendar().getTimeInMillis();
    }
}
