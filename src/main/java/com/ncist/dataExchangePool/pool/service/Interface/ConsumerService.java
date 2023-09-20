package com.ncist.dataExchangePool.pool.service.Interface;

import java.net.Socket;
import java.util.List;

public interface ConsumerService {
    int send(Socket socket, List list, String mesType);
}
