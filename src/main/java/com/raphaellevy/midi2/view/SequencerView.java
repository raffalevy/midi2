package com.raphaellevy.midi2.view;

import com.raphaellevy.midi2.Midi2;
import com.raphaellevy.midi2.controller.SequencerController;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Displays the Midi Sequencer.
 */
public class SequencerView {
    
    /**
     * The title of the window
     */
    private static final String FRAME_TITLE = "Midi Sequencer";
    
    /**
     * The width of grid spaces, used for laying out
     */
    private static final int GRID = 10;
    
    @SuppressWarnings("WeakerAccess")
    public static final Dimension PANEL_SIZE = new Dimension(78 * GRID, 44 * GRID);
    
    /**
     * Size of the note sequence display
     */
    private static final Dimension NOTE_DISPLAY_SIZE = new Dimension(60 * GRID, 13 * GRID);
    
    /**
     * The panel in which this view is displayed
     */
    private Panel panel;
    
    /**
     * The frame into which the panel is put
     */
    private JFrame frame;
    
    /**
     * The individual note panels
     */
    private IndividualNote[] individualNotes = new IndividualNote[5];
    
    /**
     * The controller for this view
     */
    private SequencerController controller = null;
    
    private SequencerView() {
    }
    
    /**
     * Open a new SequencerView
     *
     * @param controller The controller for this view
     * @return the created SequencerView
     */
    public static SequencerView open(SequencerController controller) {
        
        //Make a new SequencerView
        SequencerView view = new SequencerView();
        view.controller = controller;
        
        try {
            SwingUtilities.invokeAndWait(() -> {
                
                //Create the panel
                view.panel = view.new Panel();
                
                //Set up the JFrame
                view.frame = new JFrame(FRAME_TITLE);
                view.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                
                //Lay out the Panel
                view.panel.setup();
                
                //Add the Panel to the JFrame
                view.frame.setContentPane(view.panel);
                view.frame.pack();
                
                //Make the frame visible
                view.frame.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        //Return the SequencerView
        return view;
        
    }
    
    /**
     * Reload the components
     */
    public void reload() {
        frame.revalidate();
        frame.repaint();
    }
    
    /**
     * Set the text of a note
     * @param index which note to set the text of
     * @param text the text to set it to
     */
    public void setNoteText(int index, String text) {
        individualNotes[index].label.setText(text);
        reload();
    }
    
    /**
     * Set the background color of a note
     * @param index the note to change the color of
     * @param color the color to change it to
     */
    public void setNoteColor(int index, Color color) {
        individualNotes[index].setBackground(color);
    }
    
    /**
     * Resets the background color of a note
     * @param index the note to change the color of
     */
    public void resetNoteColor(int index) {
        IndividualNote n = individualNotes[index];
        if (n.number % 2 == 0) {
            n.setBackground(new Color(0xD8ECFF));
        } else {
            n.setBackground(new Color(0xD1E8FF));
        }
        reload();
    }
    
    /**
     * The panel in which the view is displayed
     */
    private class Panel extends JPanel {
        
        private Panel() {
            super();
            setLayout(null);
            setPreferredSize(PANEL_SIZE);
            setOpaque(true);
            setBackground(new Color(0xF8F7FF));
        }
        
        /**
         * Lay out components
         */
        private void setup() {
            add(new NoteDisplay());
        }
    }
    
    /**
     * Displays the inputted notes
     */
    private class NoteDisplay extends JPanel {
        private NoteDisplay() {
            super();
            setBounds(9 * GRID, 8 * GRID, (int) NOTE_DISPLAY_SIZE.getWidth(), (int) NOTE_DISPLAY_SIZE.getHeight());
            setLayout(null);
            for ( int i = 0; i < 5; i++ ) {
                individualNotes[i] = new IndividualNote(i);
                add(individualNotes[i]);
            }
        }
    }
    
    /**
     * The segment of the display for each individual note
     */
    private class IndividualNote extends JPanel {
        
        private int number;
        private JLabel label;
        
        private IndividualNote(int number) {
            super();
            this.number = number;
            setBounds(number * 12 * GRID, 0, 12 * GRID, 13 * GRID);
            if (number % 2 == 0) {
                setBackground(new Color(0xD8ECFF));
            } else {
                setBackground(new Color(0xD1E8FF));
            }
            setOpaque(true);
            setLayout(null);
            label = new JLabel("C");
            label.setFont(Midi2.lato12.deriveFont(48.0f));
            label.setBounds(3 * GRID, 3 * GRID, 6 * GRID, 7 * GRID);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            add(label);
            
        }
    }
}