package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp (name = "testRun", group = "Default")
public class testRun extends LinearOpMode {
    @Override
    public void runOpMode(){
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "Motor0");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "Motor1");
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "Motor2");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "Motor3");

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();
        if (opModeIsActive()){
            backLeft.setPower(1.0);
            backRight.setPower(1.0);
            frontLeft.setPower(1.0);
            frontRight.setPower(1.0);
            sleep(5000);
        }
    }
}
