package com.fsl.poiexcel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiexcelApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println(f(3));
    }


   public int f(int n){
            //f(0) = 0,f(1) = 1，等价于 n<=1时，f(n) = n。
            if(n <= 1){
                   return n;
            }

            if(n == 2){
              return 2;
             }

                return  f(n-1) + f(n-2);
        }

}
