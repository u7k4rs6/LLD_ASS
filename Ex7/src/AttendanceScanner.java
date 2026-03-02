
public class AttendanceScanner implements PowerControl, AttendanceScan {

    @Override
    public void powerOn() {
        // ok
    }

    @Override
    public void powerOff() {
        // no output
    }

    @Override
    public int scanAttendance() {
        return 3;
    }
}
