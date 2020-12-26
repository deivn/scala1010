package com.ymw.love.system.entity.faq;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * @author zjc
 * @version 1.1
 * Q&a entity
 * @date 2019-08-02
 */
@TableName("u_faqs")
@Data
public class FaqEntity extends Model<FaqEntity> {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * Q&a content
     */
    @NotEmpty(message = "问答不能为空")
    private String issueContent;
    /**
     * Q&a pictures
     */
    private String contentImg;
    /**
     * Q&a type
     */
    @NotEmpty(message = "问答类型不能为空")
    private String typeName;
    /**
     * Release people id
     */
    private String userId;
    /**
     * Browse the number
     */
    private Integer browseCount;
    /**
     * Release time
     */
    private LocalDateTime addTime;

    /**
     * audit status 1,审核中，2，通过，3.失败
     */
    private Integer auditStatus;
    /**
     * status 1，上架，2、下架
     */
    private Integer status;
    /**
     * Share a number
     */
    private Integer countShare;
    /**
     * 1.是匿名、2.不是匿名
     */
    private Integer anonymityStatus;

    /**
     * 推荐状态 0、不推荐 1、推荐
     */
    private Integer recommendStatus;
    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String replyContent;
    @TableField(exist = false)
    private Integer replyCount;
    @TableField(exist = false)
    private Integer pageSum = 1;
    @TableField(exist = false)
    private Integer pageSize = 10;

    @TableField(exist = false)
    private Integer collectStatus;
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
    private Integer isRecommend;

    @TableField(exist = false)
    private String specialId;
    @TableField(exist = false)
    private String title;
    @TableField(exist = false)
    private String specialTitle;
    @TableField(exist = false)
    private String parentId;

}
