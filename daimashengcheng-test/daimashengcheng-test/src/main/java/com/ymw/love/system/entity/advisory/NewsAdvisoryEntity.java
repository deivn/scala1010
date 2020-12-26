package com.ymw.love.system.entity.advisory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author zjc
 * @version 1.1
 * advisory entity
 * @date 2019-08-08
 */
@Data
@TableName("u_news_advisory")
public class NewsAdvisoryEntity extends Model<NewsAdvisoryEntity> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    @NotEmpty(message = "标题名称不能为空！")
    private String newsTitle;
    /**
     * 咨询描述
     */
    @NotEmpty(message = "描述不能为空!")
    private String newsDescribe;
    private String newsImg;
    private String coverImg;
    @NotEmpty(message = "类型不能为空!")
    private String typeName;

    private Integer browseCount;

    private Integer countShare;
    /**
     * 审核状态：1,审核中，2，通过，3.失败
     */
    private Integer auditStatus;
    /**
     * 1，上架，2、下架
     */
    private Integer status;

    private String userId;

    private LocalDateTime addTime;

    private String author;

    private String auditor;

    private LocalDateTime auditTime;

    private String auditReason;
    /**
     * 推荐状态 0、不推荐 1、推荐
     */
    private Integer recommendStatus;

    @TableField(exist = false)
    private Integer commentCount;

    @TableField(exist = false)
    private Integer giveCount;

    @TableField(exist = false)
    private Integer collectCount;

    @TableField(exist = false)
    private String beginTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String str;

    @TableField(exist = false)
    private Integer day = 0;

    @TableField(exist = false)
    private Integer pageSum = 1;

    @TableField(exist = false)
    private Integer pageSize = 10;

    @TableField(exist = false)
    private Integer draftsStatus = 0;

    @TableField(exist = false)
    private String draftsId;
}
