package com.ncist.dataExchangePool.pool.service.Interface;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shkstart
 * @create 2023-06-29 11:35
 */
public interface FileCreaterService {
    void fileCreate(String className, int partNum);

    void dataWrite(String className, String meg, String id, long time);

    List dataRead(String className);

}
