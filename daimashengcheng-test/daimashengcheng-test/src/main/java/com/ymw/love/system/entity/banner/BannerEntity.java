package com.ymw.love.system.entity.banner;

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
 * sys_banner
 * @date 2019-08-016
 */
@Data
@TableName("sys_banner")
public class BannerEntity extends Model<BannerEntity> {
    private static final long serialVersionUID = 769415444749047663L;
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    @NotEmpty(message = "广告名称不能为空")
    private String bannerName;
    @NotEmpty(message = "广告描述不能为空")
    private String bannerDescribe;
    @NotEmpty(message = "广告图片不能为空")
    private String bannerImg;

    private Long bannerSort;
    @NotEmpty(message = "广告链接不能为空")
    private String bannerUrl;

    private Integer clickCount;

    private LocalDateTime addTime;

    private LocalDateTime sendTime;

    private LocalDateTime endTime;
    //0、待发布1.发布、2、停止
    private Integer status;

    @TableField(exist = false)
    private Integer pageSum = 1;
    @TableField(exist = false)
    private Integer pageSize = 20;
}
