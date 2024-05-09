/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GameAbilities;

import Nuts.NutGenerator;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Stack;

/**
 *
 * @author oscar
 */
public class GameAbility {

    public int itemIndex;
    private int itemLevel;
    private int duration;
    private int factor;
    private boolean setStartTime = true;
    public boolean remove = false;
    public boolean pause = false;
    public boolean multiNut;
    public boolean multiAcorn;
    private Stack<LocalTime> newTime = new Stack<>();
    private Stack<Integer> timeLimit = new Stack<>();

    public GameAbility(int itemIndex, int itemLevel, int duration) {
        this.itemIndex = itemIndex;
        this.itemLevel = itemLevel;
        this.duration = duration;
        timeLimit.push(duration);
        switch (itemIndex) {
            case 1:
                setFactor();
                Perks.changeCollect(factor, false);
                break;
            case 8:
                setFactor();
                NutGenerator.changeCollect(factor, false);
                break;
            default:
                break;
        }
    }

    private void setFactor() {
        switch (itemLevel) {
            case 1:
                factor = 2;
                break;
            case 2:
                factor = 3;
                break;
            case 3:
                factor = 4;
                break;
            default:
                break;
        }
    }

    public void check() {
        if (setStartTime) {
            newTime.push(LocalTime.now());
            setStartTime = false;
        } else if (!setStartTime && !pause) {
            if (capturedTime(timeLimit.lastElement()) == -1) {
                setStartTime = true;
                duration = timeLimit.firstElement();
                switch (itemIndex) {
                    case 1:
                        Perks.changeCollect(-factor, false);
                        break;
                    case 8:
                        NutGenerator.changeCollect(-factor, false);
                        break;
                    default:
                        break;
                }
                remove = true;
            } else {
                
            }
        }
    }

    public long capturedTime(long timeMax) {
        long totalSeconds = 0;
        LocalTime currentTime = LocalTime.now();
        Duration timeDifference = Duration.between(newTime.lastElement(), currentTime);
        if (!pause) {
            totalSeconds = timeDifference.getSeconds();
        }
        if (totalSeconds <= timeMax && !pause) {
            if (!pause) {
                duration = (int) (timeMax - totalSeconds);
            }
            return totalSeconds;
        } else {
            return -1;
        }
    }

    public void pauseTimeCapturing(boolean pause) {
        this.pause = pause;
        if (pause) {
            timeLimit.push(duration);
        } else {
            duration = timeLimit.lastElement();
            newTime.push(LocalTime.now());
        }
    }

}
