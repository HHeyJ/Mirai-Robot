//package com.hyq.robot.autojob;
//
//import com.hyq.robot.DO.BarPostDO;
//import com.hyq.robot.DO.InformRelationDO;
//import com.hyq.robot.DO.PostLinkDO;
//import com.hyq.robot.constants.CommonConstant;
//import com.hyq.robot.dao.BarPostDAO;
//import com.hyq.robot.dao.InformRelationDAO;
//import com.hyq.robot.dao.PostLinkDAO;
//import com.hyq.robot.facade.TieBaFacade;
//import com.hyq.robot.helper.SendHelper;
//import com.hyq.robot.query.BarPostQuery;
//import com.hyq.robot.query.InformRelationQuery;
//import com.hyq.robot.query.PostLinkQuery;
//import com.hyq.robot.star.RobotStar;
//import com.hyq.robot.utils.MessageFilter;
//import net.mamoe.mirai.message.data.Message;
//import net.mamoe.mirai.message.data.PlainText;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.util.CollectionUtils;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author nanke
// * @date 2020/4/5 上午12:14
// */
//@Configuration
//@EnableScheduling
//public class CreeperTask {
//
//    @Resource
//    private PostLinkDAO postLinkDAO;
//    @Resource
//    private InformRelationDAO informRelationDAO;
//    @Resource
//    private BarPostDAO barPostDao;
//
//    @Scheduled(cron = "0 0/2 * * * ? ")
//    public void task() {
//
//        List<PostLinkDO> postLinkDOS = postLinkDAO.queryByCondition(new PostLinkQuery());
//
//        for (PostLinkDO postLinkDO : postLinkDOS) {
//            String linkUrl = postLinkDO.getLinkUrl();
//            try {
//                // 获取最大楼层
//                Long maxPageNo = TieBaFacade.queryPageNo(linkUrl,null,null);
//                // 落库最大楼层
//                BarPostQuery barPostQuery = BarPostQuery.builder().postUrl(linkUrl).build();
//                List<BarPostDO> maxFloorIdDOS = barPostDao.queryByCondition(barPostQuery);
//                // 5页一爬
//                int pageNo = CollectionUtils.isEmpty(maxFloorIdDOS) ? 1 :maxFloorIdDOS.get(0).getPageNo();
//                int stopPageNo = pageNo + 5 > maxPageNo ? maxPageNo.intValue() : pageNo + 5;
//                // 获取入库数据
//                List<Message> messageList = fallLibrary(pageNo,stopPageNo,linkUrl,null,null)
//                        .stream()
//                        .map(e -> "【" + postLinkDO.getAreaName() + "黑市播报】" + e.getFloorId() + "楼:" + e.getContent())
//                        .map(PlainText::new)
//                        .collect(Collectors.toList());
//                // 获取播报订阅群
//                InformRelationQuery informRelationQuery = new InformRelationQuery();
//                informRelationQuery.setPostLinkId(postLinkDO.getId());
//                List<InformRelationDO> informRelationDOS = informRelationDAO.queryByCondition(informRelationQuery);
//                // 广播
//                List<Long> groupIdList = informRelationDOS.stream().map(InformRelationDO::getGroupId).collect(Collectors.toList());
//                SendHelper.sendGroupBatch(RobotStar.bot,groupIdList,messageList);
//            } catch (Exception e) {
//                SendHelper.sendSing(RobotStar.bot.getFriend(CommonConstant.errorSendId),new PlainText("帖子[" + linkUrl +"]定时任务爬取失败" + e));
//            }
//        }
//    }
//
//    /**
//     * 单条帖子多页爬取落库
//     * @param pageNo
//     * @param stopPageNo
//     * @param postUrl
//     */
//    private List<BarPostDO> fallLibrary(int pageNo, int stopPageNo, String postUrl, String host, Integer port) {
//
//        List<BarPostDO> result = new ArrayList<>();
//        for (int i = pageNo; i <= stopPageNo; i++) {
//            // 当前页发言数据
//            Map<String, List<Object>> baseMap = TieBaFacade.handle(postUrl + "?pn=" + i,host,port);
//            List<Object> contentList = baseMap.get("contentList");
//            List<Object> floorList = baseMap.get("floorList");
//            // 过滤已经入库的
//            List<BarPostDO> dos = filter(wrapDO(i, postUrl,contentList, floorList));
//            if (!CollectionUtils.isEmpty(dos))
//                barPostDao.insertBatch(dos);
//            result.addAll(dos);
//        }
//        return result;
//    }
//
//    /**
//     * 过滤楼层入库最高楼层
//     * @param barPostDOS
//     * @return
//     */
//    private List<BarPostDO> filter(List<BarPostDO> barPostDOS) {
//
//        if (CollectionUtils.isEmpty(barPostDOS))
//            return new ArrayList<>();
//        // 过滤已经入库的
//        BarPostQuery query = BarPostQuery.builder().postUrl(barPostDOS.get(0).getPostUrl()).build();
//        List<BarPostDO> dos = barPostDao.queryByCondition(query);
//        if (CollectionUtils.isEmpty(dos)) {
//            return barPostDOS;
//        }
//        Long floorId = dos.get(0).getFloorId();
//        return barPostDOS.stream().filter(barPostDO -> barPostDO.getFloorId() > floorId).collect(Collectors.toList());
//    }
//
//    /**
//     * 包装入库信息
//     * @return
//     */
//    private List<BarPostDO> wrapDO(Integer pageNo, String postUrl, List<Object> contentList, List<Object> floorList) {
//
//        List<BarPostDO> result = new ArrayList<>();
//        for (int i = 0; i < contentList.size() && i < floorList.size(); i++) {
//
//            String content = (String) contentList.get(i);
//            if (!MessageFilter.filter(content)) {
//                continue;
//            }
//
//            BarPostDO barPostDO = new BarPostDO();
//            barPostDO.setPostUrl(postUrl);
//            barPostDO.setFloorId((Long) floorList.get(i));
//            barPostDO.setContent((String) contentList.get(i));
//            barPostDO.setPageNo(pageNo);
//            result.add(barPostDO);
//        }
//        return result;
//    }
//}
