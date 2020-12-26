package com.ymw.love.system.controller.admin.sensitive;

import com.ymw.love.system.common.Result;
import com.ymw.love.system.entity.sensitive.SensitiveEntity;
import com.ymw.love.system.service.sensitive.SensitiveImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Sensitive words
 * @author zjc
 * @date 2019年8月23日16:39:43
 */
@RestController
@RequestMapping("/admin")
public class SensitiveConntroller {

    @Autowired
    private SensitiveImpl sensitive;

    @GetMapping("/sensitive")
    public Result findSensitiveList(){
         return sensitive.findSensitiveList();
    }

    @PostMapping("/sensitive/ins/upd")
    public Result findSensitiveInsAndUpd(@RequestBody SensitiveEntity sensitiveEntity) throws Exception {
        return sensitive.findSensitiveList(sensitiveEntity);
    }


}
