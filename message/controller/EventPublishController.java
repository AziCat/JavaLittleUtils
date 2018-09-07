package cn.sinobest.jzpt.message.controller;

import cn.sinobest.jzpt.message.service.EventPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 文书审批完成后调用发送办案提醒入口
 *
 * @author yanjunhao
 * @date 2018年8月28日
 */
@RestController
@RequestMapping(value = "api/messages")
public class EventPublishController {
    @Autowired
    private EventPublishService eventPublishService;

    @RequestMapping(value = "/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<Void> refresh(@PathVariable("messageId") String messageId) throws Exception {
        eventPublishService.refresh(messageId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
