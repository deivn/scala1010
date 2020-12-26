package com.ymw.love.system.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.entity.test.TestEntity;
import lombok.Data;

import java.util.Date;

/**
 * 数据库继承该基础类必须包含该类字段
 */
@Data
public class BaseEntity extends Model<TestEntity> {

    /** 创建人id */
    private Long creatorId;

    /** 更新人id */
    private Long updaterId;

    /** 创建时间 */
    private Date createTime;

    /** 创建更新时间 */
    private Date updateTime;

    public void buildTime() {
        Date date = new Date();
        this.setCreateTime(date);
        if(this.updateTime == null) {
            this.setUpdateTime(date);
        }
    }

    public void buildUpdateTime() {
        Date update = new Date();
        this.setUpdateTime(update);
    }
}
