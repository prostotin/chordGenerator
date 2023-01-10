/*
 * Chord generator by IG @olexprost.
 * E-mail: oleksandr.prostotin@gmail.com
 * Feel free to contact me for any questions/inqueries.
 */

import java.awt.Graphics;
import java.awt.BasicStroke;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class Refactoring extends JPanel implements MouseListener {

    static String vLines = JOptionPane.showInputDialog(null, "How many strings?");
    static String hLines = JOptionPane.showInputDialog(null, "How many frets?");
    static int verticalLines = Integer.parseInt(vLines);
    static int horizontalLines = Integer.parseInt(hLines);
    static BufferedReader in = null;
    static PrintWriter out = null;
    static File source = new File("DataFile.txt");
    static int[][] circleArray = new int[26][26];
    static JPanel bottomPanel;
    static Container parent;
    static JPanel contentPanel;
    static JPanel fingerPanel;
    static JButton chooseButton = new JButton("Chord");
    static JButton resetAll = new JButton("Clear");
    static JButton chooseColor1 = new JButton("Top");
    static JButton chooseColor2 = new JButton("Bottom");
    static JButton chooseColor3 = new JButton("Text");
    static JButton exportButton = new JButton("PNG");

    static JButton flipColor = new JButton("Flip");
    static JButton savePreset = new JButton("Save");
    static JButton loadPreset = new JButton("Load");
    static JButton changeMode = new JButton("Mode");

    static Object[] errorobject = {"File saved"};
    static JTextField chordName = new JTextField();
    static JTextField chordOffset = new JTextField();
    static JFrame chordFrame;

    static File sourceFile = new File("chords.txt");
     static File sourceFile1 = new File("chordsUke.txt");
    static JComboBox chord = new JComboBox();
     static JComboBox chordUke = new JComboBox();
    static String[][] chordVar = new String[1000][15];


    final static String[] guitars = { "Guitar", "Uke" };
    static JComboBox guitOrUke = new JComboBox(guitars);
    static int chordVarCounter = 0;
    static int chordVarCounterUke = 0;
    ////////////////////////////////////////////////

    public static void main(String args[]) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        new Refactoring();
    }
    ////gggg

    public Refactoring() {

        AutoCompleteDecorator.decorate(chord);
         AutoCompleteDecorator.decorate(chord);
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        // TODO code application logic here
        bottomPanel = new JPanel();
        fingerPanel = new JPanel();
        JFrame frame = new JFrame("Chord Image Generator by @OlexProst");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chordName = new JTextField("Chord");

        chordOffset = new JTextField("");

        chordFrame = new JFrame("Chord graphics");
        chordFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chordFrame.getContentPane().add(new ChordPanel());
        chordFrame.pack();
        chordFrame.setVisible(true);
        chordFrame.addMouseListener(this);

        GridLayout layout = new GridLayout(7, 6, 0, 0);

        fingerPanel.setLayout(layout);


        bottomPanel.add(chooseButton);
        bottomPanel.add(chordName);
        bottomPanel.add(chordOffset);
        bottomPanel.add(resetAll);

        bottomPanel.add(chooseColor1);
        bottomPanel.add(chooseColor2);
        bottomPanel.add(chooseColor3);
        bottomPanel.add(exportButton);
        bottomPanel.add(flipColor);
        bottomPanel.add(savePreset);
        bottomPanel.add(loadPreset);
        bottomPanel.add(changeMode);

        bottomPanel.setLayout(new GridLayout(3, 3));
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(fingerPanel, BorderLayout.CENTER);
        frame.setSize(300, 678);
        // frame.setVisible(true);
        chordFrame.setResizable(false);
        chordFrame.add(bottomPanel, BorderLayout.SOUTH);
        chordFrame.setSize(306, 757);
        ImageIcon icon;
        icon = new ImageIcon("images/smol.png");
        chordFrame.setIconImage(icon.getImage());

        // chordFrame.add(BorderLayout.SOUTH);
        contentPanel = (JPanel) chordFrame.getContentPane();
        chooseButton.addActionListener((ActionEvent e) -> {
             JOptionPane.showMessageDialog(null, guitOrUke, "Choose the instrument", JOptionPane.QUESTION_MESSAGE);
               int chosenInstrument = guitOrUke.getSelectedIndex();
               System.out.println(chosenInstrument);

               if(chosenInstrument == 0 ){

                   matchInstrument(chosenInstrument);


            String line = "";
            readChords(line, chordVar);
            JOptionPane.showMessageDialog(null, chord, "Choose a chord", JOptionPane.QUESTION_MESSAGE);
            String chosenChord = (String) chord.getSelectedItem();
            chordName.setText(chosenChord);
            int chosenIndex = chord.getSelectedIndex();
             JComboBox chordVarJCombo = new JComboBox(chordVar[chosenIndex]);
            JOptionPane.showMessageDialog(null, chordVarJCombo, "Choose The variation", JOptionPane.QUESTION_MESSAGE);
            String chosenVariation = (String) chordVarJCombo.getSelectedItem();

            String[] splitVariation = chosenVariation.split(",");
            int[] splitVariationInt = new int[10];

            for (int q = 0; q < verticalLines + 1; q++) {
                for (int z = 0; z < horizontalLines + 1; z++) {
                    circleArray[q][z] = 0;
                }

            }
            for (int a = 0; a < 6; a++) {
                try {
                    splitVariationInt[a] = Integer.parseInt(splitVariation[a]);

                } catch (NumberFormatException d) {
                    splitVariationInt[a] = 0;
                }
                //System.out.println(splitVariationInt[a]);
                circleArray[splitVariationInt[a]][a] = 1;
            }
            int max = 0;
            int min = horizontalLines;
            System.out.println(horizontalLines);
            for(int a = 0; a < 6; a ++){
                if( splitVariationInt[a] > max){
                    max =  splitVariationInt[a];
                }
                if(splitVariationInt[a] < min && splitVariationInt[a] != 0){

                    min = splitVariationInt[a];
                }

            }
            System.out.println(min);
            System.out.println(max);
             chordOffset.setText("");
            if(max > horizontalLines){


                for (int q = 0; q < verticalLines + 1; q++) {
                for (int z = 0; z < horizontalLines + 1; z++) {
                    circleArray[q][z] = 0;
                }
                }


                int offset = (max -  min)  ;
                chordOffset.setText(""+(min));
                for(int a = 0; a < 6; a ++){
                    splitVariationInt[a] -= (min - 1);

                    try{
                      circleArray[splitVariationInt[a]][a] = 1;
                    } catch(ArrayIndexOutOfBoundsException q){
                        //???do stuff or no
                    }

                }
            }













        } else if(chosenInstrument == 1){
            matchInstrument(chosenInstrument);

            chordVarCounterUke = 0;
           String[][] chordVarUke = new String[1000][10];
             String lineUke = "";
             int innerCounter = 0;

             readChordsUke(lineUke,innerCounter,chordVarUke);


            JOptionPane.showMessageDialog(null, chordUke, "Choose a chord", JOptionPane.QUESTION_MESSAGE);
            String chosenChord = (String) chordUke.getSelectedItem();
            chordName.setText(chosenChord);
            int chosenIndex = chordUke.getSelectedIndex();
               JComboBox chordVarUkeJCombo = new JComboBox(chordVarUke[chosenIndex]);
            JOptionPane.showMessageDialog(null, chordVarUkeJCombo, "Choose The variation", JOptionPane.QUESTION_MESSAGE);
            String chosenVariation = (String) chordVarUkeJCombo.getSelectedItem();

            String[] splitVariation = chosenVariation.split(",");
            int[] splitVariationInt = new int[10];

            for (int q = 0; q < verticalLines + 1; q++) {
                for (int z = 0; z < horizontalLines + 1; z++) {
                    circleArray[q][z] = 0;
                }

            }
            for (int a = 0; a < 4; a++) {
                try {
                    splitVariationInt[a] = Integer.parseInt(splitVariation[a]);

                } catch (NumberFormatException d) {
                    splitVariationInt[a] = 0;
                }
                //System.out.println(splitVariationInt[a]);
                circleArray[splitVariationInt[a]][a] = 1;
            }
            int max = 0;
            int min = horizontalLines;
            System.out.println(horizontalLines);
            for(int a = 0; a < 6; a ++){
                if( splitVariationInt[a] > max){
                    max =  splitVariationInt[a];
                }
                if(splitVariationInt[a] < min && splitVariationInt[a] != 0){

                    min = splitVariationInt[a];
                }

            }
            System.out.println(min);
            System.out.println(max);
             chordOffset.setText("");
            if(max > horizontalLines){


                for (int q = 0; q < verticalLines + 1; q++) {
                for (int z = 0; z < horizontalLines + 1; z++) {
                    circleArray[q][z] = 0;
                }
                }


                int offset = (max -  min)  ;
                chordOffset.setText(""+(min));
                for(int a = 0; a < 6; a ++){
                    splitVariationInt[a] -= (min - 1);

                    try{
                      circleArray[splitVariationInt[a]][a] = 1;
                    } catch(ArrayIndexOutOfBoundsException q){
                        //???do stuff or no
                    }

                }
            }

























        }
            //System.out.println(chosenVariation);
        });

        resetAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int a = 0; a < verticalLines + 1; a++) {
                    for (int b = 0; b < horizontalLines + 1; b++) {
                        circleArray[a][b] = 0;
                    }

                }
            }
        });
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                contentPanel = (JPanel) chordFrame.getContentPane();
                ChordPanel.save();

            }
        });

        chooseColor1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ChordPanel.color1 = ColorChooserExample.action();
            }
        });
        chooseColor2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ChordPanel.color2 = ColorChooserExample.action();
            }
        });
        chooseColor3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ChordPanel.color3 = ColorChooserExample.action();
            }
        });

        flipColor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int colorBufferRed = ChordPanel.color1.getRed();
                int colorBufferGreen = ChordPanel.color1.getGreen();
                int colorBufferBlue = ChordPanel.color1.getBlue();

                ChordPanel.color1 = new java.awt.Color(ChordPanel.color2.getRed(), ChordPanel.color2.getGreen(), ChordPanel.color2.getBlue());
                ChordPanel.color2 = new java.awt.Color(colorBufferRed, colorBufferGreen, colorBufferBlue);

            }
        });

        savePreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String presetName = JOptionPane.showInputDialog(null, "Name of preset?");
                try {
                    out = new PrintWriter(new BufferedWriter(new FileWriter(source, true)), true);
                    out.println("Name;" + presetName);
                    out.print(ChordPanel.color1.getRed() + ";");
                    out.print(ChordPanel.color1.getGreen() + ";");
                    out.println(ChordPanel.color1.getBlue());

                    out.print(ChordPanel.color2.getRed() + ";");
                    out.print(ChordPanel.color2.getGreen() + ";");
                    out.println(ChordPanel.color2.getBlue());

                    out.print(ChordPanel.color3.getRed() + ";");
                    out.print(ChordPanel.color3.getGreen() + ";");
                    out.println(ChordPanel.color3.getBlue());
                    out.println("---");

                    out.close();
                } catch (IOException q) {
                    System.out.println("Problem opening File");
                }
            }

        });

        loadPreset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] presetList = new String[200];
                int presetCounter = 0;
                String line = null;
                try {

                    FileReader fileReader
                            = new FileReader("DataFile.txt");

                    BufferedReader bufferedReader
                            = new BufferedReader(fileReader);

                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.indexOf("Name") == 0) {

                            String nameOfPreset[] = line.split(";");
                            presetList[presetCounter] = nameOfPreset[1];
                            presetCounter++;

                        }
                    }

                    bufferedReader.close();
                } catch (FileNotFoundException ex) {
                    System.out.println(
                            "Unable to open file '"
                            + "DataFile.txt" + "'");
                } catch (IOException ex) {
                    System.out.println(
                            "Error reading file '"
                            + "DataFile.txt" + "'");

                }
                JComboBox c = new JComboBox(presetList);
                AutoCompleteDecorator.decorate(c);
                JOptionPane.showMessageDialog(null, c, "Select the preset", JOptionPane.QUESTION_MESSAGE);
                String chosenPreset = (String) c.getSelectedItem();
                System.out.println(chosenPreset);

                String chosenPresetLine = "Name;" + chosenPreset;
                String chosenPresetRGB[] = new String[3];
                String chosenPresetRGB1[] = new String[3];
                String chosenPresetRGB2[] = new String[3];

                try {

                    FileReader fileReader
                            = new FileReader("DataFile.txt");

                    BufferedReader bufferedReader
                            = new BufferedReader(fileReader);

                    while ((line = bufferedReader.readLine()) != null) {

                        if (line.equals(chosenPresetLine)) {
                            String newLine = bufferedReader.readLine();
                            String newLine1 = bufferedReader.readLine();
                            String newLine2 = bufferedReader.readLine();
                            chosenPresetRGB = newLine.split(";");
                            chosenPresetRGB1 = newLine1.split(";");
                            chosenPresetRGB2 = newLine2.split(";");

                            ChordPanel.color1 = new java.awt.Color(Integer.parseInt(chosenPresetRGB[0]), Integer.parseInt(chosenPresetRGB[1]), Integer.parseInt(chosenPresetRGB[2]));
                            ChordPanel.color2 = new java.awt.Color(Integer.parseInt(chosenPresetRGB1[0]), Integer.parseInt(chosenPresetRGB1[1]), Integer.parseInt(chosenPresetRGB1[2]));
                            ChordPanel.color3 = new java.awt.Color(Integer.parseInt(chosenPresetRGB2[0]), Integer.parseInt(chosenPresetRGB2[1]), Integer.parseInt(chosenPresetRGB2[2]));

                        }

                    }

                    bufferedReader.close();
                } catch (FileNotFoundException ex) {
                    System.out.println(
                            "Unable to open file '"
                            + "DataFile.txt" + "'");
                } catch (IOException ex) {
                    System.out.println(
                            "Error reading file '"
                            + "DataFile.txt" + "'");

                }

            }

        });

        changeMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                vLines = JOptionPane.showInputDialog(null, "How many strings?");
                hLines = JOptionPane.showInputDialog(null, "How many frets?");
                verticalLines = Integer.parseInt(vLines);
                horizontalLines = Integer.parseInt(hLines);
            }
        });

    }
