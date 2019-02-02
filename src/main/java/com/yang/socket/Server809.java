package com.yang.socket;

import com.yang.util.Analyze809DataUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rio on 2019/2/2.
 */
public class Server809 {

    public static void main(String[] args) {

        Server809 s809 = new Server809();
        s809.socketBase();
    }

    public void socketBase() {
        try {
            ServerSocket serverSocket = new ServerSocket(22222);
            getGPSData(serverSocket.accept());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGPSData(Socket accept) {
        try {
            InputStream is = accept.getInputStream();
            List<byte[]> temp = new ArrayList<byte[]>();
            byte[] rawData = new byte[1024];
            while (is.read(rawData) != -1) {
                temp.add(rawData);
            }

            byte[] targetArr = new byte[0];

            for (byte[] ba : temp) {
                targetArr = Arrays.copyOf(targetArr, targetArr.length + ba.length);
                System.arraycopy(ba, 0, targetArr, targetArr.length, ba.length);
            }

            System.out.println(Arrays.toString(targetArr));

            Analyze809DataUtil.analyze809Data(targetArr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
