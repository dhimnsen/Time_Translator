import org.apache.commons.net.ntp.NTPUDPClient;

import java.net.InetAddress;

public class ATS_Base<T> {

    private long timeStamp = 0;
    private T data;
    protected NTPUDPClient timeClient;
    protected InetAddress inetAddress;

    /**
     * This gives the timeStamp of when the data was written to last.
     * @return The last time the data was written to.
     */
    public long getTimeStamp(){
        return timeStamp;
    }

    /**
     * This function is there to set the time stamped data.
     * @param newData - This is the incoming data that you want to update
     * @throws Exception - Throws an Exception if the data cannot be timestamped using NTP.
     */
    public void setData(T newData) throws Exception{
        this.data = newData;
        this.timeStamp = this.timeClient.getTime(this.inetAddress).getReturnTime();
    }

    /**
     * This function is used to get the latest data that was stored
     * @return - data that is stored in this class
     */
    public T getData(){
        return this.data;
    }

    /**
     * This is the constructor for ATS Base
     * @param initData - This is the inital value you want the data field in this class to be at.
     * @throws Exception - Throws an Exception if the data cannot be timestamped using NTP.
     */
    public  ATS_Base ( T initData) throws Exception{
        String TIME_SERVER = "time-a.nist.gov";
        this.timeClient = new NTPUDPClient();
        this.inetAddress = InetAddress.getByName(TIME_SERVER);
        this.data = initData;
        this.timeStamp = this.timeClient.getTime(this.inetAddress).getReturnTime();
    }


}
