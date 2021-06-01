package com.hyq.robot.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyq.robot.client.HttpClient;
import com.hyq.robot.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Date;

/**
 * @author nanke
 * @date 2021/6/1 上午10:49
 * 致终于来到这里的勇敢的人:
 * 永远不要放弃！永远不要对自己失望！永远不要逃走辜负了自己。
 * 永远不要哭啼！永远不要说再见！永远不要说慌来伤害目己。
 */
@Repository
public class OneEnglishRepository {

    public static final Gson JSON = new GsonBuilder().create();

    /**
     * 爱词霸每日一句
     * @return
     */
    public ICiBaBody iCiBa() {

        BasicNameValuePair data = new BasicNameValuePair("data", DateUtil.toYMD(new Date()));
        String s = HttpClient.sendGet("http://open.iciba.com/dsapi", Collections.singletonList(data));
        if (StringUtils.isBlank(s)) {
            s = "{\"content\":\"It isn't the big pleasures that count the most; it's making a great deal out of the little ones.\",\"note\":\"最重要的不是有大快乐，而是能充分享受小快乐。\"}";
        }
        ICiBaBody iCiBaBody = JSON.fromJson(s, ICiBaBody.class);
        return iCiBaBody;
    }

    @Data
    public static class ICiBaBody {

        /**
         * 英文
         */
        private String content;
        /**
         * 翻译
         */
        private String note;
        /**
         * 图片
         */
        private String fenxiang_img;
    }




}
