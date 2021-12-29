package music;

import midi.Instrument;

public class EndNote extends NoteEvent{
    public EndNote(Pitch pitch, int delay) {
        super(pitch, delay);
    }

    public EndNote(Pitch pitch) {
        super(pitch);
    }

    public EndNote(Pitch pitch, int delay, Instrument instr) {
        super(pitch, delay, instr);
    }

    @Override
    public void execute(MusicMachine m) {
        m.endNote(this);
    }

    @Override
    public EndNote delayed(int delay) {
        return new EndNote(pitch, delay, instr);
    }
}
