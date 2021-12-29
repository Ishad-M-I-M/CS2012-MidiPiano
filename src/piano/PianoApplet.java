/**
 * Author: dnj
 * Date: August 29, 2008
 * 6.005 Elements of Software Construction
 * (c) 2008, MIT and Daniel Jackson
 */
package piano;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import music.*;

/**
 * A skeletal applet that shows how to bind methods to key events
 */
public class PianoApplet extends Applet {
    public void init() {
        final PianoPlayer pianoPlayer= new PianoPlayer();
        setBackground(Color.green);

        // this is a standard pattern for associating method calls with GUI events
        // the call to the constructor of KeyAdapter creates an object of an
        // anonymous subclass of KeyAdapter, whose keyPressed method is called
        // when a key is pressed in the GUI
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                char key = (char) e.getKeyCode();
                switch (key) {
                    case 'I':
                        pianoPlayer.nextInstrument();
                        return;
                    case 'R':
                        pianoPlayer.toggleRecording();
                        toggleBackground();
                        return;
                    case 'P':
                        pianoPlayer.requestPlayback();
                        return;
                }
                NoteEvent ne = new BeginNote(keyToPitch(key));
                pianoPlayer.request(ne);

            }
        });

        addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char key = (char) e.getKeyCode();
                try {
                    NoteEvent ne = new EndNote(keyToPitch(key));
                    pianoPlayer.request(ne);
                }
                catch (IllegalArgumentException iae){

                }

            }
        });
    }

    private void toggleBackground(){
        if(getBackground() == Color.green)	setBackground(Color.red);
        else setBackground(Color.green);
    }

    private Pitch keyToPitch(char key){
        return new Pitch(key);
    }

}