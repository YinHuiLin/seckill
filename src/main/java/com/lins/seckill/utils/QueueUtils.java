package com.lins.seckill.utils;

/**
 * @ClassName QueueUtils
 * @Description TODO
 * @Author lin
 * @Date 2021/3/5 19:16
 * @Version 1.0
 **/
public class QueueUtils {

}

1 /// <summary>
        2     /// 单例类
        3     /// </summary>
        4
 5     {
         6         /// <summary>
         7         /// 静态变量：由CLR保证，在程序第一次使用该类之前被调用，而且只调用一次
         8         /// </summary>
         9         private static readonly QueueUtils _QueueUtils = new QueueUtils();
        10
        11         /// <summary>
        12         /// 声明为private类型的构造函数，禁止外部实例化
        13         /// </summary>
        14         private QueueUtils()
        15         {
        16
        17         }
        18         /// <summary>
        19         /// 声明属性，供外部调用，此处也可以声明成方法
        20         /// </summary>
        21         public static QueueUtils instanse
        22         {
        23             get
        24             {
        25                 return _QueueUtils;
        26             }
        27         }
        28
        29
        30         //下面是队列相关的
        31         Queue queue = new Queue();
        32
        33         private static object o = new object();
        34
        35         public  int getCount()
        36         {
        37             return queue.Count;
        38         }
        39
        40         /// <summary>
        41         /// 入队方法
        42         /// </summary>
        43         /// <param name="myObject"></param>
        44         public void Enqueue(object myObject)
        45         {
        46             lock (o)
        47             {
        48                 queue.Enqueue(myObject);
        49             }
        50         }
        51         /// <summary>
        52         /// 出队操作
        53         /// </summary>
        54         /// <returns></returns>
        55         public object Dequeue()
        56         {
        57             lock (o)
        58             {
        59                 if (queue.Count > 0)
        60                 {
        61                     return queue.Dequeue();
        62                 }
        63             }
        64             return null;
        65         }
        66
        67     }

        单例类
