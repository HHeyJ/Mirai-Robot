package com.hyq.robot.constants;

/**
 * @author nanke
 * @date 2020/4/2 下午9:01
 */
public interface CommonConstant {

    /**
     * 错误反馈QQ
     */
    Long errorSendId = 1154685452L;
    /**
     * 通知QQ群
     */
    Long selectGroupId = 839762931L;
    /**
     * 排期指令介绍文档图片
     */
    String explainImg = "https://oss.jx3box.com/upload/post/2020/12/31/3835161.jpg";
    /**
     * 空白图片
     */
    String blankImg = "https://oss.jx3box.com/upload/post/2020/12/30/1901382.png";
    /**
     * 排期HTML
     */
    String htmlStr = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "    <!-- CSS goes in the document HEAD or added to your external stylesheet -->\n" +
            "    <style type=\"text/css\">\n" +
            "        table.one {\n" +
            "            font-family: verdana, arial, sans-serif;\n" +
            "            font-size: 11px;\n" +
            "            color: #333333;\n" +
//            "            border-width: 1px;\n" +
//            "            border-color: #666666;\n" +
            "            border-collapse: collapse;\n" +
            "        }\n" +
            "\n" +
            "        table.one th {\n" +
//            "            border-width: 1px;\n" +
            "            padding: 8px;\n" +
//            "            border-style: solid;\n" +
//            "            border-color: #666666;\n" +
            "            background-color: #dedede;\n" +
            "            width: 100px;\n" +
            "            height: 30px;\n" +
            "        }\n" +
            "\n" +
            "        table.one td {\n" +
//            "            border-width: 1px;\n" +
            "            padding: 8px;\n" +
//            "            border-style: solid;\n" +
//            "            border-color: #666666;\n" +
            "            width: 60px;\n" +
            "            height: 30px;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "    </style>\n" +
            "\n" +
            "    <title>?????????????</title>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "    <div>\n" +
            "        <table class=\"one\">\n" +
            "            <tr><th colspan=\"5\">开团标题</th></tr>\n" +
            "            <tr>\n" +
            "                <th>一队</th><th>二队</th><th>三队</th><th>四队</th><th>五队</th>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片11\" />\n" +
            "                    </div>\n" +
            "                    <span>成员11</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片21\" />\n" +
            "                    </div>\n" +
            "                    <span>成员21</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片31\" />\n" +
            "                    </div>\n" +
            "                    <span>成员31</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片41\" />\n" +
            "                    </div>\n" +
            "                    <span>成员41</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片51\" />\n" +
            "                    </div>\n" +
            "                    <span>成员51</span>\n" +
            "                </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片12\" />\n" +
            "                    </div>\n" +
            "                    <span>成员12</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片22\" />\n" +
            "                    </div>\n" +
            "                    <span>成员22</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片32\" />\n" +
            "                    </div>\n" +
            "                    <span>成员32</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片42\" />\n" +
            "                    </div>\n" +
            "                    <span>成员42</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片52\" />\n" +
            "                    </div>\n" +
            "                    <span>成员52</span>\n" +
            "                </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片13\" />\n" +
            "                    </div>\n" +
            "                    <span>成员13</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片23\" />\n" +
            "                    </div>\n" +
            "                    <span>成员23</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片33\" />\n" +
            "                    </div>\n" +
            "                    <span>成员33</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片43\" />\n" +
            "                    </div>\n" +
            "                    <span>成员43</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片53\" />\n" +
            "                    </div>\n" +
            "                    <span>成员53</span>\n" +
            "                </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片14\" />\n" +
            "                    </div>\n" +
            "                    <span>成员14</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片24\" />\n" +
            "                    </div>\n" +
            "                    <span>成员24</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片34\" />\n" +
            "                    </div>\n" +
            "                    <span>成员34</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片44\" />\n" +
            "                    </div>\n" +
            "                    <span>成员44</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片54\" />\n" +
            "                    </div>\n" +
            "                    <span>成员54</span>\n" +
            "                </td>\n" +
            "            </tr>\n" +
            "            <tr>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片15\" />\n" +
            "                    </div>\n" +
            "                    <span>成员15</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片25\" />\n" +
            "                    </div>\n" +
            "                    <span>成员25</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片35\" />\n" +
            "                    </div>\n" +
            "                    <span>成员35</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片45\" />\n" +
            "                    </div>\n" +
            "                    <span>成员45</span>\n" +
            "                </td>\n" +
            "                <td bgcolor=#FFFFFF>\n" +
            "                    <div>\n" +
            "                        <img src=\"图片55\" />\n" +
            "                    </div>\n" +
            "                    <span>成员55</span>\n" +
            "                </td>\n" +
            "            </tr>\n" +
            "        </table>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>";

}
