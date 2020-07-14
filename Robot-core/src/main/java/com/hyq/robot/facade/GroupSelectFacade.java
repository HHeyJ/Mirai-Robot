package com.hyq.robot.facade;

import com.hyq.robot.DO.BarPostDO;
import com.hyq.robot.DO.PostLinkDO;
import com.hyq.robot.dao.BarPostDAO;
import com.hyq.robot.dao.PostLinkDAO;
import com.hyq.robot.helper.SendHelper;
import com.hyq.robot.query.BarPostQuery;
import com.hyq.robot.query.PostLinkQuery;
import com.hyq.robot.utils.MessageUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/7/14 下午8:52
 */
@Service
public class GroupSelectFacade {

    @Resource
    private PostLinkDAO postLinkDAO;
    @Resource
    private BarPostDAO barPostDAO;

    public void execute(Contact contact, Message message) {
        String content = message.contentToString();

        // 区服关键词 搜索关键词
        String areaKey = getAreaName(content);
        String selectKey = getSelectKey(content);
        if (StringUtils.isBlank(areaKey) || StringUtils.isBlank(selectKey)) {
            SendHelper.sendSing(contact,new PlainText("【黑市查询】查询命令错误,请参考:查询？双梦？xx"));
            return ;
        }
        // 查询帖子信息
        List<PostLinkDO> postLinkDOS = postLinkDAO.queryByCondition(new PostLinkQuery(areaKey));
        if (CollectionUtils.isEmpty(postLinkDOS)) {
            SendHelper.sendSing(contact,new PlainText("【" + areaKey + "】服务器未登记在案,请联系群主哈～"));
        }

        PostLinkDO postLinkDO = postLinkDOS.get(0);
        // 命中数据
        List<BarPostDO> hitData = getData(postLinkDO.getLinkUrl()).stream()
                .filter(e -> e.getContent().contains(selectKey))
                .collect(Collectors.toList());
        // 相同内容过滤
        hitData = hitData.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<BarPostDO>(Comparator.comparing(e -> e.getContent()))),
                        ArrayList::new));

        String noDataContent = "【" + postLinkDO.getAreaName() + "黑市】" + "近2000楼未匹配到【" + selectKey + "】,换个词试试呢～";
        if (CollectionUtils.isEmpty(hitData)) {
            SendHelper.sendSing(contact,new PlainText(noDataContent));
        } else {
            // 发送
            Collections.sort(hitData,Comparator.comparing(BarPostDO::getFloorId));
            hitData.forEach(e -> SendHelper.sendSing(contact,
                    new PlainText("【" + postLinkDO.getAreaName() +"黑市】" + e.getFloorId() + "楼:" + e.getContent())));
        }
    }

    /**
     * 获取区服名
     * @param content
     * @return
     */
    public String getAreaName(String content) {
        return MessageUtil.getKeybyWord(content,2);
    }

    /**
     * 获取搜索关键词
     * @param content
     * @return
     */
    public String getSelectKey(String content) {
        return MessageUtil.getKeybyWord(content, 3);
    }

    /**
     * 获取最近1000条数据
     * @param postUrl
     * @return
     */
    private List<BarPostDO> getData(String postUrl) {
        BarPostQuery query = new BarPostQuery();
        query.setPostUrl(postUrl);
        query.setPageSize(2000);
        return barPostDAO.queryByCondition(query);
    }
}
