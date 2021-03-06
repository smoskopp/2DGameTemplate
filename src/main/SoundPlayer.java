package main;

import java.util.HashMap;
import java.util.Map;

import static main.Sound.clips;

/**
 * Provides functions for playing, stopping, pausing any Sound represented by a clip
 */
public class SoundPlayer {

    private enum statusType{ON_PAUSE, ON_STOP, ON_PLAY};

    private static Map<String, Thread> threads = new HashMap<>();
    private static Map<String, statusType> status = new HashMap<>();

    /**
     *
     * @param clipName
     * @param onRepeat
     */
    public static void play(String clipName, boolean onRepeat) {
        status.put(clipName, statusType.ON_PLAY);
        Thread thread = new Thread(() -> {
            boolean repeat = onRepeat;
            do {
                try {
                    clips.get(clipName).setMicrosecondPosition(0);
                    clips.get(clipName).setFramePosition(0);
                    clips.get(clipName).start();
                    while (clips.get(clipName).getFramePosition() < clips.get(clipName).getFrameLength()) {
                        Thread.sleep(33);
                        TryAgain:
                        {
                            switch(status.get(clipName)) {
                                case ON_PAUSE:
                                    clips.get(clipName).stop();
                                    break TryAgain;
                                case ON_STOP:
                                    clips.get(clipName).setFramePosition(clips.get(clipName).getFrameLength());
                                    repeat = false;
                                default:
                                    clips.get(clipName).start();
                                    break;
                            }
                            Thread.sleep(33);
                        }
                    }
                    clips.get(clipName).stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }while(repeat);

            if(!threads.containsKey(clipName))
                threads.remove(clipName);
        });
        if(!threads.containsKey(clipName)) {
            threads.put(clipName, thread);
            status.put(clipName, statusType.ON_PLAY);
        }
        thread.start();
    }

    /**
     *
     * @param clipName
     */
    public static void pause(String clipName){
        if(status.containsKey(clipName))
            status.replace(clipName, statusType.ON_PAUSE);
    }

    /**
     *
     * @param clipName
     */
    public static void stop(String clipName){
        if(status.containsKey(clipName))
            status.replace(clipName, statusType.ON_STOP);
    }

    /**
     *
     * @param clipName
     */
    public static void replay(String clipName){
        if(status.containsKey(clipName))
            status.replace(clipName, statusType.ON_PLAY);
    }
}