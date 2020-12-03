/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the DifferentialDrive class.
 * Runs the motors with arcade steering.
 */
public class Robot extends TimedRobot {
  private final PWMVictorSPX m_leftMotor = new PWMVictorSPX(0);
  private final PWMVictorSPX m_rightMotor = new PWMVictorSPX(1);
  private final PWMVictorSPX m_leftDrum = new PWMVictorSPX(3);
  private final PWMVictorSPX m_rightDrum = new PWMVictorSPX(2);
  
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  private final Joystick m_stick = new Joystick(0);

  Timer timer;
  public double hit_time = 0.8; // Her vuruşta motorun çalışma süresi (saniye)
  public double hit_power = 1; // Her vuruşta motorun çalışma gücü 0-1

  public ArrayList<Double> left_times = new ArrayList<>();
  public ArrayList<Double> right_times = new ArrayList<>();


  public void robotInit() {
    timer = new Timer();

    addHit(1, "left");
    addHit(2, "right");
    addHit(4, "left");
    addHit(10, "right");
  }

  public void teleopInit() {
    timer.reset();
    timer.start();
  }

  @Override
  public void teleopPeriodic() {

    m_robotDrive.arcadeDrive(m_stick.getY(), m_stick.getZ());

    m_leftDrum.set(0);
    m_rightDrum.set(0);

    for(int i = 0; i < left_times.size(); i++) {
      if(timer.get() >= left_times.get(i) && timer.get() < left_times.get(i) + hit_time) {
        m_leftDrum.set(hit_power);
      }
    }

    for(int j = 0; j < right_times.size(); j++) {
      if(timer.get() >= right_times.get(j) && timer.get() < right_times.get(j) + hit_time) {
        m_rightDrum.set(hit_power);
      }
    }
    
  }

  public void addHit(double time, String type) {
    if(type == "left") {
      left_times.add(time);
    } else if(type == "right") {
      right_times.add(time);
    }
  }
}
