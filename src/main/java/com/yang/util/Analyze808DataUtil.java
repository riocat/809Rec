package com.yang.util;

import com.yang.data.BCD;
import com.yang.data.CliGPSReport;

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
public class Analyze808DataUtil {

    private static String fileName = "_809.txt";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat bcdSDF = new SimpleDateFormat("yyMMddHHmmss");

    public static void main(String[] args) {

        // System.out.println(preDeal("0x7e0x070x040x030xa50x060x460x010x380x480x600x100xef0x000x0f0x010x000x3c0x000x000x000x000x000x0c0x000xc10x010xdc0x410x8c0x070x3d0x5e0xe80x000x2a0x000x000x000x000x190x010x260x080x480x050x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x0c0x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410xae0x070x3d0x5e0x700x000x230x000x000x000x000x190x010x260x080x480x360x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x0e0x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410xae0x070x3d0x5e0x700x000x240x000x000x000x000x190x010x260x080x490x060x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0f0x310x010x100x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410xc00x070x3d0x5e0x700x000x240x000x000x000x000x190x010x260x080x490x360x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0f0x310x010x100x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x9e0x070x3d0x5e0x800x000x230x000x000x000x000x190x010x260x080x500x080x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0f0x310x010x100x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x8c0x070x3d0x5e0x800x000x200x000x000x000x000x190x010x260x080x500x380x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x100x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x8c0x070x3d0x5e0x800x000x200x000x000x000x000x190x010x260x080x510x130x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x100x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x8c0x070x3d0x5e0x800x000x200x000x000x000x000x190x010x260x080x510x440x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0d0x310x010x100x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x8c0x070x3d0x5e0x800x000x200x000x000x000x000x190x010x260x080x520x140x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0d0x310x010x0f0x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x8c0x070x3d0x5e0x700x000x1f0x000x000x000x000x190x010x260x080x520x440x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x100x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x9e0x070x3d0x5e0x700x000x1f0x000x000x000x000x190x010x260x080x530x140x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x0f0x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x9e0x070x3d0x5e0x700x000x1f0x000x000x000x000x190x010x260x080x530x450x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x0f0x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x9e0x070x3d0x5e0x700x000x200x000x000x000x000x190x010x260x080x540x150x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x0f0x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x9e0x070x3d0x5e0xa00x000x280x000x000x000x000x190x010x260x080x540x460x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0e0x310x010x0f0x000x3c0x000x000x000x000x000x0c0x000xc30x010xdc0x410x9e0x070x3d0x5e0xb00x000x270x000x000x000x000x190x010x260x080x550x170x010x040x000x000x3a0xd10x020x020x000x000x030x020x000x000x250x040x000x000x000x000x2b0x040x000x000x000x000x300x010x0d0x310x010x0e0x630x7e"));
        // byte[] originArray = {1,57,32,2,112,56};
        // System.out.println(Analyze808DataUtil.getBCDStr(originArray));

        analyze808Data();

        // get0702Report("190111.txt");

        // get0702Report("temp.txt");

        // get0702ReportOld("190107.txt");

        // analyze808DataStr();
    }

    public static void analyze808Data() {

        String dataStr = null;
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileOutputStream(new File("E:/GT/gps_move/myDecode/809ReciveData/" + bcdSDF.format(new Date()) + fileName)));
            byte[] sourceArray;

            List<CliGPSReport> list = new ArrayList<>();

            dataStr = preDeal(dataStr);

