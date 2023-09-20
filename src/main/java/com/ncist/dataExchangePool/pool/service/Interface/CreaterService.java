package com.ncist.dataExchangePool.pool.service.Interface;

import java.net.SocketAddress;

/**
 * @author shkstart
 * @create 2023-06-29 11:38
 */
public interface CreaterService {
    void classTableCreater(SocketAddress address, String className);
    void consumerTableCreater(SocketAddress address,String txtPath,int rows);

    void consumerTableCreater(SocketAddress address,String textPath);
}
