package snake;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.TreeMap;

public class HighScorePage extends Content {

    JTable table;
    TreeMap <Double, String> highScore;
    File data;

    HighScorePage(GameWindow owner) {
        super(owner);

        readData();
        if (highScore == null) highScore = new TreeMap <> ();

        label.setText("Рекорды");
        add(label);

        table = new JTable(5, 3);
        table.setPreferredSize(new Dimension(300, 195));
        table.setFont(font);
        table.setRowHeight(34);

        {
            layout.putConstraint(SpringLayout.WEST , label, 130,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, label, 20,
                    SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.WEST , table, 50,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, table, 70,
                    SpringLayout.NORTH, label);
            layout.putConstraint(SpringLayout.WEST , mainMenu, 100,
                    SpringLayout.WEST , this);
            layout.putConstraint(SpringLayout.NORTH, mainMenu, 20,
                    SpringLayout.SOUTH, table);
        }

        table.setGridColor(backgroundColor);
        table.setBackground(backgroundColor);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(String.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        fillTable();
        table.setEnabled(false);
        add(table);

        setButtonSettings(mainMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        owner.setContentPane(owner.lobby);
        owner.repaint();
    }

    void clearTable()
    {
        table.setEnabled(true);
        for(int i = 0; i < 5; i++)
        {
            table.setValueAt("", i, 0);
            table.setValueAt("", i, 1);
            table.setValueAt("", i, 2);
        }
        table.setEnabled(false);
    }

    void fillTable()
    {
        if (highScore.size() != 0)
        {
            if(highScore.size() > 5) highScore.remove(highScore.firstKey());
            double key = highScore.lastKey();
            for(int i = 0; i < highScore.size(); i++)
            {
                table.setValueAt(i + 1, i, 0);
                table.setValueAt(highScore.get(key), i, 1);
                table.setValueAt((int)Math.ceil(key), i, 2);
                if (highScore.lowerKey(key) != null) key = highScore.lowerKey(key);
            }
        }
    }

    void writeData()
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("records.dat"));
            oos.writeObject(highScore);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    private void readData()
    {

        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("records.dat"));
            highScore = (TreeMap) ois.readObject();
        }
        catch(Exception ex){

            data = new File("records.dat");
            System.out.println(ex.getMessage());
        }
    }
}