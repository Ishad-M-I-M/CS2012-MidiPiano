package piano;

import midi.Instrument;
import midi.Midi;
import music.MusicMachine;
import music.NoteEvent;
import music.Pitch;
import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PianoMachine implements MusicMachine {
    private boolean isRecording = false;
    private List<NoteEvent> recording, lastRecording;
    private Set<Pitch> pitchesPlaying;
    private PianoPlayer player;
    private Midi midi;
    private long previousEventTime;

    public PianoMachine(PianoPlayer player) {
        this.lastRecording = new ArrayList<>();
        this.pitchesPlaying = new HashSet<>();
        this.player = player;
        this.previousEventTime = System.currentTimeMillis();
        try {
            this.midi = new Midi();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void toggleRecording(){
        if (isRecording)
            lastRecording = recording;
        else
            recording = new ArrayList<>();
        isRecording = !isRecording;
        this.previousEventTime = System.currentTimeMillis();    // resets the time
    }

    public void beginNote(NoteEvent event){
        Pitch pitch = event.getPitch();
        Instrument instr = event.getInstrument();
        if (pitchesPlaying.contains(pitch)) return;
        event = event.delayed(calculateDelay());        //update the NoteEvent with appropriate delay
        pitchesPlaying.add(pitch);
        midi.beginNote(pitch.toMidiFrequency(), instr);
        addToRecording(event);

    }

    public void endNote(NoteEvent event){
        Pitch pitch = event.getPitch();
        Instrument instr = event.getInstrument();
        if (! pitchesPlaying.contains(pitch)) return;
        event = event.delayed(calculateDelay());        //update the NoteEvent with appropriate delay
        pitchesPlaying.remove(pitch);
        midi.endNote(pitch.toMidiFrequency(),instr);
        addToRecording(event);

    }

    public void addToRecording(NoteEvent event){
        if(isRecording)
            recording.add(event);
    }

    public void requestPlayBack(){
        player.playbackRecording(lastRecording);
    }

    private int calculateDelay(){
        /*
         * calculate the delay between previous NoteEvent and current Note Event
         * */
        long currentEventTime = System.currentTimeMillis();
        int delay = (int)(currentEventTime - previousEventTime);
        this.previousEventTime = currentEventTime;
        return delay;
    }
}
