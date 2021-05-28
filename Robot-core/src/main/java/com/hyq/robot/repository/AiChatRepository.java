package com.hyq.robot.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyq.robot.client.HttpClient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

/**
 * @author nanke
 * @date 2021/5/28 上午11:07
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Repository
public class AiChatRepository {

    public static final Gson JSON = new GsonBuilder().create();

    /**
     * 青云客
     * @param key
     * @return
     */
    public String qinYun(String key) {

        BasicNameValuePair var1 = new BasicNameValuePair("key", "free");
        BasicNameValuePair var2 = new BasicNameValuePair("msg", key);

        String s = HttpClient.sendGet("http://api.qingyunke.com/api.php", Arrays.asList(var1, var2));
        if (StringUtils.isBlank(s)) {
            return "嗯嗯嗯嗯嗯";
        }
        QinYunBody qinYunBody = JSON.fromJson(s, QinYunBody.class);
        return qinYunBody.getContent().replace("{br}","\n");
    }

    @Data
    public static class QinYunBody {

        /**
         * 内容,返回结果中{br}表示换行，请自行替换成需要的代码。
         */
        private String content;
    }



}
