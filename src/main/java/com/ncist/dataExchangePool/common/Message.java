package com.ncist.dataExchangePool.common;

import java.io.Serializable;

/**
 * @author 赖俊业
 * @create 2023-06-19 10:32
 * 表示生产者与交换池通讯时的消息对象
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1;
    private String sender;//发送者
    private String getter;//接收者
    private String id;//时间戳+八位随机数
    private long time;//时间窗口参数
    private String mesType;//消息类型
    private String content;//消息内容（json串）
    private String className;//消息（分区名称）
    private int partNum;//消息（分区数量）
    private String ack;//消息（交换池返回值）


    public Message() {
    }

    public Message(String sender, String getter, String id, long time, String mesType, String content, String className, int partNum, String ack) {
        this.sender = sender;
        this.getter = getter;
        this.id = id;
        this.time = time;
        this.mesType = mesType;
        this.content = content;
        this.className = className;
        this.partNum = partNum;
        this.ack = ack;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getPartNum() {
        return partNum;
    }

    public void setPartNum(int partNum) {
        this.partNum = partNum;
    }

    public String getAck() {
        return ack;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }
}