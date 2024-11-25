package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "LinearSlide", group = "Default")
public class LinearSlide extends LinearOpMode {
    @Override
    public void runOpMode(){
        DcMotorEx linearSlide = hardwareMap.get(DcMotorEx.class, "linearSlide");
        //DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "Motor3"); //for reference of how to initialize motor

        linearSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER); //do for all motors
        linearSlide.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); //do same for all motors

        telemetry.addData("Mode", "waiting");
        telemetry.update();
        while(true){
            waitForStart();
            if (opModeIsActive()) {

                int linearSlidePosition = linearSlide.getCurrentPosition(); //do same for all motors

                telemetry.addData("Encoder Position linearSlide", linearSlidePosition); //do same for all motors
                telemetry.update();

                //Motor 0


                linearSlide.setPower(0.5);
                }

                }
        }
}//getting an error here - need to debug whats doing on :)
