To test the program, 
a. cd ~/av-core/catkin_ws/src/usbcan_driver_seedland
b. roslaunch launch/usbcan_seedland.launch

1. /golfcart/USBCan_error_code
This topic is only available with rolling counter and checksum

2. To switch to the code with rolling counter and checksum, comment the line "#define DEBUG" in include/can_driver.h

3. The folloiwng topics are sent by the VCU to computer
/golfcart/USBCan_brakeprs
/golfcart/USBCan_door_closersn
/golfcart/USBCan_door_openrsn
/golfcart/USBCan_door_status
/golfcart/USBCan_epb_status
/golfcart/USBCan_error_code
/golfcart/USBCan_gear_position
/golfcart/USBCan_steering_angle
/golfcart/USBCan_velocity
/golfcart/bat_dist_remain
/golfcart/button_state_automode

4. The following topics are sent by the computer to VCU
/golfcart/cmd_brake
/golfcart/cmd_door
/golfcart/cmd_epb
/golfcart/cmd_steer
/golfcart/cmd_vel

rostopic pub -r 20 /golfcart/cmd_brake std_msgs/Float64 0.9
rostopic pub -r 20 /golfcart/cmd_epb std_msgs/Int32 1 (1 engage, 0 release)
rostopic pub -r 20 /golfcart/cmd_door std_msgs/Int32 1 (1 open, 0 close)
rostopic pub -r 20 /golfcart/cmd_steer geometry_msgs/Twist '{linear:  {x: 0.0, y: 0.0, z: 0.0}, angular: {x: 0.0,y: 0.0,z: 0.1}}' (0.1rad is variable to change)
rostopic pub -r 20 /golfcart/cmd_vel geometry_msgs/Twist '{linear:  {x: 1.0, y: 0.0, z: 0.0}, angular: {x: 0.0,y: 0.0,z: 0.0}}' (1.0m/s is variable to change)
