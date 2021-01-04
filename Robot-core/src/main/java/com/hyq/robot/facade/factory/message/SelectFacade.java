package com.hyq.robot.facade.factory.message;

import com.hyq.robot.DO.BarPostDO;
import com.hyq.robot.DO.PostLinkDO;
import com.hyq.robot.constants.CommonConstant;
import com.hyq.robot.dao.BarPostDAO;
import com.hyq.robot.dao.PostLinkDAO;
import com.hyq.robot.enums.EnumKeyWord;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author nanke
 * @date 2020/7/14 下午8:52
 */
@Service
public class SelectFacade implements MessageFacade {

    @Override
    public EnumKeyWord get() {
        return EnumKeyWord.GROUP_SELECT;
    }

    @Resource
    private PostLinkDAO postLinkDAO;
    @Resource
    private BarPostDAO barPostDAO;

    @Override
    public void execute(Contact sender, Contact group, Message message) {

        // 指定官方查询群
        if (!CommonConstant.selectGroupId.equals(group.getId())) {
            SendHelper.sendSing(group,new PlainText("【查询】请加入查询群【839762931】进行查询。"));
            return ;
        }

        String content = message.contentToString();
        // 区服关键词 搜索关键词
        String areaKey = getAreaName(content);
        String selectKey = getSelectKey(content);
        if (StringUtils.isBlank(areaKey) || StringUtils.isBlank(selectKey)) {
            SendHelper.sendSing(group,new PlainText("【查询】错误 请参考:查询 双梦 xx"));
            return ;
        }
        // 查询帖子信息
        List<PostLinkDO> postLinkDOS = postLinkDAO.queryByCondition(new PostLinkQuery(areaKey));
        if (CollectionUtils.isEmpty(postLinkDOS)) {
            SendHelper.sendSing(group,new PlainText("【" + areaKey + "】服务器未同步。"));
            return ;
        }

        PostLinkDO postLinkDO = postLinkDOS.get(0);
        // 命中数据
        List<BarPostDO> hitData = getData(postLinkDO.getLinkUrl()).stream()
                .filter(e -> e.getContent().contains(selectKey))
                .collect(Collectors.toList());
        // 相同内容过滤
        hitData = hitData.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<BarPostDO>(Comparator.comparing(BarPostDO::getContent))),
                        ArrayList::new));

        String noDataContent = "【" + postLinkDO.getAreaName() + "】近2000楼未匹配到【" + selectKey + "】,换个词试试吧。";
        if (CollectionUtils.isEmpty(hitData)) {
            SendHelper.sendSing(group,new PlainText(noDataContent));
        } else {
            // 发送
            hitData.sort(Comparator.comparing(BarPostDO::getFloorId));
            if (hitData.size() > 20) {
                hitData = hitData.stream().skip(hitData.size() - 20).limit(200).collect(Collectors.toList());
            }
            hitData.forEach(e -> SendHelper.sendSing(group,
                    new PlainText("【" + postLinkDO.getAreaName() +"】" + e.getFloorId() + "楼:" + e.getContent())));
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
