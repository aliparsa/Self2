package com.pishgamanasia.self2.Helper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aliparsa on 10/8/2014.
 */
public class TimerHelper {


    public static void timerFactory(int milisec, int count, final TimerFunction function) {

        //Declare the timer
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                function.tick();
            }

        }, count, milisec);


    }


    public interface TimerFunction {

        public void tick();

    }

}
