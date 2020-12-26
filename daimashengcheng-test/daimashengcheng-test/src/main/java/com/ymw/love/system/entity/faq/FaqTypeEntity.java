package com.ymw.love.system.entity.faq;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zjc
 * @version 1.1
 * Q&a type entity
 * @date 2019-08-02
 */
@TableName("u_faq_type")
@Data
public class FaqTypeEntity extends Model<FaqTypeEntity> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * type name
     */
    private String typeName;
    /**
     * type status 1,启用 2，禁用
     */
    private Integer typeStatus;

    /**
     * add date
     */
    private LocalDateTime addTime;


    @TableField(exist = false)
    private Integer pageSum = 1;
    @TableField(exist = false)
    private Integer pageSize = 10;


}
