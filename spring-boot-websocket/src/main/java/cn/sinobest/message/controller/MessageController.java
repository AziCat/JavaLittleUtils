package cn.sinobest.message.controller;

import cn.sinobest.base.util.HttpUtil;
import cn.sinobest.base.util.JsonUtil;
import cn.sinobest.message.domain.Message;
import cn.sinobest.message.domain.Result;
import cn.sinobest.message.service.IMessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *  消息控制器
 *  @author yjh
 *  @date 2017.09.08
 */
@RestController
@RequestMapping("/send")
public class MessageController {
    private Log logger = LogFactory.getLog(MessageController.class);
    private final IMessageService messageService;

    @Autowired
    public MessageController(@Qualifier("SimpleMessageService") IMessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseBody
    @RequestMapping("/sendMessage.action")
    public void sendMessage(HttpServletRequest request, HttpServletResponse response) {
        setResponse(response);
        Result result = new Result();
        try {
            String queryString = HttpUtil.getBodyData(request);
            try {
                Message message = JsonUtil.json2BeanWithNullCheck(queryString, Message.class, Message.getNotNullList());
                result = messageService.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e);
                setExceptionMsg(result, e);
            }
            returnResult(response, result);
            saveLog(request,result,queryString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存日志
     * @param request 请求实例
     * @param result 返回结果
     * @param queryString 参数
     */
    private void saveLog(HttpServletRequest request, Result result,String queryString) {
        String clientIP = request.getRemoteAddr();
        messageService.saveLog("SEND",queryString,clientIP,JsonUtil.bean2Json(result),result.getSystemid());
    }

    private void setExceptionMsg(Result result, Exception e) {
        result.setSuccess("0");
        result.setMsg(e.toString());
        result.setSystemid("");
    }
    private void setResponse(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8"); //设置编码格式
        response.setContentType("application/json");   //设置数据格式
    }

    private void returnResult(HttpServletResponse response, Object result) throws IOException {
        PrintWriter out;
        out = response.getWriter();
        out.print(JsonUtil.bean2Json(result)); //将json数据写入流中
        out.flush();
        out.close();
    }
}
