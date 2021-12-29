package piano;

import midi.Midi;
import music.NoteEvent;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PianoPlayer {
    private final BlockingQueue<NoteEvent> queue, delayQueue;
    private final PianoMachine machine;

    public PianoPlayer() {
        queue = new LinkedBlockingQueue<NoteEvent>();
        delayQueue = new LinkedBlockingQueue<NoteEvent>();
        machine = new PianoMachine(this);
        new Thread(this::processQueue).start();
        new Thread(this::processDelayQueue).start();
    }

    public void request(NoteEvent e){
        try {
            queue.put(e);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void requestPlayback(){
        machine.requestPlayBack();
    }

    public void toggleRecording(){
        machine.toggleRecording();
    }

    public void playbackRecording (List<NoteEvent> recording){
        for(NoteEvent e: recording){
            try {
                delayQueue.put(e);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    public void processQueue(){
        while (true){
            try {
                NoteEvent e = queue.take();
                e.execute(machine);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

        }
    }

    public void processDelayQueue(){
        while (true){
            try {
                NoteEvent e = delayQueue.take();
                midi.Midi.wait(e.getDelay());
                queue.put(e);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

        }
    }

    public void nextInstrument(){
        midi.Midi.nextInstrument();
        System.out.println("Instrument changed to :"+ Midi.DEFAULT_INSTRUMENT);
    }
}
