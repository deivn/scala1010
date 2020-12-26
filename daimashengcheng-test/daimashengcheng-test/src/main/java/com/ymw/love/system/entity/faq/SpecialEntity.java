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
 * u_special entity
 * @date 2019-08-02
 */
@TableName("u_special")
@Data
public class SpecialEntity extends Model<SpecialEntity> {


    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String coverImg;

    private String title;

    private Integer sort;

    private LocalDateTime addTime;

    private String faqId;

    private String parentId;

    @TableField(exist = false)
    private String replyContent;
    @TableField(exist = false)
    private Integer replyCount;
    @TableField(exist = false)
    private Integer pageSum = 1;
    @TableField(exist = false)
    private Integer pageSize = 20;
    @TableField(exist = false)
    private Integer specialCount;
    @TableField(exist = false)
    private Integer browseCount;
    @TableField(exist = false)
    private String issueContent;
    @TableField(exist = false)
    private Integer commentCount;
    @TableField(exist = false)
    private Integer recordCount;
}
