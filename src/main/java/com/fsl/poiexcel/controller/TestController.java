package com.fsl.poiexcel.controller;

import com.fsl.poiexcel.annotation.AccessLimit;
import com.fsl.poiexcel.annotation.ApiIdempotent;
import com.fsl.poiexcel.common.ServerResponse;
import com.fsl.poiexcel.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestService testService;

   /* @Autowired
    private AmqpTemplate rabbitTemplate;*/

   //测试接口幂等性   https://www.jianshu.com/p/6189275403ed
    @ApiIdempotent
    @PostMapping("testIdempotence")
    public ServerResponse testIdempotence() {
        return testService.testIdempotence();
    }

    @AccessLimit(maxCount = 5, seconds = 5)
    @PostMapping("accessLimit")
    public ServerResponse accessLimit() {
        return testService.accessLimit();
    }

}
