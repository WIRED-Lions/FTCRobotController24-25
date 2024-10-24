package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "testRun", group = "Default")
//@TeleOp (name = "testRun", group = "Default")
public class testRun extends LinearOpMode {
    @Override
    public void runOpMode(){
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "Motor0");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "Motor1");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "Motor2");
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "Motor3");

        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); //do for all motors
        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); //do same for all motors
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        telemetry.addData("Mode", "waiting");
        telemetry.update();
        while(true){
            waitForStart();
            if (opModeIsActive()) {

                int frontLeftPosition = frontLeft.getCurrentPosition(); //do same for all motors
                int frontRightPosition = frontRight.getCurrentPosition();
                int backLeftPosition = backLeft.getCurrentPosition();
                int backRightPosition = backRight.getCurrentPosition();


                telemetry.addData("Encoder Position M0", frontRightPosition);
                telemetry.addData("Encoder Position M1", backRightPosition);
                telemetry.addData("Encoder Position M2", backLeftPosition);
                telemetry.addData("Encoder Position M3", frontLeftPosition); //do same for all motors
                telemetry.update();

                //Motor 0
                frontRight.setPower(.99);
                //Motor 1
                backRight.setPower(-.99);
                //Motor 2
                backLeft.setPower(1);
                //Motor 3
                frontLeft.setPower(-1);




        }

/*
            frontRight.setPower(1);
            backRight.setPower(-1);
            backLeft.setPower(1);
            frontLeft.setPower(-1);


 */


            /*frontLeft.setPower(-1.0);
            sleep(1500);
            frontLeft.setPower(1.0);
            sleep(1500);

             */

        }
    }
}
