import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// TODO: swiss triangle and exact cuts if not 400 players or 8 rounds

public class GUI {
    private JPanel mainPanel;
    private JButton buttonMonte;
    private JTextField textFieldPlayers;
    private JTextField textFieldWinrate;
    private JTextField textFieldRounds;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField8;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField9;
    private JPanel chancesPanel;
    private JTextPane winrateNeedsToBeTextPane;
    private JButton buttonCompute;
    private JTextField textFieldTop16;

    public static long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
    }

    public GUI(){

        buttonCompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double winrate = Double.parseDouble(textFieldWinrate.getText())/100;
                int players = Integer.parseInt(textFieldPlayers.getText());
                int rounds = Integer.parseInt(textFieldRounds.getText());
                double winrateTop16 = Double.parseDouble(textFieldTop16.getText())/100;

                double wins8 = factorial(rounds)/factorial(8)/factorial(rounds-8)*Math.pow(winrate,8)*Math.pow(1-winrate, rounds-8);
                double wins7 = factorial(rounds)/factorial(7)/factorial(rounds-7)*Math.pow(winrate,7)*Math.pow(1-winrate, rounds-7);
                double wins6 = factorial(rounds)/factorial(6)/factorial(rounds-6)*Math.pow(winrate,6)*Math.pow(1-winrate, rounds-6);
                double wins5 = factorial(rounds)/factorial(5)/factorial(rounds-5)*Math.pow(winrate,5)*Math.pow(1-winrate, rounds-5);
                double wins4 = factorial(rounds)/factorial(4)/factorial(rounds-4)*Math.pow(winrate,4)*Math.pow(1-winrate, rounds-4);
                double wins3 = factorial(rounds)/factorial(3)/factorial(rounds-3)*Math.pow(winrate,3)*Math.pow(1-winrate, rounds-3);
                double winsBad = 1-wins8-wins7-wins6-wins5-wins4-wins3;

                textField1.setText(String.format("%.2f", (wins8+wins7+wins6*1.95/43.8)*100)+"%");
                textField2.setText(String.format("%.2f", wins8*100)+"%");
                textField3.setText(String.format("%.2f", wins7*100)+"%");
                textField4.setText(String.format("%.2f", wins6*100)+"%");
                textField5.setText(String.format("%.2f", wins5*100)+"%");
                textField6.setText(String.format("%.2f", wins4*100)+"%");
                textField7.setText(String.format("%.2f", wins3*100)+"%");
                textField8.setText(String.format("%.2f", winsBad*100)+"%");

                double top16value = 14000.0*winrateTop16*winrateTop16*winrateTop16*winrateTop16+10000.0*winrateTop16*winrateTop16*winrateTop16*(1.0-winrateTop16)+7000.0*winrateTop16*winrateTop16*(1.0-winrateTop16)+4000.0*winrateTop16*(1.0-winrateTop16)+2000.0*(1.0-winrateTop16);
                double value6w = 60000/(41.8);  // 41.8 miss
                double value5w = 60000/(86); //1000.0*6.1/87.5 + 750.0*64/87.5 + 500*17.4/87.5;
                double value4w = 60000/(108.945);  //
                double value3w = 0;
                double expectedValue = top16value*(wins8+wins7+wins6/43) + value6w*wins6 + value5w*wins5 + value4w*wins4 + value3w*wins3;

                textField9.setText(String.format("%.2f", expectedValue));
            }
        });

        buttonMonte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Random random = new Random();
                double winrate = Double.parseDouble(textFieldWinrate.getText())/100;
                int players = Integer.parseInt(textFieldPlayers.getText());
                int rounds = Integer.parseInt(textFieldRounds.getText());
                double winrateTop16 = Double.parseDouble(textFieldTop16.getText())/100;

                int testSet = 10000000;
                double wins8=0, wins7=0, wins6=0, wins5=0, wins4=0, wins3=0, winsBad=0;
                for (int k = 0; k < testSet; k++) {
                    int score=0;
                    for (int i = 0; i < rounds; i++) {
                        int roll = random.nextInt(100); // random 0-100
                        //System.out.println(roll);
                        if(roll<(winrate*100)){
                            score++;
                        }
                    }
                    switch (score){
                        case 8:
                            wins8++;
                            break;
                        case 7:
                            wins7++;
                            break;
                        case 6:
                            wins6++;
                            break;
                        case 5:
                            wins5++;
                            break;
                        case 4:
                            wins4++;
                            break;
                        case 3:
                            wins3++;
                            break;
                        default:
                            winsBad++;
                            break;
                    }
                }
                textField1.setText(String.format("%.2f", (wins8+wins7+wins6*1.95/43.8)/testSet*100)+"%");
                textField2.setText(String.format("%.2f", wins8/testSet*100)+"%");
                textField3.setText(String.format("%.2f", wins7/testSet*100)+"%");
                textField4.setText(String.format("%.2f", wins6/testSet*100)+"%");
                textField5.setText(String.format("%.2f", wins5/testSet*100)+"%");
                textField6.setText(String.format("%.2f", wins4/testSet*100)+"%");
                textField7.setText(String.format("%.2f", wins3/testSet*100)+"%");
                textField8.setText(String.format("%.2f", winsBad/testSet*100)+"%");

                double top16valueBasic = 6875;  // 25000/16+17000/16+10000/8+6000/4+3000/2;
                double top16value = 14000.0*winrateTop16*winrateTop16*winrateTop16*winrateTop16+10000.0*winrateTop16*winrateTop16*winrateTop16*(1.0-winrateTop16)+7000.0*winrateTop16*winrateTop16*(1.0-winrateTop16)+4000.0*winrateTop16*(1.0-winrateTop16)+2000.0*(1.0-winrateTop16);
                double value6w = 60000/(41.8);  // 41.8 miss
                double value5w = 60000/(86); //1000.0*6.1/87.5 + 750.0*64/87.5 + 500*17.4/87.5;
                double value4w = 60000/(108.945);  //
                double value3w = 0;
                //https://sixprizes.com/top-cut-calculator/?players=400&rounds=8&cut=200#results
                /*
                8-0 ??? 1.6
                7-1 ??? 12.5
                6-2 ??? 43.8
                5-3 ??? 87.5
                4-4 ??? 109.4
                 */

                double expectedValue = top16value*(wins8+wins7+wins6/43)/testSet + value6w*wins6/testSet + value5w*wins5/testSet + value4w*wins4/testSet + value3w*wins3/testSet;

                //OK//System.out.println((wins8+wins7)/testSet*100+wins6/testSet*100+wins5/testSet*100+wins4/testSet*100+wins3/testSet*100+winsBad/testSet*100);
                //OK//System.out.println(top16valueBasic);
                //OK//System.out.println(top16value);
                //OK//System.out.println(winratePercent);

                textField9.setText(String.format("%.2f", expectedValue));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("[2022] Hearthstone Masters Tour EV calculator");
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.pack();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }
}
