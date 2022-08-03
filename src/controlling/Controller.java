package controlling;

import graphics.GUI;

public class Controller
{

    private static final GUI MAIN_FRAME;
    private final Checker checker;

    public Controller()
    {
        checker = new Checker();
    }

    static {
        MAIN_FRAME = new GUI();
    }

    public static void main(String[] args)
    {
        Controller cont = new Controller();

        MAIN_FRAME.init();

        Thread game = new Thread(MAIN_FRAME);
        Thread checkingThread = new Thread(cont.checker);

        game.setPriority(Thread.NORM_PRIORITY);
        checkingThread.setPriority(Thread.MIN_PRIORITY);

        game.start();
        checkingThread.start();
    }

    private class Checker implements Runnable
    {

        @Override
        public void run()
        {
            while (true) {
                while (MAIN_FRAME.isGameRunning()) {
                    if (!MAIN_FRAME.getFinalMessage().equals("")) {
                        MAIN_FRAME.endOfGame();
                    }
                    try {
                        synchronized (this) {
                            this.wait(100);
                        }
                    } catch (InterruptedException e) {
                    }
                }
                
                try {
                    synchronized (this) {
                        this.wait(200);
                    }
                } catch (InterruptedException e) {
                }
            }

        }

    }

}
