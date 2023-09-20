package com.ncist.dataExchangePool.pool.consumerThread;

import com.ncist.dataExchangePool.common.Message;
import com.ncist.dataExchangePool.common.MessageType;
import com.ncist.dataExchangePool.pool.service.Creater;
import com.ncist.dataExchangePool.pool.service.FileCreater;
import com.ncist.dataExchangePool.pool.service.Consumer;

import java.io.*;
import java.net.Socket;
import java.util.List;

@SuppressWarnings("all")
public class CPoolReaderRunnable implements Runnable {
    private Socket socket;
    private FileCreater fileCreater = new FileCreater();
    private Consumer consumer = new Consumer();
    private Creater creater=new Creater();

    public CPoolReaderRunnable(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true) {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


                Message message = (Message) ois.readObject();


                if (message.getMesType().equals(MessageType.MESSAGE_READ)) {//消费者按条消费
                    List list = fileCreater.dataRead(message.getClassName());
                    int rows = consumer.send(socket, list, message.getMesType());
                    String textPath =(String) list.get(1);
                    creater.consumerTableCreater(socket.getRemoteSocketAddress(),textPath,rows);
                } else if (message.getMesType().equals(MessageType.MESSAGE_READ_FILE)) {//消费者按文件消费
                    List list = fileCreater.dataRead(message.getClassName());
                    consumer.send(socket,list, message.getMesType());
                    String textPath =(String) list.get(1);
                    creater.consumerTableCreater(socket.getRemoteSocketAddress(),textPath);

                } else {
                    System.out.println("这是其他类Message,暂不处理...");

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
