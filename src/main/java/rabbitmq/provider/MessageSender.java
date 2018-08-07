package rabbitmq.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MessageSender {

    private Logger logger = LoggerFactory.getLogger(MessageSender.class);

    @Value("${mq.queue}")
    private String queueId;
    @Value("${mq.exchange}") //@Value("#{startConf['mq.exchange']}")
    private String exchange;
    @Value("${mq.routekey}")
    private String routekey;

    @Autowired
    private Producer producer;

    public void sendMessage(){
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", "hello rabbitmq");
            String msg = "no serialized message";
            // 注意：第二个属性是 Queue 与 交换机绑定的路由
            producer.sendQueue( exchange, routekey, msg);
            System.out.println("发送完毕");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
