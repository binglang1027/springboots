package com.example.demo2swagger2restful.web;

import com.example.demo2swagger2restful.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

/**
 * 作用说明：
 *
 * @author mirror.zhuyanpeng
 * @create 2017-08-24 1:01
 **/

@RestController
@RequestMapping("/users")
public class UserController {
    static Map<String, User> users = Collections.synchronizedMap(new HashMap<String,User>());
    @ApiOperation(value = "获得用户列表",notes = "")
    @GetMapping("")
    public List<User> getUserList(){
        List<User> users = new ArrayList<>(UserController.users.values());
        return users;
    }
    @ApiOperation(value = "创建用户",notes = "需要传入用户对象才能创建用户")
    @ApiImplicitParam(name = "user",value = "用户详细实体user",required =true,dataType = "User")
    @PostMapping("")
    public String postUser(@RequestBody User user){
        users.put(user.getId(),user);
        return "success";
    }

    @ApiOperation(value = "获取用户详细信息",notes ="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id",value = "用户Id",required = true,dataType = "String")
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id){
        return users.get(id);
    }
    @ApiOperation(value = "更新用户详细信息",notes ="根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id",value = "用户id",required = true,dataType = "String"),
                    @ApiImplicitParam(name="user",value = "用户详细实体user",required = true,dataType = "User")
            }
    )
    @PutMapping("/{id}")
    public String putUser(@PathVariable String id,@RequestBody User user){
        User user1 = users.get(id);
        user1.setName(user.getName());
        user1.setAge(user.getAge());
        return "success";
    }
    @ApiOperation(value = "删除用户",notes = "用户url指定的id来删除user")
    @ApiImplicitParam(name = "id",value = "用户Id",required = true,dataType = "String")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id){
        users.remove(id);
        return "success";
    }

    @ApiOperation("返回自定义的响应码")
    @ApiResponses({ @ApiResponse(code = 4211, message = "跳转到登陆"),
            @ApiResponse(code = 4212, message = "服务器内部异常"),
            @ApiResponse(code = 4213, message = "权限不足") })
    @PostMapping("/result")
    public String result(){
        return  null;
    }

    @ApiIgnore
    @GetMapping("/ignore")
    public String ignore(){
        return null;
    }
}
