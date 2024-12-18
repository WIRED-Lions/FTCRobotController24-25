package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp

public class EncoderTest extends LinearOpMode {

    DcMotorEx leftFront;
    DcMotorEx leftBack;
    DcMotorEx rightFront;
    DcMotorEx rightBack;
    DcMotorEx arm;

    @Override

    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotorEx.class, "Motor3");
        rightBack = hardwareMap.get(DcMotorEx.class, "Motor2");
        rightFront = hardwareMap.get(DcMotorEx.class, "Motor0");
        leftBack = hardwareMap.get(DcMotorEx.class, "Motor1");
        arm = hardwareMap.get(DcMotorEx.class, "Motor4");

        //Need to reverse motors on one side
        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
        rightFront.setDirection(DcMotorEx.Direction.FORWARD);
        leftBack.setDirection(DcMotorEx.Direction.REVERSE);
        rightBack.setDirection(DcMotorEx.Direction.FORWARD);

        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);


        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {


            //leftBack.setPower(1);
            //leftFront.setPower(1);
            //rightBack.setPower(1);
            // rightFront.setPower(1);



            telemetry.addData("Left Front Encoder", leftFront.getCurrentPosition());
            telemetry.addData("Right Front Encoder", rightFront.getCurrentPosition());
            telemetry.addData("Left Back Encoder", leftBack.getCurrentPosition());
            telemetry.addData("Right Back Encoder", rightBack.getCurrentPosition());
            telemetry.update();

        } // While op mode is active
    } // Run Op Mode
}