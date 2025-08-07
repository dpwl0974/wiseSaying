package com.back;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//고객 요청 처리 클래스
public class Rq {

    private Map<String, String> paramMap;
    private String actionName;

    public Rq(String command) {
        paramMap = new HashMap<>();

        String[] commandBits = command.split("\\?");

        actionName = commandBits[0];
        /*String queryString = "";

        if (commandBits.length > 1) {
            queryString = commandBits[1];
        }*/
        String queryString = commandBits.length > 1 ? commandBits[1] : "";

        String[] queryStringBits = queryString.split("&");

        /*for (String param : queryStringBits) {
            String[] paramBits = param.split("=");
            String key = paramBits[0];
            String value = null;

            if (paramBits.length > 1) {
                value = paramBits[1];
            }

            if (value == null) {
                continue;
            }

            paramMap.put(key, value);
        }*/

        paramMap = Arrays.stream(queryStringBits) // key1=value1, key2=value2 ...
                .map(part -> part.split("="))
                .filter(bits -> bits.length == 2 && bits[0] != null && bits[1] != null)// [key1, value1]
                .collect(
                        Collectors.toMap(
                                bits -> bits[0],
                                bits -> bits[1]
                        )
                );
    }

    public String getActionName() {
        return actionName;
    }

    //함수마다 defaultValue 따로 사용 => 효율적
    public String getParam(String key, String defaultValue) {
        return paramMap.getOrDefault(key, defaultValue);
    }

    public int getParamAsInt(String key, int defaultValue) {
        String value = getParam(key, null);

        if(value == null) {
            return defaultValue;
        }

        return Integer.parseInt(value);
    }
}