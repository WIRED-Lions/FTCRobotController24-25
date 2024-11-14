package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp(name="Controller", group="Default")
public class Controller extends LinearOpMode {

    private Double frontRightVelocity = 0.0;
    private Double frontLeftVelocity = 0.0;
    private Double backRightVelocity = 0.0;
    private Double backLeftVelocity = 0.0;
    private Integer targetPosition = 0;
    private Integer armStartPosition = 0;

    @Override
    public void runOpMode(){
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "Motor0");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "Motor1");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "Motor2");
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "Motor3");
        DcMotorEx arm = hardwareMap.get(DcMotorEx.class, "Motor4");

        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); //do for all motors
        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); //do same for all motors
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        arm.setPower(0.0);

        armStartPosition = arm.getCurrentPosition();
        targetPosition = armStartPosition;


        telemetry.addData("Mode", "waiting");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {

            int frontLeftPosition = frontLeft.getCurrentPosition(); //do same for all motors
            int frontRightPosition = frontRight.getCurrentPosition();
            int backLeftPosition = backLeft.getCurrentPosition();
            int backRightPosition = backRight.getCurrentPosition();
            int armPosition = arm.getCurrentPosition();

            telemetry.addData("Encoder Position M0", frontRightPosition);
            telemetry.addData("Encoder Position M1", backRightPosition);
            telemetry.addData("Encoder Position M2", backLeftPosition);
            telemetry.addData("Encoder Position M3", frontLeftPosition); //do same for all motors
            telemetry.addData("Encoder Position M4", armPosition);
            telemetry.addData("Gamepad1 Right Stick Y", gamepad1.right_stick_y);
            telemetry.addData("Gamepad1 Right Stick X", gamepad1.right_stick_x);
            telemetry.addData("Gamepad1 Left Stick Y", gamepad1.left_stick_y);
            telemetry.addData("Gamepad1 Left Stick X", gamepad1.left_stick_x);
            telemetry.update();

            frontRight.setMotorEnable();
            frontLeft.setMotorEnable();
            backRight.setMotorEnable();
            backLeft.setMotorEnable();
            arm.setMotorEnable();

            liftBasket(arm);
        }
    }
    public void liftBasket(DcMotorEx arm) {
        if (gamepad1.a){
            arm.setTargetPosition(100);
        }
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
