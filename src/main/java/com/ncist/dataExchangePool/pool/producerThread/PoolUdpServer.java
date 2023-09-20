package com.ncist.dataExchangePool.pool.producerThread;

import com.ncist.dataExchangePool.pool.service.FileCreater;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 该类专门接收udp传输方式发送来的数据
 */

public class PoolUdpServer {
    private int poolPort;
    private long time;//时间窗口
    private FileCreater fileCreater = new FileCreater();

    public PoolUdpServer(int poolPort, long time) {
        try {
            DatagramSocket socket = new DatagramSocket(poolPort);

            byte[] bytes = new byte[1024 * 64];//创建一个数据包对象，用于接收数据

            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);

            while (true) {
                socket.receive(packet);
                int len = packet.getLength();
                String data = new String(bytes, 0, len);
                String[] split = data.split(",");

                fileCreater.dataWrite(split[0], split[1], split[2], time);//将数据填入指定位置


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
