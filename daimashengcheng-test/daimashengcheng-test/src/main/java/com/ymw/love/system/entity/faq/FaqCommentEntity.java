package com.ymw.love.system.entity.faq;

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
 * Q&a comment entity
 * @date 2019-08-02
 */
@TableName("u_faq_comment")
@Data
public class FaqCommentEntity extends Model<FaqCommentEntity> {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * comment content
     */
    private String commentContent;

    private String faqId;

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


}
