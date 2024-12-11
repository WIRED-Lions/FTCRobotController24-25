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
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);

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

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double percent = 50;
            double theta = Math.atan2(y, x);
            double power = Math.hypot(x, y);



            drive(theta, power, turn, percent);

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
            telemetry.update();

        }
    }

    public void drive(double theta, double power, double turn, double percent) {
        //Algorithm from video above
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFrontPower = power * cos/max + turn;
        double rightFrontPower = power * sin/max - turn;
        double leftBackPower = power * cos/max - turn;
        double rightBackPower = power * sin/max + turn;



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