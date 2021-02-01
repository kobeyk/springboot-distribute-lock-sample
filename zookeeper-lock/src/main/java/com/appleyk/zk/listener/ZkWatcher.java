package com.appleyk.zk.listener;

import com.appleyk.zk.utils.ZkApi1;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>实现watcher监听</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk/dubbo-spring-boot-sample
 * @date created on 11:36 2020/11/23
 */
public class ZkWatcher implements Watcher {

    private static final Logger logger = LoggerFactory.getLogger(ZkWatcher.class);
    private String data;
    private ZkApi1 zk;

    @Override
    public void process(WatchedEvent event) {
        logger.info("【Watcher监听事件】={}",event.getState());
        logger.info("【监听路径为】={}",event.getPath());
        if(zk == null){
            return;
        }
        String data = zk.getData(event.getPath(), this);
        if(!data.equals(this.data)){
            logger.info("节点数据更新了！！！！，更新后的值 = "+data);
        }
        //  三种监听类型： 创建，删除，更新
        logger.info("【监听的类型为】={}",event.getType());
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ZkApi1 getZk() {
        return zk;
    }

    public void setZk(ZkApi1 zk) {
        this.zk = zk;
    }
}
