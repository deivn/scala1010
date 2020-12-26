package com.ymw.love.system.entity.faq;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zjc
 * @version 1.1
 * u_advisory_comment_give
 * @date 2019-08-08
 */
@Data
@TableName("u_faq_comment_give")
public class CommentGiveEntity extends Model<CommentGiveEntity> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    private String commentId;

    private String userId;

    private LocalDateTime addTime;

}
