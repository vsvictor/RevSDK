package com.rev.revsdk.protocols;

import java.util.List;

/**
 * Created by victor on 07.02.17.
 */

public class ProtocolTester {
    private List<String> list;
    private String url;
    private TestResult result;

    public ProtocolTester(List<String> protocols, String url){
        this.list = protocols;
        this.url = url;
    }

    public void test(){
        result.clear();
        for (String name: list){
            testOne(name);
        }
    }

    public TestResult getTest(){
        return  result;
    }

    private void testOne(String name){

    }
}
