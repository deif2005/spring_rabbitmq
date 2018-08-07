import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {
    private static final Log log = LogFactory.getLog(TestMain.class);

    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/SpringContext.xml");
            context.start();
//            MessageSender messageSender = (MessageSender)context.getBean(MessageSender.class);
//            messageSender.sendMessage();
//
//            for (int i=0;i<1;i++){
//                MiuHandleService deviceHandleService = (MiuHandleService)context.getBean(MiuHandleService.class);
//                deviceHandleService.setDeviceReset("0108230001000010");
//                System.out.println("message index:" + i);
//            }
        } catch (Exception e) {
            log.error(e);
            return;
        }

        synchronized (TestMain.class) {
            while (true) {
                try {
                    TestMain.class.wait();
                } catch (InterruptedException e) {
                    log.error("== synchronized error:",e);
                }
            }
        }
    }
}
