package com.ncist.dataExchangePool.common;

public interface TableType {

    /**
     * 表的数量
     */
    int tableCount = 5;

    /**
     * index 1~5为class表
     *       6~10为consumer表
     */
    String[] tableList = new String[]{"","StateClsTable", "WrittenClsTable", "WrittenInfoClsTable", "", "",
            "ReadConTable", "ReadInfoClassTable", "", "", ""};

    int CLASS_START_INDEX=1;
    int CLASS_END_INDEX=5;
    int CONSUMER_START_INDEX=6;
    int CONSUMER_END_INDEX=10;

    /**
     * 表1(记录目前交换池中class的基本信息):
     * className
     * class的分区数(partNum)
     * class文件数量(txtNum)
     * class的状态（空闲、被写入文件、被消费中、既被写入又被消费）
     * 更新时间(time)
     */
    String StateClsTable = "1";

    /**
     * 表2:
     * 写入的生产者名称( socketAddress)
     * 本次写入涉及的文件（className)
     */
    String WrittenClsTable = "2";

    /**
     * 表3:
     * 写入的生产者名称(socketAddress）
     * 写入文件路径( txtPath)
     * 最后写入数据的位置和内容(rows, data)
     * 写入开始时间(firstTime)
     * 写入结束时间(lastTime)
     * 写入总时长(time)
     */
    String WrittenInfoClsTable = "3";

    /**
     * 表4:
     * 消费的消费者名称( socketAddress）
     * 消费涉及的class (className）
     */
    String ReadConTable = "4";

    /**
     * 表5:
     * 消费的消费者名称(socketAddress）
     * 消费文件路径（(txtPath)
     * 最后消费数据的位置和内容(rows, data)
     * 消费开始时间(firstTime)
     * 消费结束时间(lastTime)
     * 消费总时长(time)
     */
    String ReadInfoClassTable = "5";

}
