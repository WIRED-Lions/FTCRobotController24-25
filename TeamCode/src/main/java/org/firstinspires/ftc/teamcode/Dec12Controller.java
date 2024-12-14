package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp

public class Dec12Controller extends LinearOpMode {

    //Variables
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backRight;
    private DcMotorEx arm;


    private int targetPosition = 0;

    @Override

    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "Motor3");
        backLeft = hardwareMap.get(DcMotorEx.class, "Motor2");
        frontRight = hardwareMap.get(DcMotorEx.class, "Motor0");
        backRight = hardwareMap.get(DcMotorEx.class, "Motor1");
        arm = hardwareMap.get(DcMotorEx.class, "Motor4");

        //Need to reverse motors on one side
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        int armStartPosition = arm.getCurrentPosition();
        telemetry.addData("Slide encoders reset and starting at: ", arm.getCurrentPosition());
        int targetPosition = armStartPosition;

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            frontLeft.setMotorEnable();
            backLeft.setMotorEnable();
            frontRight.setMotorEnable();
            backRight.setMotorEnable();
            arm.setMotorEnable();

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double percent = 50;
            double theta = Math.atan2(y, x);
            double power = Math.hypot(x, y);

            int armPosition = arm.getCurrentPosition();

            drive(theta, power, turn, percent);
            arm();

            telemetry.addData("x", x);
            telemetry.addData("y", y);
            telemetry.addData("turn", turn);
            telemetry.addData("theta", theta);
            telemetry.addData("power", power);
            telemetry.addData("percent speed", percent);
            telemetry.addData("Left Front Power", frontLeft.getPower());
            telemetry.addData("Right Front Power", frontRight.getPower());
            telemetry.addData("Left Back Power", backLeft.getPower());
            telemetry.addData("Right Back Power", backRight.getPower());
            telemetry.addData("Encoder Position Arm", armPosition);
            telemetry.update();

        }
    }

    public void arm() {
        if (gamepad1.a){
            arm.setPower(-1);
        } else if (gamepad1.b) {
            arm.setPower(1);
        } else {
            arm.setPower(0);
        }
    }

    public void drive(double theta, double power, double turn, double percent) {
        //Algorithm from video above
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));
        double speed = 0.0;

        if ((gamepad1.right_trigger > 0.7) && (gamepad1.left_trigger > 0.7)) { //normal
            speed = 1;
        } else if (gamepad1.right_trigger > 0.7) { //fast
            speed = 2;
        } else if (gamepad1.left_trigger > 0.7) { //slow
            speed = 0.5;
        } else { //normal
            speed = 1;
        }


        double leftFrontPower = (power * cos/max + turn) * speed;
        double rightFrontPower = (power * sin/max - turn) * speed;
        double leftBackPower = (power * cos/max - turn) * speed;
        double rightBackPower = (power * sin/max + turn) * speed;



        //sets the power to an inputted percent to control sensitivity
        leftFrontPower *= percent/100;
        rightFrontPower *= percent/100;
        leftBackPower *= percent/100;
        rightBackPower *= percent/100;

        // sets the motors to the calculated values
        frontLeft.setPower(leftFrontPower);
        frontRight.setPower(rightFrontPower);
        backLeft.setPower(leftBackPower);
        backRight.setPower(rightBackPower);
    }

}