            if (dataStr != null) {
                String targetStr = dataStr;
                sourceArray = ByteHexStrConvertUtil.getByteArrayFromHexStr(targetStr);

                if (!Analyze808DataUtil.checkData(sourceArray)) {
                    System.out.println("********* 未通过校验 **********");
                    return;
                }

                // System.out.println(Analyze808DataUtil.getBinaryString(sourceArray));

                String attribute = Analyze808DataUtil
                        .getBinaryString(Analyze808DataUtil.getSubByteArray(sourceArray, 2, 3));
                //
                // System.out.println(
                // "消息ID:" +
                // Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(sourceArray,
                // 0, 1)));
                //
                // System.out.println("消息体属性:" + attribute);
                // System.out.println(
                // "终端手机号:" +
                // Analyze808DataUtil.getBCDStr(Analyze808DataUtil.getSubByteArray(sourceArray,
                // 4, 9)));
                // System.out.println("消息流水号"
                // +
                // Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(sourceArray,
                // 10, 11)));

                System.out.println("**************");

                if (attribute.charAt(2) == 1) {
                    System.out.println("分包处理???????");
                } else {

                    int typeID = Analyze808DataUtil.getShortValue(Analyze808DataUtil.getSubByteArray(sourceArray, 0, 1));

                    if (typeID == 1796) {

                        byte[] body = Analyze808DataUtil.getSubByteArray(sourceArray, 0, sourceArray.length - 2);
                        list.addAll(getCliGPSReports(body, sourceArray));

                    } else if (typeID == 512) {

                        String alert = Analyze808DataUtil
                                .getBinaryString(Analyze808DataUtil.getSubByteArray(sourceArray, 12, 15));
                        System.out.println("alert : " + alert);
                        String status = Analyze808DataUtil
                                .getBinaryString(Analyze808DataUtil.getSubByteArray(sourceArray, 16, 19));
                        long oriLatitude = Analyze808DataUtil
                                .getIntValue(Analyze808DataUtil.getSubByteArray(sourceArray, 20, 23));
                        BigDecimal Latitude = new BigDecimal(oriLatitude).divide(new BigDecimal(10).pow(6), 6,
                                BigDecimal.ROUND_DOWN);
                        long oriLongitude = Analyze808DataUtil
                                .getIntValue(Analyze808DataUtil.getSubByteArray(sourceArray, 24, 27));
                        BigDecimal Longitude = new BigDecimal(oriLongitude).divide(new BigDecimal(10).pow(6), 6,
                                BigDecimal.ROUND_DOWN);
                        int altitude = Analyze808DataUtil
                                .getShortValue(Analyze808DataUtil.getSubByteArray(sourceArray, 28, 29));
                        int oriSpeed = Analyze808DataUtil
                                .getShortValue(Analyze808DataUtil.getSubByteArray(sourceArray, 30, 31));
                        BigDecimal speed = new BigDecimal(oriSpeed).divide(new BigDecimal(10), 6,
                                BigDecimal.ROUND_DOWN);
                        int direction = Analyze808DataUtil
                                .getShortValue(Analyze808DataUtil.getSubByteArray(sourceArray, 32, 33));
                        String oriTime = Analyze808DataUtil
                                .getBCDStr(Analyze808DataUtil.getSubByteArray(sourceArray, 34, 39));
                        String time = sdf.format(bcdSDF.parse(oriTime));

                        CliGPSReport cgr = new CliGPSReport();
                        cgr.setAlarm(
                                Analyze808DataUtil.getIntValue(Analyze808DataUtil.getSubByteArray(sourceArray, 12, 15)));
                        cgr.setStatus(
                                Analyze808DataUtil.getIntValue(Analyze808DataUtil.getSubByteArray(sourceArray, 16, 19)));
                        cgr.setLat(oriLatitude);
                        cgr.setLng(oriLongitude);
                        cgr.setAltitude(altitude);
                        cgr.setDirection(direction);
                        cgr.setSpeed(oriSpeed);
                        cgr.setBCDTime(new BCD(Analyze808DataUtil.getSubByteArray(sourceArray, 34, 39)));
                        cgr.setRawdata(sourceArray);
                        list.add(cgr);
                    }

                }

            }


