package com.raphaellevy.midi2.controller;

import com.raphaellevy.midi2.Midi2;

/**
 * Handles menu events
 */
public class MenuBarController {

    /**
     * The sequencer controller
     */
    private final SequencerController parent;

    /**
     * The application
     */
    private final Midi2 app;

    public MenuBarController(SequencerController parent) {
        this.parent = parent;
        app = parent.getApp();
    }

    public void newItem() {
        app.clearNotes();
    }

    public void openItem() {
        app.open();
    }

    public void saveItem() {
        app.save();
    }

    public void deleteItem() {
        app.deleteNote();
    }

    public void clearItem() {
        app.clearNotes();
    }

    public void playItem() {
        app.playSequence();
    }

    public void stopItem() {
        app.stopPlaying();
    }

    public void addRestItem() {
        app.addRest();
    }

    public void continueItem() {
        app.addContinue();
    }

    public void instructionsItem() {
        app.displayInstructions();
    }
}