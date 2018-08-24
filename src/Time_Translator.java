public class Time_Translator extends ATS_Base<Long> {

    private long initDeviceTime, initGlobalTime;
    private long currentDeviceTime, currentGlobalTime;
    private double deviceToGlobalA, deviceToGlobalB;

    /**
     * This is where the current ntp time is translated into the device time.
     * @return The current local time based on the current ntp time.
     * @throws Exception - If we are unable to get NTP data it will throw an exception.
     */
    public long getDeviceTime() throws  Exception{
        return getDeviceTime(getGlobalTime());
    }

    /**
     * This is where a ntp time is translated into the device time.
     * @param globalTime - This is any ntp global time.
     * @return - It returns the corresponding device time.
     */
    public long getDeviceTime(long globalTime){
        double temp = (double)globalTime - this.deviceToGlobalB;
        temp = temp / this.deviceToGlobalA;
        return (long) temp;
    }

    /**
     * This is where you will store the most up to date time of the device device.
     * @param deviceTime - this is where one would update the device time so that the program can more accurately
     *                  translate between ntp and device time.
     * @throws Exception - If we are unable to get NTP data it will throw an exception.
     */
    public void updateDeviceTime(long deviceTime) throws Exception{
        this.setData(deviceTime);
        this.currentDeviceTime = deviceTime;
        this.currentGlobalTime = this.getTimeStamp();
        this.deviceToGlobalA = (double)(this.currentGlobalTime - this.initGlobalTime) / (double)(this.currentDeviceTime - this.initDeviceTime);
        this.deviceToGlobalB = (double)this.currentGlobalTime - (this.deviceToGlobalA * (double)this.currentDeviceTime);
    }

    /**
     * This function translates between the device time and the global ntp time.
     * @param deviceTime - The device time that you want to translate to ntp time.
     * @return The corresponding ntp time.
     */
    public long getGlobalTime(long deviceTime){
        double temp = (double)deviceTime * this.deviceToGlobalA;
        temp = temp + this.deviceToGlobalB;
        return (long) temp;
    }

    /**
     * This function grabs the NTP time and returns it.
     * @return The Global NTP Time.
     * @throws Exception - If we are unable to get NTP data it will throw an exception.
     */
    public long getGlobalTime() throws Exception{
        return this.timeClient.getTime(this.inetAddress).getReturnTime();
    }

    /**
     * This is the Time Translator Constructor.
     * @param currentDeviceTime - This is the time of the device when the constructor is called so that we can save the
     *                          initial values.
     * @throws Exception - If we are unable to get NTP data it will throw an exception.
     */

    public Time_Translator(long currentDeviceTime) throws Exception{
        super(currentDeviceTime);
        this.initDeviceTime = currentDeviceTime;
        this.initGlobalTime = this.getTimeStamp();
        this.currentDeviceTime = currentDeviceTime;
        this.currentGlobalTime = this.getTimeStamp();
        this.deviceToGlobalB = this.initGlobalTime - this.initDeviceTime;
        this.deviceToGlobalA = 1;
    }
}
