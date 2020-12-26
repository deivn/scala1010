package com.ymw.love.system.entity.test;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ymw.love.system.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("demo_test")
public class TestEntity extends BaseEntity {

    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    private String name;

    private Integer sex;

    private Integer age;
}
