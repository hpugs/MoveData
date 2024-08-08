package com.hpugs.movedata;

import com.hpugs.base.common.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WebStatus {

    @RequestMapping(value = "/webStatus", method = {RequestMethod.GET, RequestMethod.HEAD})
    @ResponseBody
    public Result welcome(HttpServletRequest servletRequest) {
        return Result.buildSuccess();
    }

}
