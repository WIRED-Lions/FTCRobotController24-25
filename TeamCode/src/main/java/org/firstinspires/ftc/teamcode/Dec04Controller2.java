package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp

public class Dec04Controller2 extends LinearOpMode {

    //Variables
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backRight;

    private Double speedLeft = 0.0;
    private Double speedRight = 0.0;
    private Double frontLeftVelocity = 0.0;
    private Double frontRightVelocity = 0.0;
    private Double backLeftVelocity = 0.0;
    private Double backRightVelocity = 0.0;

    private int targetPosition = 0;

    @Override

    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "Motor3");
        backLeft = hardwareMap.get(DcMotorEx.class, "Motor2");
        frontRight = hardwareMap.get(DcMotorEx.class, "Motor0");
        backRight = hardwareMap.get(DcMotorEx.class, "Motor1");

        //Need to reverse motors on one side
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backRight.setDirection(DcMotorEx.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //telemetry.addData("Status", "Running");
            //telemtery.addData("gamepad1.right_stick_y: ", gamepad1.right_stick_y);
            // telemetry needed?

            frontLeft.setMotorEnable();
            backLeft.setMotorEnable();
            frontRight.setMotorEnable();
            backRight.setMotorEnable();

            speedLeft = 0.0;
            speedRight = 0.0;
            frontLeftVelocity = 0.0;
            frontRightVelocity = 0.0;
            backRightVelocity = 0.0;
            backLeftVelocity = 0.0;

            setSpeed();
            setRotation();
            setDirection();

            frontLeft.setVelocity(frontLeftVelocity);
            backLeft.setVelocity(backLeftVelocity);
            frontRight.setVelocity(frontRightVelocity);
            backRight.setVelocity(backLeftVelocity);

        }
    }

    public void setSpeed() {
        Double offsetLeft = 1.0;
        Double offsetRight = 1.0;
        if((gamepad1.right_trigger > 0.7) && (gamepad1.left_trigger > 0.7)){
            speedLeft = 1250.0 * offsetLeft;
            speedRight = 1250.0 * offsetRight;
        } else if (gamepad1.right_trigger > 0.7) {
            speedLeft = 2000.0 * offsetLeft;
            speedRight = 2000.0 * offsetRight;
        } else if (gamepad1.left_trigger > 0.7) {
            speedLeft = 650.0 * offsetLeft;
            speedRight = 650.0 * offsetRight;
        } else if ((gamepad1.right_trigger <= 0.7) && (gamepad1.left_trigger <= 0.7)){
            speedLeft = 1250.0 * offsetLeft;
            speedRight = 1250.0 * offsetRight;
        }
    }

    public void setRotation() {
        if(gamepad1.left_stick_x < -0.5) {
            frontLeftVelocity -= speedLeft / 2;
            backLeftVelocity += speedLeft / 2;
            backRightVelocity -= speedLeft / 2;
            backLeftVelocity += speedLeft / 2;
        } else if (gamepad1.left_stick_x > 0.5) {
            frontLeftVelocity += speedLeft / 2;
            backLeftVelocity -= speedLeft / 2;
            backRightVelocity += speedLeft / 2;
            backLeftVelocity -= speedLeft / 2;
        }
    }

    public void setDirection() {
        if(gamepad1.right_stick_y <= -0.99 && (Math.abs(gamepad1.right_stick_x) < 0.3)){
            frontLeftVelocity += speedLeft;
            frontRightVelocity += speedRight;
            backLeftVelocity += speedLeft;
            backRightVelocity += speedRight;
        } else if (gamepad1.right_stick_y >= 0.99 && (Math.abs(gamepad1.right_stick_x) < 0.3)){
            frontLeftVelocity -= speedLeft;
            frontRightVelocity -= speedRight;
            backLeftVelocity -= speedLeft;
            backRightVelocity -= speedRight;
        } else if (gamepad1.right_stick_x <= -0.99 && (Math.abs(gamepad1.right_stick_y) < 0.3)){
            frontLeftVelocity -= speedLeft;
            frontRightVelocity -= speedRight;
            backLeftVelocity += speedLeft;
            backRightVelocity += speedRight;
        } else if (gamepad1.right_stick_x >= 0.99 && (Math.abs(gamepad1.right_stick_y) < 0.3)){
            frontLeftVelocity += speedLeft;
            frontRightVelocity += speedRight;
            backLeftVelocity -= speedLeft;
            backRightVelocity -= speedRight;
        } else if ((gamepad1.right_stick_y < -0.3) && (gamepad1.right_stick_x < -0.3)){
            frontLeftVelocity += 0;
            frontRightVelocity += speedRight;
            backLeftVelocity += speedLeft;
            backRightVelocity += 0;
        } else if ((gamepad1.right_stick_y < -0.3) && (gamepad1.right_stick_x > 0.3)){
            frontLeftVelocity += speedLeft;
            frontRightVelocity += 0;
            backLeftVelocity += 0;
            backRightVelocity += speedRight;
        } else if ((gamepad1.right_stick_y > 0.3) && (gamepad1.right_stick_x < -0.3)){
            frontLeftVelocity -= speedLeft;
            frontRightVelocity += 0;
            backLeftVelocity += 0;
            backRightVelocity -= speedRight;
        } else if ((gamepad1.right_stick_y > 0.3) && (gamepad1.right_stick_x > 0.3)) {
            frontLeftVelocity += 0;
            frontRightVelocity -= speedRight;
            backLeftVelocity -= speedLeft;
            backRightVelocity += 0;
        } else {
            frontLeftVelocity = frontLeftVelocity * 2;
            frontRightVelocity = frontRightVelocity * 2;
            backLeftVelocity = backLeftVelocity * 2;
            backRightVelocity = backRightVelocity * 2;
        }
    }
}