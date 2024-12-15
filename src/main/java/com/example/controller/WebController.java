package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.service.DoctorService;
import com.example.service.UserService;
import com.example.utils.AuthCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.example.common.enums.DataEnum.SESSION_KEY;

/**
 * 基础前端接口
 */
@Api(tags = "用户登录接口")
@RestController
public class WebController {
    private final Logger log = LoggerFactory.getLogger(WebController.class);
    @Resource
    private AdminService adminService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private UserService userService;

    @Resource
    AuthCodeUtils authCodeUtils;

    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录
     */
    @ApiOperation(value = "登录接口", notes = "传入用户信息")
    @PostMapping(value = "/login")
    public Result login(@RequestBody Account account, HttpServletRequest request) {
        String session = (String) request.getSession().getAttribute(SESSION_KEY.name());//
        log.info("=============================={}",session);
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        if(!session.equalsIgnoreCase(account.getVerifyCode())){
            return Result.error(ResultCodeEnum.INVALID_VERIFY_CODE);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            return Result.success(adminService.login(account));
        }
        if (RoleEnum.DOCTOR.name().equals(account.getRole())) {
            return Result.success(doctorService.login(account));
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            return Result.success(userService.login(account));
        }
        return Result.success();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.register(account);
        }
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        if (RoleEnum.DOCTOR.name().equals(account.getRole())) {
            doctorService.updatePassword(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }

    /**
     * 生成随机验证码
     * @return
     */
    @GetMapping(value = "/auth/code",produces = "application/json")
    public Result verifyCode(HttpServletRequest request){
        return Result.success(authCodeUtils.getRandomCodeBase64(request));
    }

}
