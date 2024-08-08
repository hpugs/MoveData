package com.hpugs.movedata.web;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.hpugs.base.common.result.EnumError;
import com.hpugs.base.common.result.Result;
import com.hpugs.base.common.utils.DateUtils;
import com.hpugs.base.common.utils.HttpProxyUtil;
import com.hpugs.base.common.utils.StringUtils;
import com.hpugs.movedata.modle.WWXReq;
import com.hpugs.movedata.modle.WWXResp;
import com.hpugs.movedata.modle.excel.CustomerResp;
import com.hpugs.movedata.modle.excel.ExternalUserDTO;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("/wwx")
public class WWXData {

    private static String fileName = "classpath:excel/";

    private static final HttpProxyUtil httpProxyUtil = new HttpProxyUtil("106.225.204.116", 50004, "9zpybu", "8jfa6sck");

    @PostMapping("/getExternalUser")
    public Result getExternalUser(@RequestBody WWXReq wwxReq) {
        String accessToken = wwxReq.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return Result.buildFail(EnumError.PARAMS_ERROR);
        }

        Map<String, String> map = new HashMap<>();
        map.put("", "");

        for (String userId : map.keySet()){
            queryExternalUser(accessToken, userId, map.get(userId));
        }

        return Result.buildSuccess();
    }

    private static Result queryExternalUser(String accessToken, String originalUserId, String newUserId){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/list?access_token=ACCESS_TOKEN&userid=USERID";
        url = url.replace("ACCESS_TOKEN", accessToken).replace("USERID", originalUserId);

        try {
            String str = httpProxyUtil.get(url);
            WWXResp wwxResp = JSONObject.parseObject(str, WWXResp.class);
            if (!"ok".equalsIgnoreCase(wwxResp.getErrmsg())) {
                return Result.buildFail(wwxResp.getErrcode(), wwxResp.getErrmsg());
            }

            List<String> external_useridList = wwxResp.getExternal_userid();
            if (CollectionUtils.isEmpty(external_useridList)) {
                return Result.buildSuccess(wwxResp);
            }

            // 剩余的客户继承给对应的企微承接号
            executeInheritance(accessToken, originalUserId, newUserId, external_useridList);

            return Result.buildSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.buildFail(EnumError.DEFAULT_ERROR);
    }

    private static List<CustomerResp> executeInheritance(String accessToken,
                                                         String originalUserId,
                                                         String newUserId,
                                                         List<String> externalUserIdList) {
        List<CustomerResp> customerRespList = new ArrayList<>();

        String url = "https://qyapi.weixin.qq.com/cgi-bin/externalcontact/transfer_customer?access_token=ACCESS_TOKEN";
        url = url.replace("ACCESS_TOKEN", accessToken);

        Map date = new HashMap(3);
        date.put("handover_userid", originalUserId);
        date.put("takeover_userid", newUserId);

        int size = externalUserIdList.size();
        int sizeCount = 100;

        int count = size / sizeCount + 1;
        for (int i = 0; i < count; i++) {
            List<String> list = new ArrayList();
            for (int j = 0; j < sizeCount; j++) {
                int index = sizeCount * i + j;
                if (index >= size) {
                    break;
                }
                list.add(externalUserIdList.get(index));
            }

            if (CollectionUtils.isEmpty(list)) {
                break;
            }

            date.put("external_userid", list);

            try {
                String str = httpProxyUtil.postJson(url, JSONObject.toJSONString(date));
                WWXResp wwxResp = JSONObject.parseObject(str, WWXResp.class);
                if (!"ok".equalsIgnoreCase(wwxResp.getErrmsg())) {
                    customerRespList.addAll(list.stream().map(id -> new CustomerResp(id, -2)).collect(Collectors.toList()));
                } else {
                    customerRespList.addAll(wwxResp.getCustomer());
                }
            } catch (IOException e) {
                customerRespList.addAll(list.stream().map(id -> new CustomerResp(id, -3)).collect(Collectors.toList()));
            }
        }

        return customerRespList;
    }

}
