package com.hpugs.movedata.modle;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        List<Integer> list = JSONObject.parseArray("[3,4,2,7,2,3,1]", Integer.class);
        System.out.println(JSONObject.toJSONString(list));
        list = list.stream().sorted(Comparator.comparingInt(i -> i)).collect(Collectors.toList());
        System.out.println(JSONObject.toJSONString(list));
    }

}
