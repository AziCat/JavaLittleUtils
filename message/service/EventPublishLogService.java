/**
 * Created on Tue Sep 04 09:55:42 CST 2018
 * Copyright SinoBest, 2010-2011, All rights reserved.
 */
package cn.sinobest.jzpt.message.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.sinobest.jzpt.framework.service.BaseServiceImpl;
import cn.sinobest.jzpt.message.domain.EventPublishLog;
import cn.sinobest.jzpt.message.repository.EventPublishLogRepository;

/**
 * @Author yanjunhao
 * @Date Tue Sep 04 09:55:42 CST 2018
 * @TODO
 */

@Service("EventPublishLogService")
public class EventPublishLogService extends BaseServiceImpl<EventPublishLog> {

    private static final Log logger = LogFactory.getLog(EventPublishLogService.class);

    @Autowired
    private final EventPublishLogRepository dao;

    public EventPublishLogService(EventPublishLogRepository dao) {
        super(dao);
        this.dao = dao;
        logger.debug("EventPublishLogService with generic [dao:" + dao + "]");
    }

}
