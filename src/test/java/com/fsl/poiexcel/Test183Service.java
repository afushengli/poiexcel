package com.fsl.poiexcel;

/**
 * @Auther: chenrj
 * @Date: 2019/8/14 18:12
 * @Description:
 */
public class Test183Service {

    public static void show(String content) {
        //
        //Object aa = new Object();
        String aa = new String(content);
        synchronized (aa) {
            while(true) {
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
  }



    public static void main(String[] args) {
        MyTaskThread mtt = new MyTaskThread();
        Thread t = new Thread(mtt);
        Thread t1 = new Thread(mtt);
        t.start();
        t1.start();
    }







}


class MyTaskThread implements Runnable {
    Test183Service ts = new Test183Service();

    @Override
    public void run() {
        ts.show("aa");
    }
}