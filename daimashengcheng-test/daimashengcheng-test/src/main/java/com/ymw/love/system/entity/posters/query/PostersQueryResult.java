package com.ymw.love.system.entity.posters.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("海报列表查询结果封装")
@Getter
@Setter
public class PostersQueryResult {

    @ApiModelProperty("序列化")
    private Integer seq;

    @ApiModelProperty("海报ID")
    private String id;

    @ApiModelProperty("海报名称")
    private String postersName;

    @ApiModelProperty("海报描述")
    private String postersDesc;

    @ApiModelProperty("海报图片")
    private String postersImg;

    @ApiModelProperty("发布状态 1待发布  2已发布")
    private Integer publishStatus;

    @ApiModelProperty("发布时间, yyyy-MM-dd HH:mm:ss")
    private String publishTime;

    @ApiModelProperty("微信分享次数统计")
    private Integer wechat;

    @ApiModelProperty("朋友圈分享次数统计")
    private Integer friends;

    @ApiModelProperty("qq分享次数统计")
    private Integer qq;

    @ApiModelProperty("分享次数统计")
    private String sharesList;

    @ApiModelProperty("可操作标识 4 发布  5停止发布")
    private Integer operable;
}
