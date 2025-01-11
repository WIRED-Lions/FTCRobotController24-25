package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp

public class FINALController extends LinearOpMode {

    //Variables
    private DcMotorEx frontLeft;
    private DcMotorEx backLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backRight;
    private DcMotorEx arm;
    private CRServo beltRotate;
    private Servo dumpBucket;
    private Servo intakeMover;
    private CRServo intakeSpin;
    private DcMotorEx conveyorMover;


    private int targetPosition = 0;

    @Override

    public void runOpMode() {

        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        beltRotate = hardwareMap.get(CRServo.class, "beltRotate");
        dumpBucket = hardwareMap.get(Servo.class, "dumpBucket");
        intakeMover = hardwareMap.get(Servo.class, "intakeMover");
        intakeSpin = hardwareMap.get(CRServo.class, "intakeSpin");
        conveyorMover = hardwareMap.get(DcMotorEx.class, "conveyorMover");

        //Need to reverse motors on one side
        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        //frontRight.setDirection(DcMotorEx.Direction.FORWARD);
        //backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);
        arm.setDirection(DcMotorEx.Direction.REVERSE);
        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        beltRotate.setDirection(CRServo.Direction.REVERSE);
        conveyorMover.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        intakeSpin.setDirection(CRServo.Direction.REVERSE);

        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        arm.setVelocity(1000);

        telemetry.addData("Slide encoders reset and starting at: ", arm.getCurrentPosition());
        arm.setTargetPosition(targetPosition);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        conveyorMover.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        conveyorMover.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        conveyorMover.setTargetPosition(0);
        conveyorMover.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        dumpBucket.setPosition(0.0);
        double initialPosition = dumpBucket.getPosition();


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            frontLeft.setMotorEnable();
            backLeft.setMotorEnable();
            frontRight.setMotorEnable();
            backRight.setMotorEnable();
            arm.setMotorEnable();
            conveyorMover.setMotorEnable();

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y; // is this supposed to be negative? yes it is
            double turn = gamepad1.right_stick_x;
            double percent = 50;
            double theta = Math.atan2(y, x);
            double power = Math.hypot(x, y);
            int armPosition = arm.getCurrentPosition();

            drive(theta, power, turn, percent);
            movearm();
            conveyorBelt();
            basketScore();
            conveyorMover();
            armRotate();
            intakeSpin();
            telemetry(x, y, turn, percent, theta, power, armPosition, targetPosition, initialPosition);

        }
    }

    public void telemetry(double x, double y, double turn, double percent, double theta, double power, double armPosition, double targetPosition, double initialPosition){
        //drive train variables
        telemetry.addData("x", x);
        telemetry.addData("y", y);
        telemetry.addData("turn", turn);
        telemetry.addData("theta", theta);
        telemetry.addData("power", power);
        telemetry.addData("percent speed", percent);
        //drive train wheel power
        telemetry.addData("Left Front Power", frontLeft.getPower());
        telemetry.addData("Right Front Power", frontRight.getPower());
        telemetry.addData("Left Back Power", backLeft.getPower());
        telemetry.addData("Right Back Power", backRight.getPower());
        //drive train wheel encoder counts
        telemetry.addData("Left Front Encoder", frontLeft.getCurrentPosition());
        telemetry.addData("Right Front Encoder", frontRight.getCurrentPosition());
        telemetry.addData("Left Back Encoder", backLeft.getCurrentPosition());
        telemetry.addData("Right Back Encoder", backRight.getCurrentPosition());
        //arm
        telemetry.addData("Encoder Position Arm", armPosition);
        telemetry.addData("Arm Target Position", targetPosition);
        //basket, conveyor, and intake
        telemetry.addData("Initial Position", initialPosition);
        telemetry.addData("Dump Position", dumpBucket.getPosition());
        telemetry.addData("Conveyor Mover Encoder", conveyorMover.getCurrentPosition());
        telemetry.addData("Rotate Arm", intakeMover.getPosition());
        telemetry.update();
    }

    public void intakeSpin(){
        if (gamepad1.left_bumper) {
            intakeSpin.setPower(-1.0);
        } else if (gamepad1.right_bumper) {
            intakeSpin.setPower(1.0);
        } else if (gamepad1.right_bumper && gamepad2.left_bumper){
            intakeSpin.setPower(0.0);
        } else {
            intakeSpin.setPower(0.0);
        }
    }




    public void armRotate(){
        if (gamepad1.y){
            intakeMover.setPosition(0);
        } else if (gamepad1.a) {
            intakeMover.setPosition(0.75);
        }
    }

    public void conveyorMover(){
        conveyorMover.setVelocity(500);

        if (gamepad2.left_stick_y >= 0.7){
            conveyorMover.setTargetPosition(750); // might be high, test before competition
        } else if (gamepad2.left_stick_y <= -0.7) {
            conveyorMover.setTargetPosition(0);
        }
    }


    public void basketScore(){
        if (gamepad2.left_bumper){
            dumpBucket.setPosition(1);
            //dumpBucket.setPosition(0.5);
            //dumpBucket.setPosition(dumpBucket.getPosition() - 0.01);
        } else if (gamepad2.right_bumper){
            dumpBucket.setPosition(0.6);
            //dumpBucket.setPosition(0.1);
            //dumpBucket.setPosition(0);
        }
    }

    public void conveyorBelt() {
        if (gamepad2.left_trigger >= 0.3) {
            beltRotate.setPower(1.0);
        } else if (gamepad2.right_trigger >= 0.3) {
            beltRotate.setPower(-1.0);
        } else if ((gamepad2.right_trigger >= 0.3) && (gamepad2.left_trigger >= 0.3)) {
            beltRotate.setPower(0.0);
        } else {
            beltRotate.setPower(0.0);
        }
    }


    public void movearm() {

        arm.setVelocity(4000);

        if (gamepad2.y){
            arm.setTargetPosition(3100); // know how much an encoder count is worth in inches or centimeters to tell judges
        } else if (gamepad2.a) {
            arm.setTargetPosition(200);
        } else if (gamepad2.b) {
            arm.setTargetPosition(1750);
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
