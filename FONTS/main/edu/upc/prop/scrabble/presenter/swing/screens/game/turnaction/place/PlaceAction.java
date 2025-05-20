package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.place;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;

import javax.swing.*;

public class PlaceAction extends JPanel {
    private final JPanel parent;
    private JButton putBtn;
    private final PlaceActionMaker placeActionMaker;

    public PlaceAction(JPanel parent, PlaceActionMaker placeActionMaker) {
        setOpaque(false);
        this.placeActionMaker = placeActionMaker;
        this.parent = parent;
        createDrawButton();
        add(putBtn);
    }

    private void createDrawButton() {
        putBtn = new JButton("Place");
        putBtn.setBounds(1400, 575, 75, 50); //hardcoded
        putBtn.addActionListener(e -> {
            parent.remove(putBtn);
            parent.revalidate();
            parent.repaint();

            JButton confirmBtn = new JButton("Confirm");
            confirmBtn.setBounds(1400, 575, 75, 50); //hardcoded
            confirmBtn.addActionListener(confirmEvent -> {
                // llegir per pantalla un moviment (cridar a funcio que converteixi string a moviemnt)
                //placeActionMaker.run(moviment);
            });
            parent.add(confirmBtn);
            parent.revalidate();
            parent.repaint();
            JButton cancelBtn = new JButton("Cancel");
            cancelBtn.setBounds(1400, 575, 75, 50); //hardcoded
            cancelBtn.addActionListener(confirmEvent -> {
                // fer rollback
            });

            parent.add(cancelBtn);
            parent.revalidate();
            parent.repaint();
        });
        parent.add(putBtn);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}

