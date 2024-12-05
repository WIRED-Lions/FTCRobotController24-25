package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp

public class Nov25ControllerBackUp extends LinearOpMode {

    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    private DcMotorEx arm;


    // todo: write your code here
    private double speedLeft = 0.0;
    private double speedRight = 0.0;
    private double frontLeftVelocity = 0.0;
    private double frontRightVelocity = 0.0;
    private double backLeftVelocity = 0.0;
    private double backRightVelocity = 0.0;
    private int armStartPosition = 0;
    private int targetPosition = 0;


    @Override
    public void runOpMode(){
        frontLeft = hardwareMap.get(DcMotorEx.class, "Motor3");
        frontRight = hardwareMap.get(DcMotorEx.class, "Motor0");
        backRight = hardwareMap.get(DcMotorEx.class, "Motor1");
        backLeft = hardwareMap.get(DcMotorEx.class, "Motor2");

        arm = hardwareMap.get(DcMotorEx.class, "Motor4");

        /***
         frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); //do for all motors
         frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); //do same for all motors
         frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
         frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
         backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
         backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
         backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
         backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
         ***/

        frontLeft.setDirection(DcMotorEx.Direction.FORWARD);
        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);


        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        arm.setPower(0.0);

        armStartPosition = arm.getCurrentPosition();
        telemetry.addData("Slide encoders reset and starting at: ", arm.getCurrentPosition());
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

            telemetry.addData("Encoder Position M3", frontLeftPosition); //do same for all motors
            telemetry.addData("Encoder Position M0", frontRightPosition);
            telemetry.addData("Encoder Position M2", backLeftPosition);
            telemetry.addData("Encoder Position M1", backRightPosition);

            telemetry.addData("Encoder Position M4", armPosition);

            telemetry.addData("Motor Left Front Speed: ", frontLeft.getVelocity());
            telemetry.addData("Motor Right Front Speed: ", frontRight.getVelocity());
            telemetry.addData("Motor Left Back Speed: ", backLeft.getVelocity());
            telemetry.addData("Motor Right Back Speed: ", backRight.getVelocity());

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
            setSpeed();
            setRotation();
            setDirection();
            setMotorVelocity();
        }
    }

    public void setSpeed() {
        double offsetLeft = 1.08;
        double offsetRight = 0.92;
        if ((gamepad1.right_trigger > 0.7) && (gamepad1.left_trigger > 0.7)) { //normal
            speedLeft = 1250.0 * offsetLeft;
            speedRight = 1250.0 * offsetRight;
        } else if (gamepad1.right_trigger > 0.7) { //fast
            speedLeft = 2000.0 * offsetLeft;
            speedRight = 2000.0 * offsetRight;
        } else if (gamepad1.left_trigger > 0.7) { //slow
            speedLeft = 650.0 * offsetLeft;
            speedRight = 650.0 * offsetRight;
        } else { //normal
            speedLeft = 1250.0 * offsetLeft;
            speedRight = 1250.0 * offsetRight;
        }
    }

    public void setRotation() {
        if (gamepad1.left_stick_x < -0.5) { //rotate left
            frontLeftVelocity -= speedLeft / 2;
            frontRightVelocity += speedRight / 2;
            backLeftVelocity -= speedLeft / 2;
            backRightVelocity += speedRight / 2;
        } else if (gamepad1.left_stick_x > 0.5) { //rotate right
            frontLeftVelocity += speedLeft / 2;
            frontRightVelocity -= speedRight / 2;
            backLeftVelocity += speedLeft / 2;
            backRightVelocity -= speedRight / 2;
        }
    }

    public void setDirection() {
        if (gamepad1.right_stick_y <= -0.99 && (Math.abs(gamepad1.right_stick_x) < 0.3)) { //forward
            frontLeftVelocity += speedLeft;
            frontRightVelocity += speedRight;
            backLeftVelocity += speedLeft;
            backRightVelocity += speedRight;
        } else if (gamepad1.right_stick_y >= 0.99 && (Math.abs(gamepad1.right_stick_x) < 0.3)) { //back
            frontLeftVelocity -= speedLeft;
            frontRightVelocity -= speedRight;
            backLeftVelocity -= speedLeft;
            backRightVelocity -= speedRight;
        } else if (gamepad1.right_stick_x <= -0.99 && (Math.abs(gamepad1.right_stick_y) < 0.3)) { //left
            frontLeftVelocity -= speedLeft;
            frontRightVelocity += speedRight;
            backLeftVelocity += speedLeft;
            backRightVelocity -= speedRight;
        } else if (gamepad1.right_stick_x >= 0.99 && (Math.abs(gamepad1.right_stick_y) < 0.3)) { //right
            frontLeftVelocity += speedLeft;
            frontRightVelocity -= speedRight;
            backLeftVelocity -= speedLeft;
            backRightVelocity += speedRight;
        } else if ((gamepad1.right_stick_y < -0.3) && (gamepad1.right_stick_x < -0.3)) { //forward left
            frontLeftVelocity += 0;
            frontRightVelocity += speedRight;
            backLeftVelocity += speedLeft;
            backRightVelocity += 0;
        } else if ((gamepad1.right_stick_y < -0.3) && (gamepad1.right_stick_x > 0.3)) { //forward right
            frontLeftVelocity += speedLeft;
            frontRightVelocity += 0;
            backLeftVelocity += 0;
            backRightVelocity += speedRight;
        } else if ((gamepad1.right_stick_y > 0.3) && (gamepad1.right_stick_x < -0.3)) { //back left
            frontLeftVelocity -= speedLeft;
            frontRightVelocity += 0;
            backLeftVelocity += 0;
            backRightVelocity -= speedRight;
        } else if ((gamepad1.right_stick_y > 0.3) && (gamepad1.right_stick_x > 0.3)) { //back right
            frontLeftVelocity += 0;
            frontRightVelocity -= speedRight;
            backLeftVelocity -= speedLeft;
            backRightVelocity += 0;
        } else { //no direction
            frontLeftVelocity = frontLeftVelocity * 2; //multiply velocity by 2 to speed up stationary rotation
            frontRightVelocity = frontRightVelocity * 2;
            backLeftVelocity = backLeftVelocity * 2;
            backRightVelocity = backRightVelocity * 2;
        }
    }

    public void liftBasket(DcMotorEx arm) {
        if (gamepad1.a){
            arm.setPower(-1);
        } else if (gamepad1.b) {
            arm.setPower(1);
        } else {
            arm.setPower(0);
        }
    }

    public void setMotorVelocity() {
        frontLeft.setVelocity(frontLeftVelocity);
        frontRight.setVelocity(frontRightVelocity);
        backLeft.setVelocity(backLeftVelocity);
        backRight.setVelocity(backRightVelocity);
    }
}