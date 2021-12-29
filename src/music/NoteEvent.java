package music;

import midi.Instrument;
import midi.Midi;

public abstract class NoteEvent {
    protected final Pitch pitch;
    protected final int delay;
    protected final midi.Instrument instr;

    public NoteEvent(Pitch pitch){
        this(pitch,0, Midi.DEFAULT_INSTRUMENT);
    }
    public NoteEvent(Pitch pitch, int delay) {
        this(pitch, delay, Midi.DEFAULT_INSTRUMENT);
    }

    public NoteEvent(Pitch pitch, int delay, midi.Instrument instr){
        this.pitch = pitch;
        this.delay = delay;
        this.instr = instr;
    }

    abstract public NoteEvent delayed(int delay);

    abstract public void execute(MusicMachine m);

    public Pitch getPitch() {
        return pitch;
    }

    public int getDelay(){
        return this.delay;
    }

    public Instrument getInstrument() {
        return instr;
    }

}
