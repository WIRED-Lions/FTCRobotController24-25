//Encoder Count to Inches calculations
//circumference of wheel = 12 inches
//encoder measure = 550 encoder counts per rotation 
// 500 counts/12 in = 46 counts per inch

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
    private DcMotorEx arm;
    private CRServo beltRotate;
    private Servo dumpBucket;
    private Servo intakeMover;
    private CRServo intakeSpin;
    private DcMotorEx conveyorMover;

    private static int inchConstant = 46;
    private int targetPosition = 0;

    @Override

    public void runOpMode() {

        // variable declarations
        int inch = 0;
        int time = 0;

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


        frontLeft.setMotorEnable();
        backLeft.setMotorEnable();
        frontRight.setMotorEnable();
        backRight.setMotorEnable();
        arm.setMotorEnable();

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        waitForStart();



        if (opModeIsActive()) {

            forward(12, 2000);

            telemetry.addData("frontLeft Encoder count", frontLeft.getCurrentPosition());
            telemetry.addData("backLeft Encoder count", backLeft.getCurrentPosition());
            telemetry.addData("frontRight Encoder count", frontRight.getCurrentPosition());
            telemetry.addData("backRight Encoder count", backRight.getCurrentPosition());
            telemetry.update();

            sleep(2000);

            right(12, 2000);

        }
    }

    public void forward(int inch, int time){
        frontLeft.setTargetPosition(inch * inchConstant);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(1);
        backLeft.setTargetPosition(inch * inchConstant);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setPower(1);
        frontRight.setTargetPosition(inch * inchConstant);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(1);
        backRight.setTargetPosition(inch * inchConstant);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setPower(1);
        sleep(time);
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void right(int inch, int time){
        frontLeft.setTargetPosition(inch * inchConstant);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setPower(1);
        backLeft.setTargetPosition(-inch * inchConstant);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setPower(1);
        frontRight.setTargetPosition(-inch * inchConstant);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setPower(1);
        backRight.setTargetPosition(inch * inchConstant);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setPower(1);
        sleep(time);
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }

}