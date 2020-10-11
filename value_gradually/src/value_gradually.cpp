#include<value.h>
using namespace std;


float value;
float time_span;
float rate1;
float rate2;
ros::Publisher  value_pub_ ;
ros::Time last_received_time;
ros::Time loop_s ;
void
publish_value_gradually()
{
    last_received_time = ros::Time::now();
    float serial_duration = (last_received_time - loop_s).toSec();
    //std::cout<< serial_duration<<std::endl;
    std_msgs::Float64 msg;
    if(serial_duration < value/rate1){
        msg.data = rate1 *  serial_duration;
        std::cout<< msg.data <<std::endl;
    }else if((serial_duration > value/rate1) && (serial_duration < (value/rate1 + time_span))){
        //std::cout<< serial_duration <<std::endl;
        //std::cout<< "time_span = "<< time_span <<std::endl;
        //std::cout<< "num = "<< (value/rate1 + time_span) <<std::endl;
        msg.data = value;
        std::cout<< msg.data <<std::endl;
    }else if(serial_duration > (value/rate1 + time_span) && serial_duration < (value/rate1 + time_span - value/rate2)){
        msg.data = value + rate2 * (serial_duration - value/rate1 -time_span);
        std::cout<< msg.data <<std::endl;
    }else{
        msg.data = 0;
        std::cout<< msg.data <<std::endl;        
    }

    value_pub_.publish(msg);

}


void
publishValueTimer(const ros::TimerEvent& event)
{
    publish_value_gradually();

}

int main(int argc, char** argv)
{
    ros::init(argc, argv, "param_test");
    ros::NodeHandle pn("~");
    //私有命名空间
    loop_s = ros::Time::now();
    
    pn.param<float>("value",value,1.0);
    pn.param<float>("time_span",time_span,8.0);
    pn.param<float>("rate1",rate1,0.45); //rate1  positive
    pn.param<float>("rate2",rate2,-0.45);//rate2  negative
    std::cout<<"value="<<value<<std::endl;
    std::cout<<"time_span="<<time_span<<std::endl;
    std::cout<<"rate1="<<rate1<<std::endl;
    std::cout<<"rate2="<<rate2<<std::endl;

    value_pub_ = pn.advertise<std_msgs::Float64>("T_Value", 1);
    ros::Timer write_timer_ = pn.createTimer(ros::Duration(1.0/20), &publishValueTimer);
    
    ros::spin();//千万别忘了spin,只有spin了才能真正去触发回调函数
    return 0;
}

