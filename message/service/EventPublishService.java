/**
 * Created on Tue Sep 04 09:55:41 CST 2018
 * Copyright SinoBest, 2010-2011, All rights reserved.
 */
package cn.sinobest.jzpt.message.service;

import cn.sinobest.jzpt.framework.security.SecurityUtil;
import cn.sinobest.jzpt.message.TokenUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import cn.sinobest.jzpt.framework.service.BaseServiceImpl;
import cn.sinobest.jzpt.message.domain.EventPublish;
import cn.sinobest.jzpt.message.repository.EventPublishRepository;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @Author yanjunhao
 * @Date Tue Sep 04 09:55:41 CST 2018
 * @TODO
 */

@Service("EventPublishService")
public class EventPublishService extends BaseServiceImpl<EventPublish> {
    public static final String WAIT = "2";
    public static final String SCOPE = "HTTP";
    private static final Log logger = LogFactory.getLog(EventPublishService.class);

    @Autowired
    private final EventPublishRepository dao;
    @Autowired
    private Environment env;

    public EventPublishService(EventPublishRepository dao) {
        super(dao);
        this.dao = dao;
        logger.debug("EventPublishService with generic [dao:" + dao + "]");
    }

    /**
     * 重置消息
     *
     * @param messageId
     */
    public void refresh(String messageId) {
        EventPublish eventPublish = this.getBySystemid(messageId);
        Assert.notNull(eventPublish, messageId + " can not match any record");
        //更新状态为等待执行
        eventPublish.setEventStatus(WAIT);
        //清空重试次数
        eventPublish.setRetriedCount(null);
        //更新token信息
        String salt = env.getProperty("token.salt");
        eventPublish.setTokenSalt(TokenUtil.encode(SecurityUtil.getAccessToken(),
                StringUtils.isEmpty(salt) ? "default" : salt));
        save(eventPublish);
    }
}
