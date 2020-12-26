package com.ymw.love.system.entity.advisory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author zjc
 * @version 1.1
 * advisory comment entity
 * @date 2019-08-02
 */
@TableName("u_advisory_comment")
@Data
public class AdvisoryCommentEntity extends Model<AdvisoryCommentEntity> {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * comment content
     */
    private String commentContent;

    private String advisoryId;

    private String userId;

    private String parentId;

    private LocalDateTime addTime;
    /**
     * give a like count
     */
    private Integer likeCount;
    /**
     * state 1.启用 2.禁用
     */
    private Integer status;
    @TableField(exist = false)

    private Map<String, Object> zjReply;
    @TableField(exist = false)

    private String replyContent;
    @TableField(exist = false)

    private Integer replyCount;
    @TableField(exist = false)
    private Integer pageSum = 1;

    @TableField(exist = false)
    private Integer pageSize = 10;


    @TableField(exist = false)
    private String beginTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private Integer day = 0;

    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String str;



}
