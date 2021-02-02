package com.appleyk.zk.controller;

import com.appleyk.common.result.Result;
import com.appleyk.zk.utils.ZkApi2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

/**
 * <p>zk节点操作接口</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 15:36 2021/2/2
 */
@RestController
@RequestMapping("/zk")
@Api(tags = "2、ZK节点操作相关接口")
public class ZkController {

    @Autowired
    private CuratorFramework curatorClient;


    @ApiOperation("创建节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "节点路径", required = true),
            @ApiImplicitParam(name = "data", value = "节点内容", defaultValue = "我是谁")
    })
    @GetMapping("/node/create")
    public Result createNode(String path,String data){
        ZkApi2.create(curatorClient,path,data.getBytes(Charset.defaultCharset()));
        return Result.ok("节点创建成功");
    }

    @ApiOperation("创建临时节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "节点路径", required = true),
            @ApiImplicitParam(name = "data", value = "节点内容", defaultValue = "我是谁")
    })
    @GetMapping("/ephemeral/node/create/")
    public Result createENode(String path,String data){
        ZkApi2.createEphemeral(curatorClient,path,data.getBytes(Charset.defaultCharset()));
        return Result.ok("节点创建成功");
    }

    @ApiOperation("创建临时顺序节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "节点路径",required = true),
            @ApiImplicitParam(name = "data", value = "节点内容", defaultValue = "我是谁")
    })
    @GetMapping("/ephemeral/sequential/node/create")
    public Result createESNode(String path,String data){
        ZkApi2.createEphemeralSequential(curatorClient,path,data.getBytes(Charset.defaultCharset()));
        return Result.ok("节点创建成功");
    }

    @ApiOperation("获取（路径）节点的值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "节点的完整路径", required = true)
    })
    @GetMapping("/node/val/fetch")
    public Result getNodeValue(String path){
        return Result.ok(ZkApi2.getNodeValue(curatorClient,path));
    }

    @ApiOperation("获取父路径下的所有子节点（node列表）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "父目录",required = true)
    })
    @GetMapping("/path/child")
    public Result getChildren(String path){
        return Result.ok(ZkApi2.getPathChildren(curatorClient,path));
    }

    @ApiOperation("删除指定path的节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "path", value = "要删除的节点路径",required = true)
    })
    @DeleteMapping("/node/delete")
    public Result delete(String path){
        ZkApi2.delete(curatorClient,path);
        return Result.ok("删除节点成功！");
    }

}
