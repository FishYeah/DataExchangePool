package com.ncist.dataExchangePool.pool.service.Interface;

import java.net.SocketAddress;
import java.util.List;

public interface ConsumerTableService {

 void readConTable(List classNames, SocketAddress address);

 void readInfoClassTable(SocketAddress address,String className, int partNum, String txtName, int rows, String data, long firstTime, long lastTime, long time);

 String tableTime();

 Boolean lockPartition(String className);

 void unlockPartition(String className);

}
