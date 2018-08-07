package rabbitmq.callback;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements ConfirmCallback {

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println("confirm--:correlationData:"+correlationData+",ack:"+ack+",cause:"+cause);
//		if (correlationData != null){
//			//更新缓存中CorrelationData值status的状态，置为true,说明查询成功
//			if (ack == true){
//
//			}else { //置为false,说明查询失败
//				//写日志记录失败原因:cause
//			}
//		}
	}

}
