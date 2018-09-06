import java.net.InetAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class Main {
    public static void main(String [] args) throws Exception{

        Time_Translator first = new Time_Translator(System.currentTimeMillis());

        System.out.println("Data: " + first.getData() + " Time: " + first.getTimeStamp());
        for(int i = 0; i < 100; i++) {
            TimeUnit.SECONDS.sleep(1);

            first.updateDeviceTime(System.currentTimeMillis());
            System.out.println("Data: " + first.getData() + " TimeStamp: " + first.getTimeStamp() + " Converted Time: " + first.getGlobalTime(first.getData()));
        }
    }
}
