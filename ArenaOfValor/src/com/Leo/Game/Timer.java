package com.Leo.Game;

import javax.swing.*;


import java.awt.HeadlessException;  
import java.awt.BorderLayout;  
import java.awt.Font;  
   
/**
 * <em><b>Timer class extend JFrame</b></em>
 * <p>Use threads to make the timer dynamically display time in the JFrame interface, 
 * and the time is accurate to milliseconds
 * <p>The Timer property has a record start time {@link #programStart}, a start pause time {@link #pauseStart}, 
 * a pause total time {@link #pauseCount}, and a count thread {@link #thread}.
 * @author Leo
 * @version 2.0
 * @since 2018.11.20
 */
public class Timer extends JFrame {  
   
    private static final long serialVersionUID = 1L;
 
    private static final String INITIAL_LABEL_TEXT = "00:00:00 000";  
   
    private long programStart = System.currentTimeMillis();  
   
    private long pauseStart = programStart;  
   
    private long pauseCount = 0;  
    
    private CountingThread thread = new CountingThread();  
   
    private BattleField battleField;
    
    private JLabel label = new JLabel(INITIAL_LABEL_TEXT);  

    /**
     * 
	 * <b><em>Timer continues to count method</em></b>
	 * <p>Calculate the total duration of the pause {@code pauseCount += (System.currentTimeMillis() - pauseStart)} 
	 * and stop the pause {@code thread.stopped = false}.
     * @param battleField battleField
     */
   public void Continue(BattleField battleField){
	   pauseCount += (System.currentTimeMillis() - pauseStart);  
       thread.stopped = false;  
   }
   
   /**
    *<b><em>Pause timing method</em></b> 
    * <p>The timer records the start time {@code pauseStart = System.current<TimeMillis()} 
    * and sets the stop boolean to true {@code thread.stopped = true}.
    * @param battleField battleField
    */
   public void Stop(BattleField battleField) {
	   pauseStart = System.currentTimeMillis();  
       thread.stopped = true;  
   }
   
   /**
    *<b><em>Reset timer method</em></b> 
    * <ul>
    * <li>Set the timer to start the pause time to the program start time {@code pauseStart = programStart;}</li> 
    * <li>pause the total time to reset to zero {@code pauseCount = 0 ;}</li>
    *  <li>the pause time state is false {@code thread.stopped = false;  }</li>
    * <li>and the time stamp is 0 {@code label.setText(INITIAL_LABEL_TEXT);}</li>
    * </ul>
    * @param battleField battleField
    */
    public void Reset(BattleField battleField) {
    	pauseStart = programStart;  
        pauseCount = 0;  
        thread.stopped = false;  
        label.setText(INITIAL_LABEL_TEXT);  
    }
   

    /**
     * <b><em>Timer initialization</em></b> 
     * @param title label 
     * @throws HeadlessException HeadlessException
     */
    public Timer(String title) throws HeadlessException {  
        super(title);  
        setDefaultCloseOperation(EXIT_ON_CLOSE);  
        setLocation(300, 300);  
        setResizable(false);  
   
        setupBorder();  
        setupLabel();  
        thread.start(); 
    }  
   
    /**
     *<b><em>Set the timer interface border</em></b> 
     */
    private void setupBorder() {  
        JPanel contentPane = new JPanel(new BorderLayout());  
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));  
        this.setContentPane(contentPane);  
    }  
   
    /**
     * <b><em>Set the timer's "digital display"</em></b>
     */
    private void setupLabel() {  
        label.setHorizontalAlignment(SwingConstants.CENTER);  
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 40));  
        this.add(label, BorderLayout.CENTER);  
    }  
   
    /**
     * <strong>Count thread inherits thread class</strong>
     * <p>Modify function {@link CountingThread#run()} 
     * @author Leo
     * @version 2.0
     * @since 2018.11.20
     */
    private class CountingThread extends Thread {  
   
        public boolean stopped = true;  
   
        private CountingThread() { 
            setDaemon(true);  
            
        }  
   
        /**
         *<b><em>thread run</em></b> 
         *<p>When the timer pause state is false ({@code stop==false}), calculate the game time 
         *and change the timer label format to time format.
         */
        public void run() {  
            while (true) {  
                if (!stopped) {  
                    long elapsed = System.currentTimeMillis() - programStart - pauseCount;  
                    label.setText(format(elapsed));  
                }  
   
                try {  
                    sleep(1);
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                    System.exit(1);  
                }  
            }  
        }  
   
        /**
         * <strong>Change time format method</strong>
         * <p>Change the time format to h:m:s ms
         * @param elapsed  game time
         * @return Time(format)
         */
        private String format(long elapsed) {  
            int hour, minute, second, milli;  
   
            milli = (int) (elapsed % 1000);  
            elapsed = elapsed / 1000;  
   
            second = (int) (elapsed % 60);  
            elapsed = elapsed / 60;  
   
            minute = (int) (elapsed % 60);  
            elapsed = elapsed / 60;  
   
            hour = (int) (elapsed % 60);  
   
            return String.format("%02d:%02d:%02d %03d", hour, minute, second, milli);  
        }  
    }  
}
