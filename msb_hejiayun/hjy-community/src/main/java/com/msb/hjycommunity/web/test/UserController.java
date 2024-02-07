package com.msb.hjycommunity.web.test;

import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.exception.BaseException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    // http://localhost:8888/hejiayun/user/queryUserById?userId=10
    @RequestMapping("/queryUserById")
    public BaseResponse<User> queryUserById(String userId) {
        if (userId != null) {
            return BaseResponse.success(new User(userId, "spike"));
        } else {
            return BaseResponse.fail("500", "查询用户信息失败！！");
        }
    }

    @RequestMapping("/addUser")
    // good, http://localhost:8888/hejiayun/user/addUser?userId=10
    // bad, http://localhost:8888/hejiayun/user/addUser
    // BindingResult来源于spring-boot-starter-validation
    public BaseResponse addUser(@Validated User user, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        // error不是空的话，就是校验失败
        // 如果参数校验失败，会将错误信息封装成对象组装到BindingResult
        if (!fieldErrors.isEmpty()) {
            return BaseResponse.fail("500", fieldErrors.get(0).getDefaultMessage());
        }
        return BaseResponse.success("OK......");
    }

    @RequestMapping("/queryUser")
    public BaseResponse queryUser(User user) {
        // 和GlobalExceptionHandler一起用
        throw new BaseException("500", "测试异常类！！！");
    }
}
