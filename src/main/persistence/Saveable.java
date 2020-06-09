package persistence;

import java.io.PrintWriter;

//Copied from Saveable class of TellerApp
// Represents data that can be saved to file
public interface Saveable {

    // REQUIRES:
    // MODIFIES: printWriter
    // EFFECTS: writes the saveable to printWriter
    void save(PrintWriter printWriter);
}
