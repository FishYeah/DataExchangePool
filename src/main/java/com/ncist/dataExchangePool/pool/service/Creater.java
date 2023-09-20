package com.ncist.dataExchangePool.pool.service;

import com.ncist.dataExchangePool.pool.service.Interface.CreaterService;

import java.io.*;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * @author shkstart
 * @create 2023-06-23 11:07
 * 包含classTableCreater()方法、consumerTableCreater()方法。
 */
@SuppressWarnings("all")
public class Creater implements CreaterService {


    //class目录主要作用为记录交换池中已经存在的class名称
    // （这里可以视情况增加class的其余具体信息）
    @Override
    public void classTableCreater(SocketAddress address, String className) {
        String filePath = "src\\classTable.txt";

        File file = new File(filePath);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String times = simpleDateFormat.format(date);

        try {
            FileOutputStream fos = new FileOutputStream(filePath,true);
            fos.write((address+","+className+","+times+"\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void consumerTableCreater(SocketAddress address,String textPath, int rows) {
        String filePath = "src\\consumerTable.txt";

        File file = new File(filePath);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String times = simpleDateFormat.format(date);

        try {
            FileOutputStream fos = new FileOutputStream(filePath,true);
            fos.write((address+","+textPath+","+rows+","+times+"\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void consumerTableCreater(SocketAddress address,String textPath) {
        String filePath = "src\\consumerTable.txt";

        File file = new File(filePath);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String times = simpleDateFormat.format(date);

        try {
            FileOutputStream fos = new FileOutputStream(filePath,true);
            fos.write((address+","+textPath+","+times+"\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
