package com.ncist.dataExchangePool.pool.service;

import com.ncist.dataExchangePool.pool.service.Interface.ConsumerTableService;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class consumerTable implements ConsumerTableService {

    private final Set<String> writingPartitions = new HashSet<>(); //分区状态
    private final String tableType = "consumerTable";

    @Override
    public void readConTable(List classNames, SocketAddress address) {

        StringBuilder content = new StringBuilder(tableTime() + address + ":");

        for (Object className : classNames) {
            content.append(className);
        }

        tableStructure.writeTable(tableType, 4, content.toString());

    }

    @Override
    public void readInfoClassTable(SocketAddress address, String className, int partNum, String txtName, int rows, String data, long firstTime, long lastTime, long time) {

        StringBuilder content = new StringBuilder(tableTime() + address + ":");

        content.append(className + "\\" + partNum + "\\" + txtName + ",lastDataRow:" + rows + ",lastData:" + data + ",firstTime:" + firstTime + ",lastTime:" +
                lastTime+",time:"+(lastTime-firstTime));

        tableStructure.writeTable(tableType, 5, content.toString());

    }

    @Override
    public String tableTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date) + "\n";
    }

    @Override
    public Boolean lockPartition(String className) {
        if (writingPartitions.contains(className))//如果存在则说明分区已经锁定
            return false;
        else {
            writingPartitions.add(className);
            return true;
        }
    }

    @Override
    public void unlockPartition(String className) {
        writingPartitions.remove(className);
    }
}
