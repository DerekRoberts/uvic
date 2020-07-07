
/**
 * Line Follower
 */
import lejos.nxt.*;

public class LineFollower {

    public static void main(String[] aArg) throws Exception {

        //port 3 is left sensor
        //port 1 is right sensor
        //port C is right motor
        //port B is left motor
        LightSensor lightL = new LightSensor(SensorPort.S3);
        LightSensor lightR = new LightSensor(SensorPort.S1);

        final int forward = 1;
        final int back = 2;
        final int stop = 3;
        final int flt = 4;
        final int power = 70;
        final int whiteThreshold;

        //Use the lightL sensor as a reflection sensor
        lightL.setFloodlight(true);
        LCD.drawString("LightL %: ", 0, 0);
        //Use the lightR sensor as a reflection sensor
        lightR.setFloodlight(true);
        LCD.drawString("LightR %: ", 0, 1);

        //Show light percent until LEFT is pressed
        LCD.drawString("Press LEFT", 0, 4);
        LCD.drawString("to start", 0, 5);
        while (!Button.LEFT.isDown()) {
            LCD.drawInt(lightL.readValue(), 3, 9, 0);
            LCD.drawInt(lightR.readValue(), 3, 9, 1);
        }
        //Start of the car
        //Follow line until ESCAPE is pressed
        LCD.drawString("Press ESCAPE", 0, 4);
        LCD.drawString("to stop ", 0, 5);
        LCD.drawString("Avg of L&R:", 0, 2);

        //whiteThreshold is 10 below the average of Left and Right
        //below this value, we expect to see black
        whiteThreshold = ((lightL.getLightValue()
                + lightR.getLightValue()) / 2) - 10;

        while (!Button.ESCAPE.isDown()) {
            // showing the values
            LCD.drawInt(lightL.readValue(), 3, 9, 0);
            LCD.drawInt(lightR.readValue(), 3, 9, 1);
            LCD.drawInt(whiteThreshold, 3, 9, 2);

            if ((lightL.readValue() > whiteThreshold)
                    && (lightR.readValue() > whiteThreshold)) {
                // Go striaght
                LCD.drawString("striaght", 0, 3);
                MotorPort.B.controlMotor(power, forward);
                MotorPort.C.controlMotor(power, forward);
            } else if ((lightL.readValue() < whiteThreshold)
                    && (lightR.readValue() < whiteThreshold)) {
                //Stop
                LCD.drawString("halt    ", 0, 3);
                MotorPort.B.controlMotor(0, stop);
                MotorPort.C.controlMotor(0, stop);
            } else if (lightL.readValue() < whiteThreshold) {
                // turn left
                LCD.drawString("left    ", 0, 3);
                MotorPort.B.controlMotor(power, forward);
                MotorPort.C.controlMotor(power, back);
            } else if (lightR.readValue() < whiteThreshold) {
                //turn right
                LCD.drawString("right   ", 0, 3);
                MotorPort.B.controlMotor(power, back);
                MotorPort.C.controlMotor(power, forward);
            }
            Thread.sleep(10);
        }
        // Stop car gently with free wheel drive
        MotorPort.B.controlMotor(0, flt);
        MotorPort.C.controlMotor(0, flt);
        LCD.clear();
        LCD.drawString("Program stopped", 0, 0);
        Thread.sleep(1000);
    }
}
