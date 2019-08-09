package com.fsl.poiexcel.job;

import com.fsl.poiexcel.mapper.OperatelockMapper;
import com.fsl.poiexcel.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: chenrj
 * @Date: 2019/7/23 17:41
 * @Description:
 */
@Component
public class Schedule {
    private Logger logger = LoggerFactory.getLogger(Schedule.class);

    /**
     * https://www.cnblogs.com/mmzs/p/10161936.html
     * 定时任务方法 此处没两秒执行一次
     * @Scheduled:设置定时任务
     * cron属性：cron表达式。定时任务触发是时间的一个字符串表达形式
     */


    @Autowired      //注入mapper
    private OperatelockMapper operatelockMapper;

    @Autowired
    private JedisUtil jedisUtil;


    //定时任务 0 */1 * * * ? 每分钟执行一次
    @Scheduled(cron="10 * * * * ?")
    public void scheduledMethod(){
       /* logger.info("定时器被触发开始:"+new Date());*/



        /*  客户端每10s（举个例子）向服务器端发一个信息，
      服务端将发这个数据的信息和保存时间存入表中，随后应用用定时任务不断的检查当前时间和刚刚存入的时间的差值：
      这里用到了这个函数TIMESTAMPDIFF(SECOND, dataEnterTime,now())，
      如果这个值大于20s，就判定断网。其实就是定时任务开启，不断扫描最后一次发送时间和现在时间的差值，
      时间太长的话，那就是断了，而不是每次都主动向客户端发信息。
        原文：https://blog.csdn.net/u014792342/article/details/79076770
        */

     /*  List<Operatelock> list = operatelockMapper.selectAll();

       if(!CollectionUtils.isEmpty(list)){

           for(Operatelock o : list){
               SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               logger.info("当前时间:" +simpleDateFormat.format( new Date())+"更新时间："+ simpleDateFormat.format(o.getUpdateTime()));
           }


           long time = new Date().getTime();
           List  <Operatelock>  opList =  list.stream().filter((o) -> (time -o.getUpdateTime().getTime() > Constant.EXPIRE_DELETE_TIME )).collect(Collectors.toList());

           List  <Long>  idList = opList.stream().map(o->o.getId()).collect(Collectors.toList());
           List <String> fileList = opList.stream().map(o->o.getFileName()).collect(Collectors.toList());




           if(!CollectionUtils.isEmpty(idList)){

               for(String fileName:fileList ){
                   StrBuilder key = new StrBuilder();
                   key.append(Constant.Redis.TOKEN_PREFIX).append(fileName);
                   Long del = jedisUtil.del(key.toString());
               }


               Map<String, Object > map = new HashMap<>();
               map.put("idList",idList);
               operatelockMapper.batchDeletebyid(map);
           }
       }
        logger.info("定时器被触发结束:"+new Date());*/
    }


}
