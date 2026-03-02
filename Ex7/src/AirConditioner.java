
public class AirConditioner implements PowerControl, TemperatureControl {

    @Override
    public void powerOn() {
        // power on initialized
    }

    @Override
    public void powerOff() {
        System.out.println("AC OFF");
    }

    @Override
    public void setTemperatureC(int temp) {
        System.out.println("AC set to " + temp + "C");
    }
}
