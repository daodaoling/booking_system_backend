cmake_minimum_required(VERSION 2.8.3)
project(value_gradually)

find_package(Boost REQUIRED COMPONENTS thread)

find_package(catkin REQUIRED COMPONENTS
  roscpp
)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11")

include_directories(
  include
  ${Boost_INCLUDE_DIRS} 
  ${catkin_INCLUDE_DIRS}  
  src
)


catkin_package(
   INCLUDE_DIRS include 
   LIBRARIES lib
   CATKIN_DEPENDS geometry_msgs nav_msgs roscpp rospy std_msgs
   DEPENDS system_lib)

add_executable(value_gradually src/value_gradually.cpp)                #添加可执行节点
target_link_libraries(value_gradually ${catkin_LIBRARIES})    #链接库
add_dependencies(value_gradually value_gradually_generator_messages_cpp)
