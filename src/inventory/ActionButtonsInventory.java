package inventory;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionButtonsInventory
{
    private final JButton[] buttons;
    
    
    public ActionButtonsInventory(){
        buttons = new JButton[FieldsInventory.COLS];
        
        for(int col = 0; col < FieldsInventory.COLS; col++){
            buttons[col] = new JButton();
            buttons[col].setText("V");
        }
    }
    
    
    public int getRightColOfButton(Object eventSrc){
        for(int col = 0; col < FieldsInventory.COLS; col++){
            if(buttons[col].equals(eventSrc)){
                return col;
            }
        }
        return -1;
    }
    
    public JPanel addButtonsToPanel(JPanel panel, ActionListener action){
        for(JButton button : buttons){
            button.addActionListener(action);
            panel.add(button);
        }
        
        return panel;
    }
    
}
