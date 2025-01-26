import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculatrice {

    private static double result = 0;
    private static String operator = "";
    private static boolean isNewInput = true;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculatrice");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField("0");
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 4, 5, 5));
        String[] buttons = {
                "%", "√", "x^2", "1/x",
                "CE", "C", "<--", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = button.getText();

                    try {
                        switch (command) {
                            case "CE" -> textField.setText("0");
                            case "C" -> {
                                textField.setText("0");
                                result = 0;
                                operator = "";
                                isNewInput = true;
                            }
                            case "<--" -> {
                                String current = textField.getText();
                                textField.setText(current.length() > 1 ? current.substring(0, current.length() - 1) : "0");
                            }
                            case "%" -> {
                                double value = Double.parseDouble(textField.getText());
                                textField.setText(String.valueOf(value / 100));
                            }
                            case "√" -> {
                                double value = Double.parseDouble(textField.getText());
                                if (value >= 0) {
                                    textField.setText(String.valueOf(Math.sqrt(value)));
                                } else {
                                    textField.setText("Erreur");
                                }
                            }
                            case "x^2" -> {
                                double value = Double.parseDouble(textField.getText());
                                textField.setText(String.valueOf(value * value));
                            }
                            case "1/x" -> {
                                double value = Double.parseDouble(textField.getText());
                                if (value != 0) {
                                    textField.setText(String.valueOf(1 / value));
                                } else {
                                    textField.setText("Erreur");
                                }
                            }
                            case "+/-" -> {
                                double value = Double.parseDouble(textField.getText());
                                textField.setText(String.valueOf(-value));
                            }
                            case "=" -> {
                                double secondOperand = Double.parseDouble(textField.getText());
                                switch (operator) {
                                    case "+" -> result += secondOperand;
                                    case "-" -> result -= secondOperand;
                                    case "*" -> result *= secondOperand;
                                    case "/" -> {
                                        if (secondOperand != 0) {
                                            result /= secondOperand;
                                        } else {
                                            textField.setText("Erreur");
                                            return;
                                        }
                                    }
                                }
                                textField.setText(String.valueOf(result));
                                operator = "";
                                isNewInput = true;
                            }
                            case "+", "-", "*", "/" -> {
                                result = Double.parseDouble(textField.getText());
                                operator = command;
                                isNewInput = true;
                            }
                            default -> {
                                if (isNewInput) {
                                    textField.setText(command.equals(".") ? "0." : command);
                                    isNewInput = false;
                                } else {
                                    textField.setText(textField.getText() + command);
                                }
                            }
                        }
                    } catch (NumberFormatException ex) {
                        textField.setText("Erreur");
                    }
                }
            });

            buttonPanel.add(button);
        }

        frame.setLayout(new BorderLayout());
        frame.add(textField, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
