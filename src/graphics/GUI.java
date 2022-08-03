package graphics;

import inventory.ActionButtonsInventory;
import inventory.FieldsInventory;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI extends JFrame implements Runnable
{

    private boolean gameRunning;

    public boolean isGameRunning()
    {
        return gameRunning;
    }
    
    
    private final MenuBar menuBar;
    private final Menu gameMenu;
    private final MenuItem miNewGame, miExit; 
    
    private JPanel panelMainGame, panelButtons;
    private final ActionListener buttonListener;

    private final FieldsInventory fieldsInv;
    private final ActionButtonsInventory buttonInv;
    
    private String finalMessage;
    
    public String getFinalMessage(){
        return finalMessage;
    }
    
    public GUI()
    {
        gameRunning = true;
        panelMainGame = new JPanel();
        panelButtons = new JPanel();
        fieldsInv = new FieldsInventory();
        buttonInv = new ActionButtonsInventory();
        finalMessage = "";
        buttonListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(finalMessage.equals("")){
                    finalMessage = fieldsInv.play(
                            buttonInv.getRightColOfButton(e.getSource()));
                }
            }
        };
        
        menuBar = new MenuBar();
        gameMenu = new Menu();
        miNewGame = new MenuItem();
        miExit = new MenuItem();
    }

    public void init()
    {
        //mainFrame start
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLayout(new BorderLayout());
        //mainFrame end

        //buttonPanel start
        panelButtons.setLayout(new GridLayout(1, FieldsInventory.COLS, 1, 1));
        panelButtons = buttonInv.addButtonsToPanel(panelButtons, buttonListener);
        //buttonPanel end
        
        //mainGame start
        panelMainGame.setLayout(new GridLayout(FieldsInventory.ROWS, FieldsInventory.COLS, 1, 1));
        panelMainGame = fieldsInv.addFieldsToMainPanel(panelMainGame);
        //mainGame end

        this.add(panelMainGame, BorderLayout.CENTER);
        this.add(panelButtons, BorderLayout.NORTH);
        
        //menu start
        gameMenu.setLabel("Game");
        
        miNewGame.setLabel("New Game");
        miNewGame.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                panelMainGame.removeAll();
                System.gc();
                fieldsInv.renewFields();
                panelMainGame = fieldsInv.addFieldsToMainPanel(panelMainGame);
                finalMessage = "";
                gameRunning = true;
                GUI.this.repaint();
            }
        });
        
        miExit.setLabel("Exit");
        miExit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        gameMenu.add(miNewGame);
        gameMenu.add(miExit);
        
        menuBar.add(gameMenu);
        //menu end
        
        this.setMenuBar(menuBar);
    }
    
    public void endOfGame(){
        gameRunning = false;
        JOptionPane.showMessageDialog(this, getFinalMessage(),
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void run()
    {
        setVisible(true);
    }

    
}
