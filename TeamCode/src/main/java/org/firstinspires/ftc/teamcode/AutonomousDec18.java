//Encoder Count to Inches calculations
//circumference of wheel = 12 inches
//encoder measure = 550 encoder counts per rotation 
// 500 counts/12 in = 30 counts per inch

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous

public class AutonomousDec18 extends LinearOpMode {

    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backRight;

    @Override

    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "Motor3");
        backLeft = hardwareMap.get(DcMotorEx.class, "Motor2");
        frontRight = hardwareMap.get(DcMotorEx.class, "Motor0");
        backRight = hardwareMap.get(DcMotorEx.class, "Motor1");

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        int inch = 46;

        frontLeft.setMotorEnable();
        backLeft.setMotorEnable();
        frontRight.setMotorEnable();
        backRight.setMotorEnable();

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {

            frontLeft.setTargetPosition(inch * 32);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setPower(1);
            backLeft.setTargetPosition(inch * 32);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setPower(1);
            frontRight.setTargetPosition(inch * 32);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setPower(1);
            backRight.setTargetPosition(inch * 32);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setPower(1);
            sleep(5000);

            telemetry.addData("frontLeft Encoder count", frontLeft.getCurrentPosition());
            telemetry.addData("backLeft Encoder count", backLeft.getCurrentPosition());
            telemetry.addData("frontRight Encoder count", frontRight.getCurrentPosition());
            telemetry.addData("backRight Encoder count", backRight.getCurrentPosition());
            telemetry.update();

            sleep(10000);
            
            /*
            for(int i = 0; i < 3; i++){
                frontLeft.setTargetPosition(movePosition);
                frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                frontLeft.setPower(1);
                backLeft.setTargetPosition(movePosition);
                backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backLeft.setPower(1);
                frontRight.setTargetPosition(movePosition);
                frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                frontRight.setPower(1);
                backRight.setTargetPosition(movePosition);
                backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backRight.setPower(1);
                sleep(1000);
                
                frontLeft.setTargetPosition(stayPosition);
                frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                frontLeft.setPower(1);
                backLeft.setTargetPosition(stayPosition);
                backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backLeft.setPower(1);
                frontRight.setTargetPosition(stayPosition);
                frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                frontRight.setPower(1);
                backRight.setTargetPosition(stayPosition);
                backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backRight.setPower(1);
                sleep(1000); 
            }*/
        }
    }
}