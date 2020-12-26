package com.ymw.love.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@ApiModel(description = "分页返回实体")
@Data
public class PageVO<T> implements Serializable {

    private static final long serialVersionUID = -659540923292151982L;

    @ApiModelProperty("当前页")
    private Integer current;

    @ApiModelProperty("分页大小")
    private Integer pageSize;

    @ApiModelProperty("总页数")
    private Integer pages;

    @ApiModelProperty("总记录")
    private Long total;

    @ApiModelProperty("数据")
    private List<T> records;

    public PageVO() {
    }

    public PageVO(Integer currentPage, Integer pageSize, Long totalCount, List<T> result) {
        this.current = currentPage;
        this.pageSize = pageSize;
        this.total = totalCount;
        this.records = result;
        if (currentPage != null && pageSize != null && totalCount != null) {
            this.total = totalCount % getPageSize() == 0 ? totalCount / getPageSize() : totalCount / getPageSize() + 1;
        }
    }
}