            Collections.sort(list, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    CliGPSReport cgr1 = (CliGPSReport) o1;
                    CliGPSReport cgr2 = (CliGPSReport) o2;
                    return (int) (cgr1.getBCDTime().getLongValue() - cgr2.getBCDTime().getLongValue());
                }

            });

            for (CliGPSReport cpr : list) {

                long status = cpr.getStatus();
                long alert = cpr.getAlarm();

                long oriLatitude = cpr.getLat();
                BigDecimal Latitude = new BigDecimal(oriLatitude).divide(new BigDecimal(10).pow(6), 6,
                        BigDecimal.ROUND_DOWN);
                long oriLongitude = cpr.getLng();
                BigDecimal Longitude = new BigDecimal(oriLongitude).divide(new BigDecimal(10).pow(6), 6,
                        BigDecimal.ROUND_DOWN);
                int altitude = cpr.getAltitude();
                int oriSpeed = cpr.getSpeed();
                BigDecimal speed = new BigDecimal(oriSpeed).divide(new BigDecimal(10), 6, BigDecimal.ROUND_DOWN);
                int direction = cpr.getDirection();
                String oriTime = Analyze808DataUtil.getBCDStr(cpr.getBCDTime().getBytes());
                String time = sdf.format(bcdSDF.parse(oriTime));

                // 输出定位信息
                StringBuilder sb = new StringBuilder();
                sb.append("time: ").append(time).append(" ; speed :").append(speed).append(" ; Latitude : ")
                        .append(Latitude).append(" ; Longitude : ").append(Longitude).append(" ; direction : ")
                        .append(direction).append("; altitude : ").append(altitude).append("; alert : ").append(alert)
                        .append("; status: ").append(status).append(";")
                        .append(Analyze808DataUtil.getBinaryString(cpr.getRawdata()));
                pw.println(sb.toString());
                pw.flush();
            }

        } catch (Exception e) { // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            if (pw != null) {
                pw.close();
            }

        }
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

    public static boolean checkData(byte[] sourceArray) {
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

    // 0x7e0x020x000x000x2a0x010x380x210x820x450x560x0e0xb70x000x000x000x000x000x0c0x00
    // 0x020x020x560x160x5f0x060xfa0xfb0x930x000x4d0x000x000x000x500x180x120x190x100x11
    // 0x000x010x040x000x250xb40xa80x020x020x000x000x030x020x000x000xad0x7e
    public static String preDeal(String dataStr) {
        String targetStr = null;
        String head = dataStr.substring(0, 4);
        String body = dataStr.substring(4, dataStr.length() - 4);
        String end = dataStr.substring(dataStr.length() - 4, dataStr.length());
        if (!"0x7e".equals(head) || !"0x7e".equals(end))
            return null;

        Pattern pattern1 = Pattern.compile("0x7d0x02");
        Pattern pattern2 = Pattern.compile("0x7d0x01");

        Matcher matcher1 = pattern1.matcher(body);

        String temp = matcher1.replaceAll("0x7e");

        Matcher matcher2 = pattern2.matcher(temp);

        targetStr = matcher2.replaceAll("0x7d");

        return targetStr;
    }

    // unsign byte -> short
    public static short getByteValue(byte[] originArray) {
        byte target = originArray[originArray.length - 1];
        return (short) (target & 0xFF);
    }

    public static short getByteValue(byte originByte) {
        return (short) (originByte & 0xFF);
    }

    // unsign short -> int
    public static int getShortValue(byte[] originArray) {
        byte b1 = originArray[originArray.length - 2];
        byte b2 = originArray[originArray.length - 1];

        return ((b1 & 0xFF) << 8) + (b2 & 0xFF);
    }

    // unsign int -> long
    public static long getIntValue(byte[] originArray) {
        long target = 0;
        byte b1 = originArray[originArray.length - 4];
        byte b2 = originArray[originArray.length - 3];
        byte b3 = originArray[originArray.length - 2];
        byte b4 = originArray[originArray.length - 1];

        target += (b1 & 0xffl) << 24;
        target += (b2 & 0xffl) << 16;
        target += (b3 & 0xffl) << 8;
        target += (b4 & 0xffl);
        return target;
    }

    // include end
    public static byte[] getSubByteArray(byte[] originArray, int start, int end) {
        byte[] byteArray = new byte[end - start + 1];
        int i = 0;
        for (int index = start; index <= end; index++) {
            byteArray[i++] = originArray[index];
        }
        return byteArray;
    }

    public static String getEncodedString(byte[] originArray, String charset) {
        String target = null;
        try {
            target = new String(originArray, charset);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return target;
    }

    public static String getBinaryString(byte[] originArray) {
        StringBuffer sb = new StringBuffer();
        int mode = 1;
        for (byte b : originArray) {
            String source = Integer.toBinaryString(b & 0xff);
            StringBuffer temp = new StringBuffer();
            int sourceLen = source.length();
            for (; sourceLen < 8; sourceLen++) {
                temp.append("0");
            }
            temp.append(source);
            sb.append(temp);
        }
        return sb.toString();
    }

    public static String getBCDStr(byte[] originArray) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < originArray.length; i++) {
            byte temp = originArray[i];
            int high = (temp & 0x0f0) >>> 4;
            int low = temp & 0x0f;
            sb.append(high).append(low);
        }
        return sb.toString();
    }
}
