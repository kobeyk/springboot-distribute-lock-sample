package com.appleyk.db.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Appleyk
 * @since 2020-10-21
 */
@TableName("t_resource_lock")
public class ResourceLock implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 资源名称
     */
    private String resourcName;

    /**
     * 持有该资源的线程名称
     */
    private String threadName;

    /**
     * 持有该资源的线程所在的服务地址
     */
    private String serverAddress;

    /**
     * 创建资源锁的时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourcName() {
        return resourcName;
    }

    public void setResourcName(String resourcName) {
        this.resourcName = resourcName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TResourceLock{" +
        "id=" + id +
        ", resourcName=" + resourcName +
        ", threadName=" + threadName +
        ", serverAddress=" + serverAddress +
        ", createTime=" + createTime +
        "}";
    }
}
