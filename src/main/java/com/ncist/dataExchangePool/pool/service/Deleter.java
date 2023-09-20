package com.ncist.dataExchangePool.pool.service;

import com.ncist.dataExchangePool.pool.service.Interface.DeleterService;

import java.io.*;
import java.util.ArrayList;

/**
 * @author shkstart
 * @create 2023-06-21 19:44
 * 功能为定期检查class内各条数据已经存在的时间，
 * 超过最大设定日期的数据将会被删除。
 */
public class Deleter implements DeleterService {
    private long deadLine = 24 * 60 * 60 * 1000;//五天的毫秒数

    @Override
    public void schDeletion(String className) {
        String filePath = "src\\" + className;
        String minLine = null;//存放最小数据信息
        int j = 0;//存放最小数据的大小


        File file = new File(filePath);
        File[] files = file.listFiles();
        for (File file1 : files) {//遍历claaName文件夹下的文件
            long k = 0;
            File file4 = null;
            File[] files1 = file1.listFiles();
            for (File file2 : files1) {//遍历文件里面的txt文件
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file2.getPath()));
                    //定义字符串接收每一行的数据
                    String line;

                    //定义一个数组用来存放文件中的数据
                    ArrayList<String> list = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        if (line.length() != 0) {//判断是否为空串
                            long timeStamp = Long.parseLong(line.substring(0, 13));//获取时间戳
                            long l = System.currentTimeMillis();
                            if (l - timeStamp < deadLine) {//数据小于五天的放入数组中
                                if (line.length() < j || j == 0) {
                                    j = line.length();
                                    minLine = line;
                                }
                                list.add(line);
                            }
                        }
                    }


                    FileOutputStream fos = new FileOutputStream(file2.getPath());
                    for (String s : list) {//将数组中的数据覆盖回源文件中
                        fos.write((s + "\n").getBytes());
                    }

                    //更改txt文件名字
                    if (file2.length()!=0) {
                        String minPath = file2.getParent() + "//" + minLine.substring(0, 13) + ".txt";
                        File file3 = new File(minPath);
                        file2.renameTo(file3);
                    }

                    //更该文件夹的名字
                    if (file2.length() < k || k == 0) {
                        k = file2.length();
                        file4 = file2;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (file4.length()!=0) {
                String minFilePath = filePath + "\\" + file4.getName();
                File file2 = new File(minFilePath);
                file1.renameTo(file2);
            }
        }
    }
}
