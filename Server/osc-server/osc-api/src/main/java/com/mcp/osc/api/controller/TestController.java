package com.mcp.osc.api.controller;

import com.mcp.osc.domain.exception.BusinessException;
import com.mcp.osc.domain.exception.BusinessResultCode;
import com.mcp.osc.infrastructure.api.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author: KG
 * @description:
 * @date: Created in 下午5:12 2022/9/21
 * @modified by:
 */
@Slf4j
@Api(tags = "测试[Hello]")
@RestController
@RequestMapping("/test")
@ResponseResult
public class TestController {
    @ApiOperation(value = "Test Boolean Response")
    @ResponseBody
    @GetMapping(value = "/bool")
    public Boolean testBool() {
        log.info("Test Boolean Response...");

        return true;
    }

    /*@ApiOperation(value = "Test Login Response")
    @ResponseBody
    @PostMapping(value = "/login")
    public UserVo testLogin(@RequestBody UserForm userForm) {
        UserVo userVo = new UserVo();

        userVo.setUsername(userForm.getUsername());
        userVo.setPassword(userForm.getPassword());
        userVo.setMessage("Login Success");

        return userVo;
    }*/

    @ApiOperation(value = "Test Throw Exception")
    @ResponseBody
    @PostMapping(value = "/exception")
    public String testThrowException() {
        throw new BusinessException(BusinessResultCode.UPLOAD_FILE_EXCEED_LIMIT);
    }
}
