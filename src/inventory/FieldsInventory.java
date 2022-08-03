package inventory;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FieldsInventory
{

    public static final int ROWS = 6, COLS = 7;

    private static final Color DEFAULT_BACKGROUND = Color.black;
    private static final Color COLOR_PLAYER_0 = Color.blue;
    private static final Color COLOR_PLAYER_1 = Color.red;

    private final JLabel[][] fields;

    private int currentPlayer; //0 or 1

    public FieldsInventory()
    {
        fields = new JLabel[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                fields[row][col] = new JLabel();
                fields[row][col].setBackground(DEFAULT_BACKGROUND);
                fields[row][col].setOpaque(true); // to make background visible
            }
        }

        currentPlayer = 0;
    }

    public JPanel addFieldsToMainPanel(JPanel canvas)
    {
        for (int row = 0; row < ROWS; row++) {
            for (JLabel field : fields[row]) {
                canvas.add(field);
            }
        }
        return canvas;
    }
    
    public void renewFields(){
        for (int row = 0; row < ROWS; row++) {
            for (JLabel field : fields[row]) {
                field.setBackground(DEFAULT_BACKGROUND);
            }
        }
    }

    public String play(int col)
    {
        //check if col below is free
        for (int row = ROWS - 1; row >= 0; row--) {
            if (fields[row][col].getBackground().equals(DEFAULT_BACKGROUND)) {

                //if yes -> take lowest and put right color
                fields[row][col].setBackground(
                        (currentPlayer == 0) ? COLOR_PLAYER_0 : COLOR_PLAYER_1);

                //check if user won
                if (player_won()) {
                    return "Player " + currentPlayer + " won";
                }

                //switch player
                switchPlayer();

                break;
            }
            //else -> ignore actionEvent and wait for next try
        }

        return "";
    }

    private void switchPlayer()
    {
        currentPlayer = (currentPlayer == 0) ? 1 : 0;
    }

    private boolean player_won()
    {
        boolean playerWon = false;
        Color colorOfPlayer = (currentPlayer == 0) ? COLOR_PLAYER_0 : COLOR_PLAYER_1;

        int counter = 0; // if 4 -> won

        //horizontal
        for (int row = 0; row < ROWS; row++) {
            counter = 0;
            for (JLabel field : fields[row]) {
                if (field.getBackground().equals(colorOfPlayer)) {
                    counter++;
                    if (counter == 4) {
                        playerWon = true;
                        break;
                    }
                } else {
                    counter = 0;
                }
            }
        }

        counter = 0;
        //vertical
        if (!playerWon) { //if it's still false
            for (int col = 0; col < COLS; col++) {
                counter = 0;
                for (int row = 0; row < ROWS; row++) {
                    if (fields[row][col].getBackground().equals(colorOfPlayer)) {
                        counter++;
                        if (counter == 4) {
                            playerWon = true;
                            break;
                        }
                    } else {
                        counter = 0;
                    }
                }
            }
        }

        //diagonal
        if (!playerWon) { //if it's still false
            //from upper left to lower right corner
            for (int row = 0; row < ROWS - 3; row++) {
                for (int col = 0; col < COLS - 3; col++) {
                    counter = 0;
                    for (int i = 0; i < 4; i++) {
                        if (fields[row + i][col + i].getBackground().equals(colorOfPlayer)) {
                            counter++;
                            if (counter == 4) {
                                playerWon = true;
                                break;
                            }
                        } else {
                            counter = 0;
                        }
                    }

                }
            }

            //from upper right to lower left corner
            for (int row = 0; row < ROWS - 3; row++) {
                for (int col = COLS-1; col > 3; col--) {
                    counter = 0;
                    for (int i = 0; i < 4; i++) {
                        if (fields[row + i][col - i].getBackground().equals(colorOfPlayer)) {
                            counter++;
                            if (counter == 4) {
                                playerWon = true;
                                break;
                            }
                        } else {
                            counter = 0;
                        }
                    }
                }
            }
        }
        return playerWon;
    }
}
