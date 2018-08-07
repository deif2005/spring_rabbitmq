
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rabbitmq.provider.Producer;

import java.util.List;

@Service
public class HandleService {

    @Value("${mq.queue}")
    private String queueId;
    @Value("${mq.exchange}")
    private String exchange;
    @Value("${mq.routekey}")
    private String routekey;

    @Autowired
    private Producer producer;

    public void getVersion(String deviceId){
        deviceId = "1234567890";
        producer.sendById(exchange,routekey,deviceId);
    }
}
