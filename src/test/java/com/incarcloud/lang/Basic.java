package com.incarcloud.lang;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Basic {
    private Logger s_logger = LoggerFactory.getLogger(Basic.class);

    @Test
    public void action(){
        Action<String> actionShow = (String txt)->{
            s_logger.info("action is running with argument '{}'", txt);
        };

        actionShow.run("Hello Action");
    }

    @Test
    public void func(){
        Func<Integer, String> funcSize = (String txt)->{
            s_logger.info("'{}' has {} characters", txt, txt.length());
            return txt.length();
        };

        String txt = "Func";
        int size = funcSize.call(txt);
        Assert.assertEquals(txt.length(), size);
    }

    @Test
    public void action2(){
        Action2<Integer, String> action2 = (n, txt)->{
            for(int i=0;i<n;i++){
                s_logger.info(txt);
            }
        };

        String txt = "Hello Action2";
        int nTimes = 3;
        s_logger.info("Show `{}` {} times:", txt, nTimes);

        action2.run(nTimes, "Hello Action2");
    }

    @Test
    public void func5(){
        Func5<Float, Integer, Integer, Integer, Integer, Integer> func5 = (x1, x2, x3, x4, x5)->{
            float fSum = 1.0f*(x1+x2+x3+x4+x5);
            s_logger.info("the sum of {}, {}, {}, {}, {} is {}",
                    x1, x2, x3, x4, x5, fSum);
            return fSum;
        };

        float fSum = func5.call(1, 2, 3, 4, 5);

        Assert.assertEquals(fSum, 15.0f, 0.001f);
    }

    @Test
    public void submit(){
        ExecutorForAcFunction pool = new ExecutorForAcFunction(Executors.newFixedThreadPool(2));

        Action<Integer> action = (i)->{
            s_logger.info("Action -> {}", i);
        };

        for(int i=0;i<5;i++){
            pool.submit(action, i);
        }

        try {
            pool.getExecSrv().shutdown();
            pool.getExecSrv().awaitTermination(1, TimeUnit.SECONDS);
        }catch (InterruptedException ex){
            // ignore
        }
    }

    @Test
    public void schedule(){
        ScheduledExecutorForAcFunction scheduler = new ScheduledExecutorForAcFunction(Executors.newSingleThreadScheduledExecutor());
        scheduler.schedule(this::fibo, 500, TimeUnit.MILLISECONDS, scheduler, 1, 1);

        try {
            s_logger.info("Let's count fibonacci:");
            Thread.sleep(1000 * 10);
            scheduler.getScheduler().shutdown();
            scheduler.getScheduler().awaitTermination(2, TimeUnit.SECONDS);
        }catch (InterruptedException ex){

        }

    }

    private void fibo(ScheduledExecutorForAcFunction scheduler, Integer a, Integer b){
        s_logger.info("{}", String.format("%5d", a));
        scheduler.schedule(this::fibo, 500, TimeUnit.MILLISECONDS, scheduler, b, a+b);
    }
}
