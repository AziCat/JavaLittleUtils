package cn.sinobest.jzpt.message;

import cn.sinobest.jzpt.framework.security.SecurityUtil;
import cn.sinobest.jzpt.framework.util.ApplicationContextUtil;
import cn.sinobest.jzpt.framework.util.JsonUtils;
import cn.sinobest.jzpt.message.annotation.PathParameter;
import cn.sinobest.jzpt.message.config.InvocationHandlerFactory;
import cn.sinobest.jzpt.message.config.MethodMetadata;
import cn.sinobest.jzpt.message.domain.EventPublish;
import cn.sinobest.jzpt.message.service.EventPublishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 保存到消息表
 *
 * @author yanjunhao
 * @date 2018年9月3日
 */
public class EventPublishMethodHandler implements InvocationHandlerFactory.MethodHandler {
    private final Logger logger = LoggerFactory.getLogger(EventPublishMethodHandler.class);

    private MethodMetadata methodMetadata;
    private Class<?> type;

    EventPublishMethodHandler(MethodMetadata methodMetadata, Class<?> type) {
        this.methodMetadata = methodMetadata;
        this.type = type;
    }

    public MethodMetadata getMethodMetadata() {
        return methodMetadata;
    }

    public void setMethodMetadata(MethodMetadata methodMetadata) {
        this.methodMetadata = methodMetadata;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public static class Factory {
        public InvocationHandlerFactory.MethodHandler create(Class<?> type, MethodMetadata methodMetadata) {
            return new EventPublishMethodHandler(methodMetadata, type);
        }
    }

    @Override
    public Object invoke(Object[] argv) throws Throwable {
        //参数列表
        List<Object> argvList;
        if (null != argv) {
            argvList = new LinkedList<>(Arrays.asList(argv));
        } else {
            argvList = new LinkedList<>();
        }
        //检查元数据
        checkMetadata(this.methodMetadata);


        EventPublishService eventPublishService = (EventPublishService) ApplicationContextUtil.getBean(EventPublishService.class);
        Environment env = (Environment) ApplicationContextUtil.getBean(Environment.class);
        if (null == eventPublishService) {
            throw new NullPointerException("Bean[EventPublishService] have not found");
        }


        //保存到消息表
        EventPublish eventPublish = new EventPublish();
        eventPublish.setConsumes(methodMetadata.getConsumes());
        eventPublish.setDescription(methodMetadata.getKey());

        try {
            //格式化url
            String url = formatUrl(argvList, methodMetadata.getUrl(), methodMetadata.getPathParameterList());
            eventPublish.setUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


        //等待执行
        eventPublish.setEventStatus(EventPublishService.WAIT);
        eventPublish.setMaxRetry(methodMetadata.getMaxRetry());
        eventPublish.setMessageType(methodMetadata.getMessageType());
        eventPublish.setRequestHeadType(methodMetadata.getRequestType());

        //消息作用范围为http请求
        eventPublish.setScope(EventPublishService.SCOPE);

        //去除{}适配的参数后，检查接口中定义的方法必须有且只能有1个入参，或者没入参
        if (argvList.size() > 1) {
            throw new Exception("[" + methodMetadata.getKey() + "]should have at least one parameter and only one, or empty");
        }
        if (argvList.size() == 1) {
            //处理入参，只取第一个
            Object param = argvList.get(0);
            String paramStr;
            if (param instanceof String) {
                paramStr = (String) param;
            } else {
                paramStr = JsonUtils.object2Json(param);
            }
            eventPublish.setRequestParam(paramStr);
            eventPublish.setRequestParamType(param.getClass().toString());
        }

        String salt = env.getProperty("token.salt");
        eventPublish.setTokenSalt(TokenUtil.encode(SecurityUtil.getAccessToken(),
                StringUtils.isEmpty(salt) ? "default" : salt));
        eventPublishService.save(eventPublish);

        logger.info(this.type + ":" + methodMetadata.getKey() + " send message success");
        return null;
    }

    /**
     * 格式化url
     *
     * @param argvList
     * @param url
     * @return
     */
    private static String formatUrl(List<Object> argvList, String url, List<PathParameter> pathParameterList) throws Exception {
        List<Object> copyList = new ArrayList<>(argvList);
        for (int i = 0; i < copyList.size(); i++) {
            PathParameter pathParameter = pathParameterList.get(i);
            Object param = copyList.get(i);
            //获取PathParameter注解用于适配url中的{}
            if (null != pathParameter) {
                //判断参数是否String
                if (!(param instanceof String)) {
                    throw new Exception("@PathParameter is only allow use at String parameter");
                }
                String name = pathParameter.name();
                if (!StringUtils.isEmpty(name)) {
                    url = url.replace("{" + name + "}", (String) param);
                    //适配后的参数移除
                    argvList.remove(param);
                } else {
                    throw new Exception("@PathParameter.name is not allow empty");
                }
            }
        }
        //检查参数中是否还存在{}没有被替换
        String regEx = "\\{.*?}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(url);
        boolean validated = true;
        StringBuilder stringBuffer = new StringBuilder();
        while (m.find()) {
            stringBuffer.append(m.group()).append(" ");
            validated = false;
        }
        if (!validated) {
            throw new Exception(stringBuffer.toString() + " need declare with @PathParameter");
        }
        return url;
    }

    /**
     * 检查方法元数据
     *
     * @param methodMetadata
     */

    private void checkMetadata(MethodMetadata methodMetadata) throws NullPointerException {
        Assert.notNull(methodMetadata, "MethodMetadata must be not null");
        if (StringUtils.isEmpty(methodMetadata.getMessageType())) {
            throw new NullPointerException("@MessageClient:messageType must be not null");
        }
        if (StringUtils.isEmpty(methodMetadata.getUrl())) {
            throw new NullPointerException("@MessageRequestParam:url must be not null");
        }
    }
}
