package com.yang.data;

/**
 * Created by rio on 2019/2/2.
 */
public class Message809Header {

    // 数据长度(包括头标识、数据头、数据体、CRC校验码和尾标识)
    private long msgLength;
    // 报文序列号 占用四个字节，为发送信息的序列号，用于接收方检测是否有信息的丢失，上级平台和下级平台接自己发送数据包的个数计数，互不影响。程序开始运行时等于零，发送第一帧数据时开始计数，到最大数后自动归零。
    private long msgSn;
    // 业务数据类型
    private int msgID;
    // 下级平台接入码，上级平台给下级平台分配唯一标识码
    private long msgGnsscenterid;
    // 协议版本好标识，上下级平台之间采用的标准协议版编号；长度为3个字节来表示，0x01 0x02 0x0F 标识的版本号是v1.2.15，以此类推。
    private byte[] versionFlag;
    // 报文加密标识位b: 0表示报文不加密，1表示报文加密 用来区分报文是否进行加密，如果标识为1，则说明对后继相应业务的数据体采用ENCRYPT_KEY对应的密钥进行加密处理。如果标识为0，则说明不进行加密处理
    private short encryptFlag;
    // 数据加密的密匙，长度为4个字节。
    private long encryptKey;

    public byte[] headRawByte;

    public long getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(long msgLength) {
        this.msgLength = msgLength;
    }

    public long getMsgSn() {
        return msgSn;
    }

    public void setMsgSn(long msgSn) {
        this.msgSn = msgSn;
    }

    public int getMsgID() {
        return msgID;
    }

    public void setMsgID(int msgID) {
        this.msgID = msgID;
    }

    public long getMsgGnsscenterid() {
        return msgGnsscenterid;
    }

    public void setMsgGnsscenterid(long msgGnsscenterid) {
        this.msgGnsscenterid = msgGnsscenterid;
    }

    public byte[] getVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(byte[] versionFlag) {
        this.versionFlag = versionFlag;
    }

    public long getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(long encryptKey) {
        this.encryptKey = encryptKey;
    }

    public byte[] getHeadRawByte() {
        return headRawByte;
    }

    public void setHeadRawByte(byte[] headRawByte) {
        this.headRawByte = headRawByte;
    }

    public short getEncryptFlag() {
        return encryptFlag;
    }

    public void setEncryptFlag(short encryptFlag) {
        this.encryptFlag = encryptFlag;
    }
}