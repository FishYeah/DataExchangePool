package com.ncist.dataExchangePool.pool.service;

import com.ncist.dataExchangePool.common.Message;
import com.ncist.dataExchangePool.common.MessageType;
import com.ncist.dataExchangePool.pool.service.Interface.ConsumerService;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Consumer implements ConsumerService {

    @Override
    public int send(Socket socket, List list, String mesType) {
        ObjectOutputStream oos = null;
        //记录行数
        int k=1;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            if (mesType.equals(MessageType.MESSAGE_READ)) {
                ArrayList<String> list1 = (ArrayList) (list.get(0));
                for (int i = 0; i < list1.size(); i++) {
                    Message message = new Message();
                    String[] messages = list1.get(i).split(",,");
                    //按条向消费者发送数据
                    message.setId(messages[0] + ",," + messages[1]);
                    message.setContent(messages[2]);
                    message.setMesType(MessageType.MESSAGE_READ_BACK);
                    message.setSender(socket.toString());
                    oos.writeObject(message);
                    k++;
                }
            } else if (mesType.equals(MessageType.MESSAGE_READ_FILE)) {
                ArrayList<String> list1 = (ArrayList) (list.get(0));
                //封装
                Message message = new Message();
                message.setContent(list1.toString());
                message.setMesType(MessageType.MESSAGE_READ_FILE_BACK);
                message.setSender(socket.toString());
                oos.writeObject(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return k;
    }
}
