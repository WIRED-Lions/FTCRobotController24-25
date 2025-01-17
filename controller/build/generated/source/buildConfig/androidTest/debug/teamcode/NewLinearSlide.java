package teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Autonomous(name = "NewLinearSlide", group = "Default")
public class NewLinearSlide extends LinearOpMode {
    @Override
    public void runOpMode(){
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "linearSlide");

        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); //do for all motors
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); //do same for all motors


        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            int motorPosition = motor.getCurrentPosition(); //do same for all motors


            telemetry.addData("Encoder Position M0", motorPosition);
            telemetry.update();

            //Motor 0
            motor.setPower(0.99);

        }
    }
}


