import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.List;

/**
 * Luis Juarez
 * 04/21/2016
 * Hangman game
 * Program description: Hangman game - Player will try to guess a phrase by selecting letters in the alphabet, if user
 * has 10 wrong guesses the game is over. If the player reveals the full phrase the game is over.
 */
public class HangmanGame {

    public static void main(String[] args) {

        JFrame frame = new HangmanFrame();
        frame.setTitle("Hangman Game");
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(530,570));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
    }
}

class JHangmanPanel extends JPanel
{
    int wrongGuesses;

    public void setGuessesRemaining(int i)
    {
        wrongGuesses = i;
    }

    public void addPart() {
        wrongGuesses++;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int x = (this.getWidth()) / 2;
        int y = (this.getHeight()) / 2;

        g2.setStroke(new BasicStroke(5,BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g2.setColor(Color.darkGray);

        switch (wrongGuesses) {
            case 10:
                g.drawOval(x+30,y+45,8,8); // right foot
            case 9:
                g.drawOval(x-35,y+45,8,8); // left foot
            case 8:
                g.drawOval(x-35,y+20,6,6); // left hand
            case 7:
                g.drawOval(x+30,y+20,6,6); // right hand
            case 6:
                g.drawLine(x,y-15,x-30,y+20); // left arm
            case 5:
                g.drawLine(x,y-15,x+30,y+20); // right arm
            case 4:
                g.drawLine(x-30,y+45, x,y+20); // left leg
            case 3:
                g.drawLine(x+30,y+45, x, y+20); // right leg
            case 2:
                g.drawLine(x,y-15,x,y+20); //body
            case 1:
                g.drawOval(x-15,y-45, 30, 30); // head
            case 0:
                //Noose
                g.drawLine(x-100,y-50,x,y-50); // noose top
                g.drawLine(x-100,y-50,x-100,y+40); // noose pole
                g.drawLine(x-150,y+40,x-50,y+40); //noose stand
        }
    }
}

class HangmanFrame extends JFrame implements ActionListener
{
    Font fancyFont = new Font("Times", Font.BOLD, 40);
    //Declaring objects
    int counter = 0; //counts wrong guesses
    static Path filepath;
    String phrase;
    String answer = SelectPhrase(); //Generates the word for game
    StringBuilder hiddenPhrase;  //stringbuilder is used to hide the password and changes when a word is guessed
    Container con = getContentPane();
    BorderLayout layout = new BorderLayout();
    JMenuBar mainMenuBar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenu menu4 = new JMenu("Help");
    JMenuItem restart = new JMenuItem("Restart");
    JMenuItem exit = new JMenuItem("Exit");
    JMenuItem about = new JMenuItem("About");
    JLabel phraseLabel = new JLabel(hiddenPhrase.toString()) ;
    JLabel incorrect = new JLabel("Incorrect: " + counter); // Displays number of incorrect guesses
    JHangmanPanel p3 = new JHangmanPanel();

    JButton aButton = new JButton("A");
    JButton bButton = new JButton("B");
    JButton cButton = new JButton("C");
    JButton dButton = new JButton("D");
    JButton eButton = new JButton("E");
    JButton fButton = new JButton("F");
    JButton gButton = new JButton("G");
    JButton hButton = new JButton("H");
    JButton iButton = new JButton("I");
    JButton jButton = new JButton("J");
    JButton kButton = new JButton("K");
    JButton lButton = new JButton("L");
    JButton mButton = new JButton("M");
    JButton nButton = new JButton("N");
    JButton oButton = new JButton("O");
    JButton pButton = new JButton("P");
    JButton qButton = new JButton("Q");
    JButton rButton = new JButton("R");
    JButton sButton = new JButton("S");
    JButton tButton = new JButton("T");
    JButton uButton = new JButton("U");
    JButton vButton = new JButton("V");
    JButton wButton = new JButton("W");
    JButton xButton = new JButton("X");
    JButton yButton = new JButton("Y");
    JButton zButton = new JButton("Z");

    HangmanFrame()
    {
        con.setLayout(layout);
        //setup menu bars to frame
        setJMenuBar(mainMenuBar);
        mainMenuBar.add(menu1);
        mainMenuBar.add(menu4);
        menu1.add(restart);
        menu1.add(exit);
        menu4.add(about);
        menu1.setMnemonic('F');
        exit.addActionListener(this);
        about.addActionListener(this);
        restart.addActionListener(this);

        aButton.addActionListener(this);
        bButton.addActionListener(this);
        cButton.addActionListener(this);
        dButton.addActionListener(this);
        eButton.addActionListener(this);
        fButton.addActionListener(this);
        gButton.addActionListener(this);
        hButton.addActionListener(this);
        iButton.addActionListener(this);
        jButton.addActionListener(this);
        kButton.addActionListener(this);
        lButton.addActionListener(this);
        mButton.addActionListener(this);
        nButton.addActionListener(this);
        oButton.addActionListener(this);
        pButton.addActionListener(this);
        qButton.addActionListener(this);
        rButton.addActionListener(this);
        sButton.addActionListener(this);
        tButton.addActionListener(this);
        uButton.addActionListener(this);
        vButton.addActionListener(this);
        wButton.addActionListener(this);
        xButton.addActionListener(this);
        yButton.addActionListener(this);
        zButton.addActionListener(this);

        //create panels for hangman and for letter buttons
        FlowLayout flow = new FlowLayout(FlowLayout.CENTER, 2, 2);
        JPanel p1 = new JPanel(flow);
        JPanel p2 = new JPanel(flow);

        //p3.setLayout(flow);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        //add items to panel
        p1.add(phraseLabel);
        phraseLabel.setFont(fancyFont);
        p2.add(incorrect);
        incorrect.setFont(fancyFont);
        p3.setBackground(Color.white);

        buttonPanel.add(aButton);
        buttonPanel.add(bButton);
        buttonPanel.add(cButton);
        buttonPanel.add(dButton);
        buttonPanel.add(eButton);
        buttonPanel.add(fButton);
        buttonPanel.add(gButton);
        buttonPanel.add(hButton);
        buttonPanel.add(iButton);
        buttonPanel.add(jButton);
        buttonPanel.add(kButton);
        buttonPanel.add(lButton);
        buttonPanel.add(mButton);
        buttonPanel.add(nButton);
        buttonPanel.add(oButton);
        buttonPanel.add(pButton);
        buttonPanel.add(qButton);
        buttonPanel.add(rButton);
        buttonPanel.add(sButton);
        buttonPanel.add(tButton);
        buttonPanel.add(uButton);
        buttonPanel.add(vButton);
        buttonPanel.add(wButton);
        buttonPanel.add(xButton);
        buttonPanel.add(yButton);
        buttonPanel.add(zButton);

        JPanel mainPanel = new JPanel(new GridLayout(2,3,5,5));
        setLayout(new GridLayout(4, 2, 5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        mainPanel.add(p1);
        mainPanel.add(p2);
        this.add(p3, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.CENTER);
        this.add(mainPanel);
    }

    String SelectPhrase()
    {
        String chosenWord;
        //check for file and set filepath to file if found, otherwise, throw error message
        try {filepath  = Paths.get("phrases.txt");}
        catch (Exception e)
        {
            System.out.println("Error finding file");
        }
        //array to hold phrases
        try{
            List<String> possiblePhrases = Files.readAllLines(filepath, Charset.defaultCharset());
            String[] loadedPhrases = possiblePhrases.toArray((new String[possiblePhrases.size()]));
            int randomNumber = ((int) (Math.random() * 100) % possiblePhrases.size() + 0);
            SetPhrase(loadedPhrases[randomNumber]);
            HidePhrase(loadedPhrases[randomNumber]);
            chosenWord = loadedPhrases[randomNumber];
        } catch (IOException e)
        {
            System.out.println("In/Out error");
            JOptionPane.showMessageDialog(null,"Error, phrases file is missing, game will have limited phrases");
            String[] phraseStringArray = new String[] {"game","test", "supercalifragilisticexpialidocious", "excalibur", "yes"};
            //randomly chooses from phrase
            int randomNumberBackup = ((int) (Math.random() * 100) % 5 + 0);
            SetPhrase(phraseStringArray[randomNumberBackup]);
            HidePhrase(phraseStringArray[randomNumberBackup]);
            chosenWord = phraseStringArray[randomNumberBackup];
        }
        return chosenWord;
    }

    void SetPhrase(String p)
    {
        phrase = p;
    }

    void HidePhrase(String phraseToHide)
    {
        //change phrase to asterisks
        int length = phraseToHide.length();
        StringBuilder guess = new StringBuilder(phraseToHide.length());
        for (int i = 0; i < length; i ++)
        {
            guess.append("*");
        }
        setHiddenPhrase(guess);
    }

    void addCounter()
    {
        if(counter<9) {
            counter++;
            System.out.println(counter);
            p3.addPart();
            incorrect.setText("Incorrect: " + String.valueOf(counter));
        } else {
            JOptionPane.showMessageDialog(null, "You lose! The word was " + answer);
           GameOver();
        }
    }

    void GameOver()
    {
        int prompt = JOptionPane.showConfirmDialog(null,
                "Would you like to play again?",
                "Game Over!",
                JOptionPane.YES_NO_OPTION);
        if (prompt == JOptionPane.NO_OPTION)
            System.exit(0);
        else {
           resetGame();
        }
    }

    void setHiddenPhrase(StringBuilder s)
    {
        hiddenPhrase = s;
    }

    boolean VerifyGuess(String secret, StringBuilder HiddenPhrase, char letter)
    {
        boolean inWord = false;
        for (int index = 0; index < secret.length(); index++) {
            if (secret.charAt(index) == letter) {
                HiddenPhrase.setCharAt(index, letter);
                System.out.print("This letter is in the phrase! - ");
                setHiddenPhrase(HiddenPhrase);
                inWord = true;
            }
        }
        return inWord;
    }

boolean GuessAction(String letterClicked)
{
    boolean correctGuess = false;
    if ( VerifyGuess(phrase, hiddenPhrase, letterClicked.charAt(0)))
    {
        correctGuess = true;
        phraseLabel.setText(hiddenPhrase.toString());
        if(hiddenPhrase.toString().equals(answer))
        {
            JOptionPane.showMessageDialog(null, "You win!");
            GameOver();
        }
    }
   return correctGuess;
}
    void resetGame()
    {
        counter=0;
        hiddenPhrase = null;
        incorrect.setText("Incorrect: 0");
        p3.setGuessesRemaining(0);
        aButton.setEnabled(true);
        aButton.setBackground(null);
        bButton.setEnabled(true);
        bButton.setBackground(null);
        cButton.setEnabled(true);
        cButton.setBackground(null);
        dButton.setEnabled(true);
        dButton.setBackground(null);
        eButton.setEnabled(true);
        eButton.setBackground(null);
        fButton.setEnabled(true);
        fButton.setBackground(null);
        gButton.setEnabled(true);
        gButton.setBackground(null);
        hButton.setBackground(null);
        hButton.setEnabled(true);
        iButton.setBackground(null);
        iButton.setEnabled(true);
        jButton.setBackground(null);
        jButton.setEnabled(true);
        kButton.setBackground(null);
        kButton.setEnabled(true);
        lButton.setBackground(null);
        lButton.setEnabled(true);
        mButton.setBackground(null);
        mButton.setEnabled(true);
        nButton.setBackground(null);
        nButton.setEnabled(true);
        oButton.setBackground(null);
        oButton.setEnabled(true);
        pButton.setEnabled(true);
        pButton.setBackground(null);
        qButton.setEnabled(true);
        qButton.setBackground(null);
        rButton.setEnabled(true);
        rButton.setBackground(null);
        sButton.setEnabled(true);
        sButton.setBackground(null);
        tButton.setEnabled(true);
        tButton.setBackground(null);
        uButton.setEnabled(true);
        uButton.setBackground(null);
        vButton.setEnabled(true);
        vButton.setBackground(null);
        wButton.setEnabled(true);
        wButton.setBackground(null);
        xButton.setEnabled(true);
        xButton.setBackground(null);
        yButton.setEnabled(true);
        yButton.setBackground(null);
        zButton.setEnabled(true);
        zButton.setBackground(null);
        answer = SelectPhrase();
        phraseLabel.setText(hiddenPhrase.toString());
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
        String arg = e.getActionCommand();
        if (arg.equals("Exit")) {
            System.exit(0);
        }
        if (arg.equals("Restart"))
        {
            resetGame();
        }
        if(arg.equals("About") )
        {
            JOptionPane.showMessageDialog(null, "Hangman Game by Luis Juarez");
        }
        if(arg.equals("A")){
            aButton.setEnabled(false);
            aButton.setOpaque(true);
            if(GuessAction("a")) {
                aButton.setBackground(Color.green);
            } else {
                aButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("B")){
            bButton.setEnabled(false);
            bButton.setOpaque(true);
            if(GuessAction("b")) {
                bButton.setBackground(Color.green);
            } else {
                bButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("C")){
            cButton.setEnabled(false);
            cButton.setOpaque(true);
            if(GuessAction("c")) {
                cButton.setBackground(Color.green);
            } else {
                cButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("D")){
            dButton.setEnabled(false);
            dButton.setOpaque(true);
            if(GuessAction("d")) {
                dButton.setBackground(Color.green);
            } else {
                dButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("E")){
            eButton.setEnabled(false);
            eButton.setOpaque(true);
            if(GuessAction("e")) {
                eButton.setBackground(Color.green);
            } else {
                eButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("F")){
            fButton.setEnabled(false);
            fButton.setOpaque(true);
            if(GuessAction("f")) {
                fButton.setBackground(Color.green);
            } else {
                fButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("G")){
            gButton.setEnabled(false);
            gButton.setOpaque(true);
            if(GuessAction("g")) {
                gButton.setBackground(Color.green);
            } else {
                gButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("H")){
            hButton.setEnabled(false);
            hButton.setOpaque(true);
            if(GuessAction("h")) {
                hButton.setBackground(Color.green);
            } else {
                hButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("I")){
            iButton.setEnabled(false);
            iButton.setOpaque(true);
            if(GuessAction("i")) {
                iButton.setBackground(Color.green);
            } else {
                iButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("J")){
            jButton.setEnabled(false);
            jButton.setOpaque(true);
            if(GuessAction("j")) {
                jButton.setBackground(Color.green);
            } else {
                jButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("K")){
            kButton.setEnabled(false);
            kButton.setOpaque(true);
            if(GuessAction("k")) {
                kButton.setBackground(Color.green);
            } else {
                kButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("L")){
            lButton.setEnabled(false);
            lButton.setOpaque(true);
            if(GuessAction("l")) {
                lButton.setBackground(Color.green);
            } else {
                lButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("M")){
            mButton.setEnabled(false);
            mButton.setOpaque(true);
            if(GuessAction("m")) {
                mButton.setBackground(Color.green);
            } else {
                mButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("N")){
            nButton.setEnabled(false);
            nButton.setOpaque(true);
            if(GuessAction("n")) {
                nButton.setBackground(Color.green);
            } else {
                nButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("O")){
            oButton.setEnabled(false);
            oButton.setOpaque(true);
            if(GuessAction("o")) {
                oButton.setBackground(Color.green);
            } else {
                oButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("P")){
            pButton.setEnabled(false);
            pButton.setOpaque(true);
            if(GuessAction("p")) {
                pButton.setBackground(Color.green);
            } else {
                pButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("Q")){
            qButton.setEnabled(false);
            qButton.setOpaque(true);
            if(GuessAction("q")) {
                qButton.setBackground(Color.green);
            } else {
                qButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("R")){
            rButton.setEnabled(false);
            rButton.setOpaque(true);
            if(GuessAction("r")) {
                rButton.setBackground(Color.green);
            } else {
                rButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("S")){
            sButton.setEnabled(false);
            sButton.setOpaque(true);
            if(GuessAction("s")) {
                sButton.setBackground(Color.green);
            } else {
                sButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("T")){
            tButton.setEnabled(false);
            tButton.setOpaque(true);
            if(GuessAction("t")) {
                tButton.setBackground(Color.green);
            } else {
                tButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("U")){
            uButton.setEnabled(false);
            uButton.setOpaque(true);
            if(GuessAction("u")) {
                uButton.setBackground(Color.green);
            } else {
                uButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("V")){
            vButton.setEnabled(false);
            vButton.setOpaque(true);
            if(GuessAction("v")) {
                vButton.setBackground(Color.green);
            } else {
                vButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("W")){
            wButton.setEnabled(false);
            wButton.setOpaque(true);
            if(GuessAction("w")) {
                wButton.setBackground(Color.green);
            } else {
                wButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("X")){
            xButton.setEnabled(false);
            xButton.setOpaque(true);
            if(GuessAction("x")) {
                xButton.setBackground(Color.green);
            } else {
                xButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("Y")){
            yButton.setEnabled(false);
            yButton.setOpaque(true);
            if(GuessAction("y")) {
                yButton.setBackground(Color.green);
            } else {
                yButton.setBackground(Color.red);
                addCounter();
            }
        }
        if(arg.equals("Z")){
            zButton.setEnabled(false);
            zButton.setOpaque(true);
            if(GuessAction("z")) {
                zButton.setBackground(Color.green);
            } else {
                zButton.setBackground(Color.red);
                addCounter();
            }
        }
    }
}