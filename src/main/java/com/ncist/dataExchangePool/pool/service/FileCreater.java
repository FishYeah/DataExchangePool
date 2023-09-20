package com.ncist.dataExchangePool.pool.service;


import com.ncist.dataExchangePool.pool.service.Interface.FileCreaterService;
import com.sun.org.apache.xml.internal.security.utils.ElementCheckerImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author 赖俊业
 * @create 2023-06-19 15:39
 * 包含fileCreate()方法、dataWrite()方法、dataRead()。
 * fileCreate()方法功能为根据用户创建class的指令在交换池开辟空间。
 * dataWrite()方法功能为根据项目的数据写入机制将输入写入对应的位置。
 * dataRead()方法功能为根据消费者发送的消费请求将数据文件发送给消费者。
 */
public class FileCreater implements FileCreaterService {
    private long firstTime = 0;//记录每一个时间段第一个时间戳
    private String writeFilePath;//记录最终写入文件的路径
    private String writeBackupFilePath;//记录最终备份文件写入的路径

    //根据用户创建class的指令在交换池开辟空间。
    @Override
    public void fileCreate(String className, int partNum) {

        String filePath = "src\\" + className;
        String backupFilePath = filePath + "[备份]";

        //创建主文件夹
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        } else {
            System.out.println("文件已存在");
        }

        //创建分区文件夹
        for (int i = 1; i <= partNum; i++) {
            String fileFilePath = filePath + "\\" + i;
            File file1 = new File(fileFilePath);
            file1.mkdirs();
        }

        //创建备份主文件夹
        File file1 = new File(backupFilePath);
        if (!file1.exists()) {
            file1.mkdir();
        } else {
            System.out.println("备份文件已存在");
        }

        //创建备份分区文件夹
        for (int i = 1; i <= partNum; i++) {
            String partFilePath = backupFilePath + "\\" + i;
            File file2 = new File(partFilePath);
            file2.mkdir();
        }
    }


    //根据项目的数据写入机制将输入写入对应的位置。
    @Override
    public void dataWrite(String className, String meg, String id, long time) {
        String timeStamp = id.substring(0, 13);//记录每个消息的时间戳
        String filePath1 = null;
        String filePath2 = null;

        String filePath = "src\\" + className;
        String backupFilePath = filePath + "[备份]";

        long j, k = 0;//记录最小文件的长度
        File minFile = null;//最小文件
        boolean loop = false;
        File file5 = null;
        HashMap<Long, File> list = new HashMap<>();


        File file = new File(filePath);
        File[] files = file.listFiles();

        for (File file1 : files) {//遍历class文件下的文件
            j = 0;
            File[] files1 = file1.listFiles();
            if (files1.length == 0) {//若文件为空,记录路径
                filePath1 = file1.getPath() + "\\" + timeStamp + ".txt";
                filePath2 = backupFilePath + "\\" + file1.getName() + "\\" + timeStamp + ".txt";
                loop = true;
                break;
            }

            for (File file2 : files1) {//遍历文件夹，找出最小文件

                if (file2.length() < j || j == 0) {
                    j = file2.length();
                    file5 = file2;
                }

            }
            list.put(j, file5);
            if (k > j || k == 0) {
                k = j;
                minFile = file5;
            }
        }

        if (!loop) {//若文件都非空，则记录最小文件路径
            long fileLength = 1 * 1024 * 1024;//10MB
            filePath1 = minFile.getPath();

            File parentFile = minFile.getParentFile();
            filePath2 = backupFilePath + "\\" +
                    parentFile.getName() + "\\" +
                    minFile.getName();


            if (minFile.length() > fileLength) {//若文件大于10mb，则在该目录下创建新的txt文件
                filePath1 = minFile.getParent() + "\\" + timeStamp + ".txt";
                filePath2 = backupFilePath + "\\" +
                        parentFile.getName() + "\\" +
                        timeStamp + ".txt";
            }

        }


        long l = Long.parseLong(timeStamp);
        //判断该消息的时间戳与记录的时间戳的差值是否超过设定的时间
        if (firstTime == 0 || l - firstTime > time) {
            firstTime = l;//若超过或者是第一个消息时间戳则把当前的时间赋值
            writeFilePath = filePath1;//把记录的路径赋值
            writeBackupFilePath = filePath2;
        }

        //写入
        try {
            FileOutputStream fos = new FileOutputStream(writeFilePath, true);
            FileOutputStream fos1 = new FileOutputStream(writeBackupFilePath, true);


            fos.write((id + ",," + meg + "\n").getBytes());
            fos1.write((id + ",," + meg + "\n").getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //按照消费者请求，将读取的数据发送给消费者
    @Override
    public List dataRead(String className) {
        String classPath = "src\\" + className;

        File file = new File(classPath);
        File[] files = file.listFiles();
        //生成随机文件夹路径
        Random random = new Random();
        int i = random.nextInt(files.length);
        File file1 = new File(files[i].getPath());
        File[] files1 = file1.listFiles();
        int j = random.nextInt(files1.length);
        File file2 = new File(files1[j].getPath());


        ArrayList<String> list = new ArrayList<>();

        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file2.getPath()));
            BufferedReader br = new BufferedReader(isr);
            String str;
            //按行读取字符串
            while ((str = br.readLine()) != null) {
                list.add(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Object> objectList = new ArrayList<>();
        objectList.add(list);
        objectList.add(file2.getParent());

        return objectList;
    }
}


