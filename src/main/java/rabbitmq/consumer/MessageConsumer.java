package rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

@Service
public class MessageConsumer implements ChannelAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private static Integer i = 1;

    @Override
    public void onMessage(Message message, Channel channel) {
        byte[] bytes = message.getBody();
//        String body = new String(bytes);
        String data=null;
        HashMap hashMap = null;
        if (message.getMessageProperties().getContentType().equals("application/x-java-serialized-object")){
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                hashMap = (HashMap)objectInputStream.readObject();
                byteArrayInputStream.close();
                objectInputStream.close();
                data = (String) hashMap.get("data");

                //手动confirm
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

            }catch (IOException ex){
                ex.printStackTrace();
            }catch (ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }else if (message.getMessageProperties().getContentType().equals("text/plain")){
            data = new String(bytes);
        }
        logger.info("receive message:index:{} {}",i++,data);

    }

}
