package rabbitmq.provider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rmqpTemplate;

    /**
     * 发送消息
     * @param exchangeKey
     * @param queueKey
     * @param object
     */
    public void sendQueue(String exchangeKey, String queueKey, Object object) {
        // convertAndSend 将Java对象转换为消息发送至匹配key的交换机中Exchange
        amqpTemplate.convertAndSend(exchangeKey, queueKey, object);

    }

    /**
     * 发送消息关联id
     * @param exchangeKey
     * @param queueKey
     * @param obj
     */
    public void sendById(String exchangeKey, String queueKey, Object obj) {
        String msgId = UUID.randomUUID().toString();
        Message message = MessageBuilder.withBody(obj.toString().getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setCorrelationId(msgId).build();
        CorrelationData date = new CorrelationData(msgId);
        // TODO 将 msgId 与 message 的关系保存起来,例如放到缓存中
        rmqpTemplate.send(exchangeKey, queueKey, message, date);
        //将msgid和message操作指令匹配存储后，用于获取该操作的返回值信息
        //存储字符格式为key:msgid+optId value:status,status未confirm之前为false

    }
}
