package com.msx7.josn.tvServer.udp.reciver;

import com.msx7.josn.tvServer.udp.Config;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by Josn on 2015/12/6.
 */
public class BroadReceiver {
    public static final int RECEIVE_LENGTH = 1024;

    public void init() {
        try {
            InetAddress destAddress = InetAddress.getByName(Config.ADRESS_BROADCAST);
            if (!destAddress.isMulticastAddress()) {//检测该地址是否是多播地址
                throw new IllegalAccessError("地址不是多播地址");
            }
            MulticastSocket receiveMulticast = new MulticastSocket(Config.port_BROADCAST);
            receiveMulticast.joinGroup(destAddress);
            DatagramPacket dp = new DatagramPacket(new byte[RECEIVE_LENGTH], RECEIVE_LENGTH);
            receiveMulticast.receive(dp);
            System.out.println(new String(dp.getData()).trim());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
