package com.hyq.robot.client;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 * @author nanke
 * @date 2020/3/31 上午12:30
 */
@Slf4j
public class CreeperClient {

    public static Document getHtmlDocument(String url,String proxyHost,Integer proxyPort) {

        WebClient webClient = new WebClient(BrowserVersion.CHROME);

        if (StringUtils.isNotBlank(proxyHost) && proxyPort != null) {
            ProxyConfig proxyConfig = new ProxyConfig();
            proxyConfig.setProxyHost(proxyHost);
            proxyConfig.setProxyPort(proxyPort);
            webClient.getOptions().setProxyConfig(proxyConfig);
        }
        // 超时时间
        webClient.getOptions().setTimeout(3000);
        // JS执行出错的时是否抛出异常
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        // HTTP的状态非200时是否抛出异常
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        // 是否启用CSS
        webClient.getOptions().setCssEnabled(false);
        // 是否启用JS
        webClient.getOptions().setJavaScriptEnabled(false);
        // 设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage page = null;
        try {
            // 尝试加载网页
            page = webClient.getPage(url);
        } catch (Exception e) {
            log.error("爬虫失败,URL地址:{}",url,e);
        } finally {
            webClient.close();
        }
        webClient.waitForBackgroundJavaScript(30000);
        // 直接将加载完成的页面转换成xml格式的字符串
        return page == null ? null : Jsoup.parse(page.asXml());
    }
}
