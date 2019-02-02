package com.yang.util;

import com.yang.data.BCD;
import com.yang.data.CliGPSReport;
import com.yang.data.Message809Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rio on 2019/2/2.
 */
public class Analyze809DataUtil {

    private static String fileName = "_809.txt";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat bcdSDF = new SimpleDateFormat("yyMMddHHmmss");

    public static void analyze809Data(byte[] rawData) {

        try {
            String dataStr = pre809Deal(ByteHexStrConvertUtil.getHexStrFromByteArray(rawData));
            byte[] realBytes = ByteHexStrConvertUtil.getByteArrayFromHexStr(dataStr);

            Message809Header m809header = get809MessageHeader(realBytes);

            switch (m809header.getMsgID()) {
/*                case :
                    arse0x1202(realBytes, m809header);
                    break;*/
                case 0x1202:
                    parse0x1202(realBytes, m809header);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parse0x1202(byte[] realBytes, Message809Header m809header) {

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileOutputStream(new File("E:/GT/gps_move/myDecode/809ReciveData/" + bcdSDF.format(new Date()) + fileName)));

            byte[] dataBody = new byte[0];
            System.arraycopy(realBytes, m809header.getHeadRawByte().length, dataBody, 0,
                    (realBytes.length - m809header.getHeadRawByte().length - 2));
            if (m809header.getEncryptFlag() == 1) {
                dataBody = decrypt809Data(m809header, dataBody);
            }

            int index = 0;
            // VEHICLE_NO 21	Octet String	车牌号
            String vehicleNo = Analyze808DataUtil.getEncodedString(Analyze808DataUtil.getSubByteArray(realBytes, index, index + 20), "GBK");
            index = index + 21;

            // VEHICLE_COLOR	1	BYTE	车辆颜色，按照JT/T415-2006中5.4.12的规定
            short vehicleColor = realBytes[index++];

            // DATA_TYPE	2	Uint16_t	子业务类型标识
            int dataType = Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++));

            // DATA_LENGTH	4	Uint32_t	后续数据长度
            long dataLength = Analyze808DataUtil.getIntValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++ + 2));

            // 该字段标识传输的定位信息是否使用国家测绘局批准的地图保密插件进行加密。加密标识：1-已加密，0-未加密
            short EXCRYPT = Analyze808DataUtil.getByteValue(realBytes[index++]);

            // DATE 日月年（dmyy），年的表示是先将年转换成2为十六进制数，如2009标识为0x070xD9.
            short day = Analyze808DataUtil.getByteValue(realBytes[index++]);
            short month = Analyze808DataUtil.getByteValue(realBytes[index++]);
            int year = Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++));

            // TIME 时分秒（hms）
            short hour = Analyze808DataUtil.getByteValue(realBytes[index++]);
            short minute = Analyze808DataUtil.getByteValue(realBytes[index++]);
            short second = Analyze808DataUtil.getByteValue(realBytes[index++]);

            // Longitude 经度，单位为1*10^-6度。
            long oriLongitude = Analyze808DataUtil.getIntValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++ + 2));

            // Longitude 经度，单位为1*10^-6度。
            long oriLatitude = Analyze808DataUtil.getIntValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++ + 2));

            // VEC1 速度，指卫星定位车载终端设备上传的行车速度信息，为必填项。单位为千米每小时（km/h）。
            int VEC1 = Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++));

            // VEC2 行驶记录速度，指车辆行驶记录设备上传的行车速度信息，为必填项。单位为千米每小时（km/h）。
            int VEC2 = Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++));

            // VEC3 车辆当前总里程数，值车辆上传的行车里程数。单位单位为千米（km）。
            long VEC3 = Analyze808DataUtil.getIntValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++ + 2));

            // 方向，0-359，单位为度（。），正北为0，顺时针。
            int direction = Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++));

            // 海拔高度，单位为米（m）。
            int altitude = Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(realBytes, index++, index++));

            BigDecimal Latitude = new BigDecimal(oriLatitude).divide(new BigDecimal(10).pow(6), 6,
                    BigDecimal.ROUND_DOWN);
            BigDecimal Longitude = new BigDecimal(oriLongitude).divide(new BigDecimal(10).pow(6), 6,
                    BigDecimal.ROUND_DOWN);
            int oriSpeed = VEC1;
            BigDecimal speed = new BigDecimal(oriSpeed).divide(new BigDecimal(10), 6, BigDecimal.ROUND_DOWN);

            String time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;

            // 输出定位信息
            StringBuilder sb = new StringBuilder();
            sb.append("time: ").append(time).append(" ; speed :").append(speed).append(" ; Latitude : ")
                    .append(Latitude).append(" ; Longitude : ").append(Longitude).append(" ; direction : ")
                    .append(direction).append("; altitude : ").append(altitude);
            pw.println(sb.toString());
            pw.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (pw != null) {
                pw.close();
            }

        }
    }

    public static byte[] decrypt809Data(Message809Header m809header, byte[] dataBody) {
        long M1 = 10000000l;
        long IA1 = 20000000l;
        long IC1 = 30000000l;
        int index = 0;
        long mkey = M1;
        long key = m809header.getEncryptKey();
        if (key == 0) {
            key = 1;
        }
        if (mkey == 0) {
            mkey = 1;
        }

        for (; index < dataBody.length; ) {
            key = IA1 * (key % mkey) + IC1;
            dataBody[index] = (byte) (dataBody[index] ^ ((byte) ((key >> 20) & 0xFF)));
            index++;
        }

        return dataBody;
    }

    private static Message809Header get809MessageHeader(byte[] realBytes) {
        byte[] rawHeader = new byte[0];
        byte[] tmp;

        Message809Header m809 = new Message809Header();

        tmp = Analyze808DataUtil.getSubByteArray(realBytes, 0, 3);
        arrayDilatation(tmp, rawHeader);
        m809.setMsgLength(Analyze808DataUtil.getIntValue(tmp));

        tmp = Analyze808DataUtil.getSubByteArray(realBytes, 4, 7);
        arrayDilatation(tmp, rawHeader);
        m809.setMsgSn(Analyze808DataUtil.getIntValue(tmp));

        // 0x1202 实时上传车辆定位信息	UP_EXG_MSG_REAL_LOCATION
        tmp = Analyze808DataUtil.getSubByteArray(realBytes, 8, 9);
        arrayDilatation(tmp, rawHeader);
        m809.setMsgID(Analyze808DataUtil.getShortValue(tmp));

        tmp = Analyze808DataUtil.getSubByteArray(realBytes, 10, 13);
        arrayDilatation(tmp, rawHeader);
        m809.setMsgGnsscenterid(Analyze808DataUtil.getShortValue(tmp));

        tmp = Analyze808DataUtil.getSubByteArray(realBytes, 14, 16);
        arrayDilatation(tmp, rawHeader);
        m809.setVersionFlag(tmp);

        tmp = Analyze808DataUtil.getSubByteArray(realBytes, 17, 17);
        arrayDilatation(tmp, rawHeader);
        m809.setEncryptFlag(Analyze808DataUtil.getByteValue(tmp));

        tmp = Analyze808DataUtil.getSubByteArray(realBytes, 18, 21);
        arrayDilatation(tmp, rawHeader);
        m809.setEncryptKey(Analyze808DataUtil.getIntValue(tmp));

        return m809;
    }

    private static void arrayDilatation(byte[] src, byte[] dest) {
        Arrays.copyOf(dest, src.length);
        System.arraycopy(src, 0, dest, dest.length, src.length);
    }

    private static <T> void arrayDilatation(T[] src, T[] dest) {
        Arrays.copyOf(dest, src.length);
        System.arraycopy(src, 0, dest, dest.length, src.length);
    }

    private static Collection<? extends CliGPSReport> getCliGPSReports(byte[] body, byte[] sourceArray) {
        List<CliGPSReport> list = new ArrayList<CliGPSReport>();

        // int messageID = (body[0] & 0xff) << 8 + (body[1] & 0xff); 计算出的只是错误的
        // << 运算级别低于+
        int messageID = ((body[0] & 0xff) << 8) + (body[1] & 0xff);
        int attribute = ((body[2] & 0xff) << 8) + (body[3] & 0xff);
        byte[] BCDArray = {body[4], body[5], body[6], body[7], body[8], body[9]};
        int messageSN = ((body[10] & 0xff) << 8) + (body[11] & 0xff);

        int messageNum = ((body[12] & 0xff) << 8) + (body[13] & 0xff);
        int messageType = body[14] & 0xff;

        int index = 14;
        for (int i = 0; i < messageNum; i++) {
            int beginIndex = index;
            int messageLength = ((body[++index] & 0xff) << 8) + (body[++index] & 0xff);

            CliGPSReport cgr = new CliGPSReport();
            int startInt = index + 1;
            cgr.setAlarm(((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                    + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl));
            String alert = Analyze808DataUtil.getBinaryString(Analyze808DataUtil.getSubByteArray(body, startInt, startInt + 3));
            System.out.println("alert : " + alert);
            cgr.setStatus(((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                    + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl));

            cgr.setLat(((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                    + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl));
            cgr.setLng(((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                    + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl));
            cgr.setAltitude(((body[++index] & 0xff) << 8) + (body[++index] & 0xff));
            cgr.setSpeed(((body[++index] & 0xff) << 8) + (body[++index] & 0xff));
            cgr.setDirection(((body[++index] & 0xff) << 8) + (body[++index] & 0xff));
            cgr.setBCDTime(new BCD(new byte[]{body[++index], body[++index], body[++index], body[++index],
                    body[++index], body[++index]}));

            int mainDataLength = index - beginIndex - 2;
            if (messageLength > mainDataLength) {

                int additionMessageLength = messageLength - mainDataLength;

                for (int j = 0; j < additionMessageLength; ) {
                    int beginIndex2 = index;
                    short additionID = body[++index];
                    short additionLength = body[++index];
                    switch (additionID) {
                        case 0x01:
                            long mile = ((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                                    + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl);
                            break;
                        case 0x02:
                            int oilMass = ((body[++index] & 0xff) << 8) + (body[++index] & 0xff);
                            break;
                        case 0x03:
                            int recordSpeed = ((body[++index] & 0xff) << 8) + (body[++index] & 0xff);
                            break;
                        case 0x04:
                            int alarmID = ((body[++index] & 0xff) << 8) + (body[++index] & 0xff);
                            break;
                        case 0x05:
                            break;
                        case 0x06:
                            break;
                        case 0x07:
                            break;
                        case 0x08:
                            break;
                        case 0x09:
                            break;
                        case 0x0A:
                            break;
                        case 0x0B:
                            break;
                        case 0x0C:
                            break;
                        case 0x0D:
                            break;
                        case 0x0E:
                            break;
                        case 0x0F:
                            break;
                        case 0x10:
                            break;
                        case 0x11: {
                            int type = (body[++index] & 0xff);
                            if (type != 0) {
                                long someID = ((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                                        + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl);
                            }
                        }
                        break;
                        case 0x12: {
                            int type = (body[++index] & 0xff);
                            long someID = ((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                                    + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl);
                            int direction = (body[++index] & 0xff);
                        }
                        break;
                        case 0x13: {
                            long someID = ((body[++index] & 0xffl) << 24) + ((body[++index] & 0xffl) << 16)
                                    + ((body[++index] & 0xffl) << 8) + (body[++index] & 0xffl);
                            int driveSecond = ((body[++index] & 0xff) << 8) + (body[++index] & 0xff);
                            int result = (body[++index] & 0xff);
                        }
                        break;
                        case 0x25: {
                            int flag = body[++index] & 0xff;
                            flag = body[++index] & 0xff;
                            flag = body[++index] & 0xff;
                            flag = body[++index] & 0xff;
                        }
                        break;
                        case 0x2A: {
                            int flag = body[++index] & 0xff;
                            flag = body[++index] & 0xff;
                        }
                        break;
                        case 0x2B: {
                            int AD0 = ((body[++index] & 0xff) << 8) + (body[++index] & 0xff);
                            int AD1 = ((body[++index] & 0xff) << 8) + (body[++index] & 0xff);
                        }
                        break;
                        case 0x30:
                            int signalIntensity = body[++index] & 0xff;
                            break;
                        case 0x31:
                            int satelliteNum = body[++index] & 0xff;
                            break;
                        case 0xE0: {
                            for (int m = 0; m < additionLength; m++) {
                                byte temp = body[++index];
                            }
                        }
                        break;
                        default: {
                            byte[] packet = null;
                            int idx = 0;
                            // 非标准附加信息抛弃防止异常发生
                            packet = new byte[additionLength];
                            idx = 0;
                            for (int n = additionLength; n > 0; n--) {
                                packet[idx++] = body[++index];
                            }
                        }
                        break;
                    }

                    j += index - beginIndex2;
                }
            }

            int actualLength = index - beginIndex - 2;
            if (actualLength != messageLength) {
                System.out.println("消息长度和和数据体长度不符");
                index = beginIndex + messageLength;
            }

            cgr.setRawdata(sourceArray);
            list.add(cgr);
        }

        return list;
    }

    private static boolean checkData(byte[] sourceArray) {
        byte checkSum = sourceArray[sourceArray.length - 1];
        byte target = sourceArray[0];
        for (int i = 1; i < sourceArray.length - 1; i++) {
            target = (byte) (target ^ sourceArray[i]);
        }

        if (target == checkSum) {
            return true;
        }
        return false;
    }

    private static String pre809Deal(String dataStr) {
        String targetStr = null;
        String head = dataStr.substring(0, 4);
        String body = dataStr.substring(4, dataStr.length() - 4);
        String end = dataStr.substring(dataStr.length() - 4, dataStr.length());
        if (!"0x5b".equals(head) || !"0x5d".equals(end))
            return null;

        Pattern pattern1 = Pattern.compile("0x5a0x01");
        Pattern pattern2 = Pattern.compile("0x5a0x02");
        Pattern pattern3 = Pattern.compile("0x5e0x01");
        Pattern pattern4 = Pattern.compile("0x5e0x02");

        Matcher matcher1 = pattern1.matcher(body);

        String temp = matcher1.replaceAll("0x5b");

        Matcher matcher2 = pattern2.matcher(temp);

        targetStr = matcher2.replaceAll("0x5a");

        Matcher matcher3 = pattern3.matcher(targetStr);

        targetStr = matcher3.replaceAll("0x5d");

        Matcher matcher4 = pattern4.matcher(targetStr);

        targetStr = matcher4.replaceAll("0x5e");

        return targetStr;
    }
}
