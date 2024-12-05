package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp

public class WheelTest extends LinearOpMode {

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



        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {


            leftBack.setPower(1);
            // leftFront.setPower(1);
            // rightBack.setPower(1);
            // rightFront.setPower(1);



            telemetry.addData("Left Front Power", leftFront.getPower());
            telemetry.addData("Right Front Power", rightFront.getPower());
            telemetry.addData("Left Back Power", leftBack.getPower());
            telemetry.addData("Right Back Power", rightBack.getPower());
            telemetry.update();

        } // While op mode is active
    } // Run Op Mode
}