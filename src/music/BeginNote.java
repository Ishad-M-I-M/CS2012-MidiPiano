package music;

import midi.Instrument;

public class BeginNote extends NoteEvent {

    public BeginNote(Pitch pitch){
        super(pitch);
    }

    public BeginNote(Pitch pitch, int delay) {
        super(pitch, delay);
    }

    public BeginNote(Pitch pitch, int delay, Instrument instr) {
        super(pitch, delay, instr);
    }

    @Override
    public  void execute (MusicMachine m){
        m.beginNote(this);
    }

    @Override
    public BeginNote delayed(int delay) {
        return new BeginNote(pitch, delay, instr);
    }
}
