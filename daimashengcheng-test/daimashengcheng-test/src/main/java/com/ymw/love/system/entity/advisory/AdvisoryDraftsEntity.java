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
 * advisory drafts entity
 * @date 2019-08-08
 */
@Data
@TableName("u_advisory_drafts")
public class AdvisoryDraftsEntity extends Model<AdvisoryDraftsEntity> {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    @NotEmpty(message = "标题名称不能为空！")
    private String title;
    @NotEmpty(message = "内容不能为空!")
    private String content;
    @NotEmpty(message = "作者不能为空!")
    private String author;
    @NotEmpty(message = "类型不能为空!")
    private String category;

    private String newsImg;
    private LocalDateTime saveTime;
    private String coverImg;

    /**
     * 推荐状态 0、不推荐 1、推荐
     */
    private Integer recommendStatus;

    @TableField(exist = false)
    private String beginTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private Integer day = 0;

    @TableField(exist = false)
    private Integer pageSum = 1;

    @TableField(exist = false)
    private Integer pageSize = 10;
}
