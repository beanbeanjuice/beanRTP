package com.beanbeanjuice.beanrtp.utility.countdown;

import com.beanbeanjuice.beanrtp.BeanRTP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CountdownTimer {

    private int countdownTime;
    private final Player player;
    private final Location initialLocation;
    private final CountdownDisplay display;
    private final Callable<Void> completedFunction;
    private final Callable<Void> failedFunction;

    private boolean isRunning;

    public CountdownTimer(int countdownTime, Player player, Location initialLocation,
                          CountdownDisplay display, Callable<Void> completedFunction, Callable<Void> failedFunction) {
        this.countdownTime = countdownTime;
        this.player = player;
        this.initialLocation = initialLocation;
        this.display = display;
        this.completedFunction = completedFunction;
        this.failedFunction = failedFunction;
        isRunning = false;
    }

    public void setCountdownTime(int time) {
        this.countdownTime = time;
    }

    public void start(BeanRTP plugin) {
        isRunning = true;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {

                while (hasNotMoved() && countdownTime > 0) {
                    display.run(countdownTime);
                    Thread.sleep(TimeUnit.SECONDS.toMillis(1));

                    countdownTime--;
                }

                isRunning = false;

                if (hasNotMoved()) completedFunction.call();
                else failedFunction.call();

            } catch (Exception ignored) { }
        });
    }

    public boolean isRunning() {
        return isRunning;
    }

    private boolean hasNotMoved() {
        return initialLocation.distance(player.getLocation()) == 0;
    }

}
