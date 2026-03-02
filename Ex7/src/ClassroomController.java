
public class ClassroomController {

    private final DeviceRegistry registry;

    public ClassroomController(DeviceRegistry registry) {
        this.registry = registry;
    }

    public void startClass() {
        // Projector needs power + input
        PowerControl proj = (PowerControl) registry.getFirstOfType("Projector");
        proj.powerOn();
        ((InputConnectable) proj).connectInput("HDMI-1");

        // Lights only needs brightness
        BrightnessControl lp = (BrightnessControl) registry.getFirstOfType("LightsPanel");
        lp.setBrightness(60);

        // AC only needs temperature
        TemperatureControl acUnit = (TemperatureControl) registry.getFirstOfType("AirConditioner");
        acUnit.setTemperatureC(24);

        // Scanner only needs scan
        AttendanceScan presentsScanner = (AttendanceScan) registry.getFirstOfType("AttendanceScanner");
        System.out.println("Attendance scanned: present=" + presentsScanner.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        ((PowerControl) registry.getFirstOfType("Projector")).powerOff();
        ((PowerControl) registry.getFirstOfType("LightsPanel")).powerOff();
        ((PowerControl) registry.getFirstOfType("AirConditioner")).powerOff();
    }
}
