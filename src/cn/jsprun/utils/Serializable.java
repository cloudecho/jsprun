package cn.jsprun.utils;
public interface Serializable {
    byte[] serialize();
    void unserialize(byte[] ss);
}