package com.ymw.love.system.service.sensitive;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.entity.sensitive.SensitiveEntity;
import com.ymw.love.system.mapper.sensitive.SensitiveMapper;
import com.ymw.love.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Sensitive words
 *
 * @author zjc
 * @date 2019年8月23日16:39:43
 */
@SuppressWarnings("ALL")
@Service
public class SensitiveImpl extends ServiceImpl<SensitiveMapper, SensitiveEntity> {

    @Autowired
    private SensitiveMapper sensitiveMapper;

    public Result findSensitiveList() {
        List<SensitiveEntity> sensitiveEntities = sensitiveMapper.selectList(null);
        return new Result(sensitiveEntities);
    }
    public Result findSensitiveList(SensitiveEntity sensitiveEntity) throws Exception {
        if (StringUtils.isEmpty(sensitiveEntity.getId())) {
            List<SensitiveEntity> entityList = sensitiveMapper.selectList(null);
            if (entityList.size() > 0) {
               throw new Exception("敏感词id、不能为空.");
            }
            sensitiveEntity.insert();
        } else {
            sensitiveEntity.updateById();
        }
        return new Result();
    }

    public List<SensitiveEntity> findLikeSensitive(String conent) {
        LambdaQueryWrapper<SensitiveEntity> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.like(SensitiveEntity::getSensitiveText,conent);
        List<SensitiveEntity> sensitiveEntities = sensitiveMapper.selectList(lambdaQueryWrapper);
        return sensitiveEntities;
    }

}
