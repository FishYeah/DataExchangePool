package com.ncist.dataExchangePool.pool.producerThread;

import com.ncist.dataExchangePool.common.Message;
import com.ncist.dataExchangePool.common.MessageType;
import com.ncist.dataExchangePool.pool.service.Creater;
import com.ncist.dataExchangePool.pool.service.FileCreater;

import java.io.*;
import java.net.Socket;

/**
 * @author shkstart
 * @create 2023-06-17 15:54
 */
public class PPoolReaderRunnable implements Runnable {
    private Socket socket;
    private long time = 5000;//默认的时间窗口为5秒钟
    private PoolUdpServer poolUdpServer;
    private FileCreater fileCreater = new FileCreater();
    private Creater creater=new Creater();


    public PPoolReaderRunnable(Socket socket) {

        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }


    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                Message message = (Message) ois.readObject();


                if (message.getMesType().equals(MessageType.MESSAGE_CREATE)) {
                    //调用方法创建class和分区
                    fileCreater.fileCreate(message.getClassName(), message.getPartNum());
                    time = message.getTime();
                    //调用方法，将客户端、class名称等信息存入表中
                    creater.classTableCreater(socket.getRemoteSocketAddress(),message.getClassName());
                    if (message.getAck().equals("0")) {
                        //开启udp服务器，接收数据
                        poolUdpServer = new PoolUdpServer(socket.getPort(), time);

                        //关闭socke
                        ois.close();
                        socket.close();
                        break;
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    fileCreater.dataWrite(message.getClassName(), message.getContent(), message.getId(), time);

                } else {
                    System.out.println("这是其他类Message,暂不处理...");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


