package com.ncist.dataExchangePool.pool.producerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author shkstart
 * @create 2023-06-17 15:51
 */
public class PoolMain {
    public static void main(String[] args) throws IOException {

        System.out.println("数据交换池连接成功");

        //创建serverSocket对象，同时为服务器注册端口
        ServerSocket serverSocket = new ServerSocket(6886);

        //创建线程池负责通信管道的任务
        ThreadPoolExecutor pool = new ThreadPoolExecutor(20 * 2, //核心线程数
                20 * 2,//最大线程数
                0, //最大空闲时间
                TimeUnit.SECONDS,//时间单位
                new ArrayBlockingQueue<>(8), //阻塞队列
                Executors.defaultThreadFactory(), //线程工程
                new ThreadPoolExecutor.AbortPolicy());//拒绝策略

        while (true) {
            //使用serverSocket对象调用accept方法等待客户端连接
            Socket socket = serverSocket.accept();

            System.out.println("有客户端上线：" + socket.getRemoteSocketAddress());



            //把通信管道交给独立的线程处理

                pool.execute(new PPoolReaderRunnable(socket));

        }

    }
}
