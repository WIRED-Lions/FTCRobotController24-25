package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;



@TeleOp

public class Nov25Controller extends LinearOpMode {

    //Variables
    DcMotorEx leftFront;
    DcMotorEx leftBack;
    DcMotorEx rightFront;
    DcMotorEx rightBack;
    DcMotorEx arm;

    private int armStartPosition = 0;
    private int targetPosition = 0;

    @Override

    public void runOpMode() {

        leftFront = hardwareMap.get(DcMotorEx.class, "Motor3");
        leftBack = hardwareMap.get(DcMotorEx.class, "Motor2");
        rightFront = hardwareMap.get(DcMotorEx.class, "Motor0");
        rightBack = hardwareMap.get(DcMotorEx.class, "Motor1");
        arm = hardwareMap.get(DcMotorEx.class, "Motor4");

        //Need to reverse motors on one side
        leftFront.setDirection(DcMotorEx.Direction.FORWARD);
        rightFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftBack.setDirection(DcMotorEx.Direction.FORWARD);
        rightBack.setDirection(DcMotorEx.Direction.REVERSE);

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
            // Variables
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            double percent = 50;

            // Allows gamepad2 to take over driving
            if (gamepad2.left_bumper) {
                x = gamepad2.left_stick_x;
                y = -gamepad2.left_stick_y;
                turn = gamepad2.right_stick_x;
            }


            //calculating theta and power for driving function
            double theta = Math.atan2(y, x);
            double power = Math.hypot(x, y);

            drive(theta, power, turn, percent);
            liftBasket(arm);

            // adding telemetry data
            telemetry.addData("x", x);
            telemetry.addData("y", y);
            telemetry.addData("turn", turn);
            telemetry.addData("theta", theta);
            telemetry.addData("power", power);
            telemetry.addData("percent speed", percent);
            telemetry.addData("Left Front Power", leftFront.getPower());
            telemetry.addData("Right Front Power", rightFront.getPower());
            telemetry.addData("Left Back Power", leftBack.getPower());
            telemetry.addData("Right Back Power", rightBack.getPower());
            telemetry.update();

        } // While op mode is active
    } // Run Op Mode

    //Functions
    //https://youtu.be/gnSW2QpkGXQ?si=S0n82yAB5Zl1MYK9 (shows a more complex method for programming mechanum wheels)
    public void drive(double theta, double power, double turn, double percent) {
        //Algorithm from video above
        double sin = Math.sin(theta - Math.PI/4);
        double cos = Math.cos(theta - Math.PI/4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFrontPower = power * cos/max + turn;
        double rightFrontPower = power * sin/max - turn;
        double leftBackPower = power * cos/max + turn;
        double rightBackPower = power * sin/max - turn;

        if ((power + Math.abs(turn)) > 1) {
            leftFrontPower /= power + turn;
            rightFrontPower /= power + turn;
            leftBackPower /= power + turn;
            rightBackPower /= power + turn;
        }

        //sets the power to an inputted percent to control sensitivity
        leftFrontPower *= percent/100;
        rightFrontPower *= percent/100;
        leftBackPower *= percent/100;
        rightBackPower *= percent/100;

        // sets the motors to the calculated values
        leftFront.setPower(leftFrontPower);
        rightFront.setPower(rightFrontPower);
        leftBack.setPower(leftBackPower);
        rightBack.setPower(rightBackPower);
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
}

