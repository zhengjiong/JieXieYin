package org.namofo.util;

/**
 * 
 * @author zhengjiong
 * 2014-7-4 上午7:43:13
 */
public abstract class SingletonUtils<T> {
    private T instance;

    protected abstract T newInstance();

    public final T getInstance() {
        if (instance == null) {
            synchronized (SingletonUtils.class) {
                if (instance == null) {
                    instance = newInstance();
                }
            }
        }
        return instance;
    }
}
