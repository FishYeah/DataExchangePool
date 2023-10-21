package com.ncist.dataExchangePool.pool.service;

import com.ncist.dataExchangePool.common.TableType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class tableStructure {
    private static final long MAX_FOLDER_SIZE = 10 * 1024 * 1024; // 设置分区的最大大小，以字节为单位 默认为10mb大小
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 设置单个文件的最大大小，以字节为单位 默认为2mb大小
    private static int ClslatestPartNum = 0; //记录cls表最新分区的num
    private static int ConlatestPartNum = 0; //记录con表最新分区的num
    private static int tablePartIndex[] = new int[TableType.tableCount + 1]; //记录每一个表最后一次所在的分区

    public static void writeTable(String tableType, int tableNum, String content) {

        if (ClslatestPartNum == 0 && ConlatestPartNum == 0){
            //首次写入
            initTable();
        }

        File folder;
        String folderPath = "src\\table\\" + tableType + "\\partition" + tablePartIndex[tableNum] + "\\" + TableType.tableList[tableNum];

        //找到对应子表文件夹
        folder = new File(folderPath);
        int txtNum; //标记写入文件的序号

        if (folder.exists()) {//如果分区里子表文件夹存在则进行判断大小

            long folderSize = getFolderSize(folder);//获取整个子表文件夹的大小
            //System.out.println(folderSize);

            if (folderSize >= MAX_FOLDER_SIZE) {
                /*
                    子表文件夹超过设定值,则判断该表是否在最新分区
                    如果在最新分区，则创建下一个分区
                    反之将该表最新分区下标向前移动一位
                 */

                //创建分区，使对应typepartnum, tablenum前移
                if (tablePartIndex[tableNum] == ClslatestPartNum)
                    createPartition("classTable", ++ClslatestPartNum);

                tablePartIndex[tableNum] += 1;
                txtNum = 1;//新的分区，文件序号必定是1

            } else {
                txtNum = getFilePosition(folder);
            }
        } else {
            System.out.println("无法找到子表文件夹");
            return;
        }
        writeIntoTable(tableType, tableNum, content, txtNum);
    }

    private static void initTable(){
        Arrays.fill(tablePartIndex, 1);//校正每个表的位置
        createPartition("classTable", ++ClslatestPartNum);
        createPartition("consumerTable", ++ConlatestPartNum);
    }

    private static void writeIntoTable(String tableType, int tableNum, String content, int txtNum) {
        String filePath = "src\\table\\" + tableType + "\\partition" + tablePartIndex[tableNum] + "\\" + TableType.tableList[tableNum] + "\\" + txtNum + ".txt";
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static void createPartition(String tableType, int partitionNum) {

        int startIndex, endIndex;
        if (tableType.equals("classTable")) {
            startIndex = TableType.CLASS_START_INDEX;
            endIndex = TableType.CLASS_END_INDEX;
        } else if (tableType.equals("consumerTable")) {
            startIndex = TableType.CONSUMER_START_INDEX;
            endIndex = TableType.CONSUMER_END_INDEX;
        } else {
            System.out.println("错误的tableType");
            return;
        }

        for (int i = startIndex; i <= endIndex; i++) {

            if (TableType.tableList[i] .equals("")) break;

            String createTip = tableType + "分区: partition" + partitionNum + "," + TableType.tableList[i];
            if (new File("src\\table\\" + tableType + "\\partition" + partitionNum + "\\" + TableType.tableList[i]).mkdirs())
                System.out.println("成功创建: " + createTip);
            else System.out.println("无法创建: " + createTip);
        }

    }

    private static long getFolderSize(File folder) {//返回子表文件夹的大小
        long size = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                size += file.length();
            }
        }
        return size;
    }

    private static int getFilePosition(File folder) {//返回子表中符合条件的文件的位置

        int txtNum = 1;

        File[] files = folder.listFiles();
        if (files != null) {

            for (File file : files) {
                if (file.length() < MAX_FILE_SIZE)
                    break;
                txtNum++;
            }
        }

        return txtNum;
    }

}