public void matchInstrument(int choice){

     for (int q = 0; q < verticalLines + 1; q++) {
                for (int z = 0; z < horizontalLines + 1; z++) {
                    circleArray[q][z] = 0;
                }

            }
   if(choice == 1){
       verticalLines = 4;
       horizontalLines = 5;

   } else if(choice == 0){
        verticalLines = 6;
       horizontalLines = 6;
   }

}


    public void readChords(String line, String[][] chordVar){
        int innerCounter1 = 0;
            try {
                FileReader fileReader = new FileReader("chords.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while ((line = bufferedReader.readLine()) != null) {

                    if (line.contains("],")) {
                        line = bufferedReader.readLine();
                        line = line.replaceAll("\\s", "");
                        line = line.replaceAll("\"", "");
                        line = line.replaceAll(":", "");
                        line = line.replaceAll("\\[", "");
                        chord.addItem(line);
                        while (chordVarCounter < 732 && (((line = bufferedReader.readLine()).contains("},")))) {
                            line = line.replaceAll("\\s", "");

                            line = line.substring(6, line.indexOf("\","));
                            chordVar[chordVarCounter][innerCounter1] = line;
                            innerCounter1++;
                        }
                        chordVarCounter++;
                        innerCounter1 = 0;
                        //  System.out.println(chordVarCounter);
                    }
                }
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '"
                                + "chords.txt" + "'");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + "chords.txt" + "'");

            }
    }



    public void readChordsUke(String lineUke, int innerCounter, String[][] chordVarUke){
         try {
                FileReader fileReaderUke = new FileReader("chordsUke.txt");
                BufferedReader bufferedReaderUke = new BufferedReader(fileReaderUke);

                while ((lineUke = bufferedReaderUke.readLine()) != null) {

                    if (lineUke.contains("],")) {
                        lineUke = bufferedReaderUke.readLine();
                        lineUke = lineUke.replaceAll("\\s", "");
                        lineUke = lineUke.replaceAll("\"", "");
                        lineUke = lineUke.replaceAll(":", "");
                        lineUke = lineUke.replaceAll("\\[", "");
                        chordUke.addItem(lineUke);
                        while (chordVarCounterUke < 545 && (((lineUke = bufferedReaderUke.readLine()).contains("},")))) {
                            lineUke = lineUke.replaceAll("\\s", "");

                            lineUke = lineUke.substring(6, lineUke.indexOf("\","));
                            chordVarUke[chordVarCounterUke][innerCounter] = lineUke;
                            innerCounter++;
                        }
                        chordVarCounterUke++;
                        innerCounter = 0;
                          System.out.println(chordVarCounterUke);
                    }
                }

                bufferedReaderUke.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file '"
                                + "chords.txt" + "'");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file '"
                                + "chords.txt" + "'");

            }
    }



    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getY() < 100) {

            if (ChordPanel.color4.getRed() == 255) {
                System.out.println("click");
                ChordPanel.color4 = new java.awt.Color(0, 0, 0);

            } else {
                ChordPanel.color4 = new java.awt.Color(255, 255, 255);
            }
        }

        for (int a = 0; a < verticalLines; a++) {
            if (e.getX() > a * (250 / (verticalLines - 1)) + 6 && e.getX() <= a * (250 / (verticalLines - 1)) + 50) {
                System.out.println(e.getX());
                for (int b = 1; b < horizontalLines + 1; b++) {
                    if (e.getY() > b * (550 / (horizontalLines)) + 10 && e.getY() <= b * (550 / (horizontalLines)) + 80) {
                        System.out.println(a + " " + b);

                        if (circleArray[b][a] == 1) {
                            circleArray[b][a] = 0;
                        } else {
                            circleArray[b][a] = 1;
                        }
                    }
                }
            }

        }

    }

    @Override

    public void mousePressed(MouseEvent e) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class ChordPanel extends JPanel implements Runnable {

    Thread th = new Thread(this);
    static java.awt.Color color1 = new java.awt.Color(255, 189, 82);
    static java.awt.Color color2 = new java.awt.Color(161, 102, 255);
    static java.awt.Color color3 = new java.awt.Color(000, 000, 000);
    static java.awt.Color color4 = new java.awt.Color(000, 000, 000);

    static String chordNamePanel = "";
    static String chordOffsetPanel = "";

    Font customFont;
    final static BasicStroke stroke = new BasicStroke(0.5f);
    static boolean[] circleBool = new boolean[100];
    final Font f = new Font("SansSerif", Font.BOLD, 18);

    public static void main(String[] args) {
        new ChordPanel();
    }

    public ChordPanel() {

        //adjust size and set layout
        setPreferredSize(new Dimension(300, 650));
        GridLayout layout = new GridLayout(1, 1, 0, 0);

        setLayout(layout);
        th.start();

    }

    public void drawCircles(int vertical, int horizontal, Graphics2D g2) {

        for (int a = 1; a < vertical + 1; a++) {
            for (int b = 0; b < horizontal + 1; b++) {
                if (Refactoring.circleArray[a][b] == 1) {
                    g2.fillOval(13 + b * 251 / (horizontal - 1), (a - 1) * 555 / vertical + ((555 / (vertical)) / 2 + 72 - 13), 26, 26);

                }
            }
        }

        // 4/13 = 6/
        for (int a = 1; a < vertical + 1; a++) {
            for (int b = 0; b < horizontal + 1; b++) {
                if (Refactoring.circleArray[a][b] == 1) {

                    g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                    g2.setColor(java.awt.Color.BLACK);
                    g2.drawOval(13 + b * 251 / (horizontal - 1), (a - 1) * 555 / vertical + ((555 / (vertical)) / 2 + 72 - 13), 26, 26);
                    // System.out.println((a - 1) * 555 / vertical);
                }
            }
        }

    }

    public void drawGrid(int w, int h, Graphics2D g2) {
        g2.setColor(java.awt.Color.BLACK);

        for (int i = 0; i < w + 1; i++) {
            if (i != (w)) {
                g2.drawLine(25, (555 / w) * i + 72, 275, (555 / w) * i + 72);
            } else {
                g2.drawLine(25, 626, 275, 626);
            }

            //650
        }
        for (int i = 0; i < h; i++) {
            if (i != (h - 1)) {
                g2.drawLine((251 / (h - 1) * i + 25), 72, (251 / (h - 1)) * i + 25, 625);
            } else {
                g2.drawLine(275, 72, 275, 625);
            }

        }

        //550
    }

    public void drawCenteredString(String s, int w, int h, Graphics2D g2) {
        // g2.setColor(color3);
        FontMetrics fm = g2.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);

        int x1 = (w - fm.stringWidth(s)) / 2 - 1;
        int y1 = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2) - 1;
        drawCenteredStringOutline(s, x1, y1, g2);

        int x2 = (w - fm.stringWidth(s)) / 2 - 1;
        int y2 = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        drawCenteredStringOutline(s, x2, y2, g2);

        int x3 = (w - fm.stringWidth(s)) / 2;
        int y3 = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2) - 1;
        drawCenteredStringOutline(s, x3, y3, g2);

        int x4 = (w - fm.stringWidth(s)) / 2 + 1;
        int y4 = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2) + 1;
        drawCenteredStringOutline(s, x4, y4, g2);

        int x5 = (w - fm.stringWidth(s)) / 2 + 1;
        int y5 = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        drawCenteredStringOutline(s, x5, y5, g2);

        int x6 = (w - fm.stringWidth(s)) / 2;
        int y6 = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2) + 1;
        drawCenteredStringOutline(s, x6, y6, g2);

        int x7 = (w - fm.stringWidth(s)) / 2 - 1;
        int y7 = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2) + 1;
        drawCenteredStringOutline(s, x7, y7, g2);

        try {
            g2.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf")).deriveFont(30f));
            g2.drawString(chordOffsetPanel, 3 + 1, 94 + 1);
            g2.drawString(chordOffsetPanel, 3, 94 + 1);
            g2.drawString(chordOffsetPanel, 3 - 1, 94 - 1);
            g2.drawString(chordOffsetPanel, 3, 94 - 1);
            g2.drawString(chordOffsetPanel, 3 + 1, 94);
            g2.drawString(chordOffsetPanel, 3 - 1, 94);
            g2.drawString(chordOffsetPanel, 3, 94);
        } catch (IOException | FontFormatException e) {
        }

        try {
            g2.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf")).deriveFont(60f));

        } catch (IOException | FontFormatException e) {
        }

        g2.setColor(color3);
        g2.drawString(s, x, y);

        g2.setColor(java.awt.Color.BLACK);

    }

    public void drawCenteredStringOutline(String s, int w, int h, Graphics2D g2) {

        g2.setColor(color4);

        g2.drawString(s, w, h);

    }

    @Override
    public void paint(Graphics g) {

        String chordNameString = Refactoring.chordName.getText();
        String chordOffsetString = Refactoring.chordOffset.getText();
        ChordPanel.chordNamePanel = chordNameString;
        ChordPanel.chordOffsetPanel = chordOffsetString;
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D rect = new Rectangle2D.Float();
        rect.setRect(0f, 0f, 300f, 650f);
        GradientPaint gp;

        gp = new GradientPaint(100f, 0f, color1, 100f, 650f, color2);
        g2.setPaint(gp);
        g2.fill(rect);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        g2.setColor(java.awt.Color.BLACK);

        Dimension d = this.getSize();

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf")).deriveFont(61f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf")));
        } catch (IOException | FontFormatException e) {
        }
        g2.setFont(customFont);

        // drawCenteredStringOutline(chordNamePanel, d.width, 75, g2);
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf")).deriveFont(60f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf")));
        } catch (IOException | FontFormatException e) {
        }
        g2.setFont(customFont);
        drawCenteredString(chordNamePanel, d.width, 75, g2);
        g2.setColor(color3);

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("micra.ttf")));
        } catch (IOException | FontFormatException e) {
        }
        g2.setFont(customFont);

        g2.drawString(chordOffsetPanel, 3, 94);

        g2.setColor(java.awt.Color.BLACK);
        drawGrid(Refactoring.horizontalLines, Refactoring.verticalLines, g2);

        gp = new GradientPaint(100f, 0f, color2, 100f, 650f, color1);

        g2.setPaint(gp);

        drawCircles(Refactoring.horizontalLines, Refactoring.verticalLines, g2);
        //////////////

        /////////////////////////////
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setColor(java.awt.Color.BLACK);

    }

    @Override
    public void run() {

        while (true) {
            repaint();

            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
            }
            //  Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        }
    }

    static public BufferedImage createImage(JPanel panel) {

        int w = panel.getWidth();
        int h = 650;
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        return bi;
    }

    static public void save() {
        try {
            // retrieve image
            String fileName = Refactoring.chordName.getText();
            fileName += ".png";
            BufferedImage bi = createImage(Refactoring.contentPanel);
            File outputfile = new File(fileName);
            ImageIO.write(bi, "png", outputfile);
            System.out.println("Worked.");
            JOptionPane.showMessageDialog(Refactoring.chordFrame, Refactoring.errorobject, "File saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            ///
            System.out.println("Didnt work");
        }
    }
}

class ColorChooserExample extends JFrame {

    static JButton b;
    static Container c;

    ColorChooserExample() {
        c = getContentPane();
        c.setLayout(new FlowLayout());
        b = new JButton("color");
        // b.addActionListener(this);
        c.add(b);
    }

    public static java.awt.Color action() {
        java.awt.Color initialcolor = java.awt.Color.RED;
        java.awt.Color colorChosen = JColorChooser.showDialog(c, "Select a color", initialcolor);
        return colorChosen;
//c.setBackground(color);
    }

    public static void main(String[] args) {
        ColorChooserExample ch = new ColorChooserExample();
        ch.setSize(400, 400);
        ch.setVisible(true);
        ch.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
