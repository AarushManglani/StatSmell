import javax.swing.*;
import java.awt.*;

public class QuizForm {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Quiz");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel questionLabel = new JLabel("What is the capital of France?");

        JRadioButton option1 = new JRadioButton("Berlin");
        JRadioButton option2 = new JRadioButton("Madrid");
        JRadioButton option3 = new JRadioButton("Paris");
        JRadioButton option4 = new JRadioButton("Rome");

        ButtonGroup group = new ButtonGroup();
        group.add(option1);
        group.add(option2);
        group.add(option3);
        group.add(option4);

        JButton submitButton = new JButton("Submit");

        frame.add(questionLabel);
        frame.add(option1);
        frame.add(option2);
        frame.add(option3);
        frame.add(option4);
        frame.add(submitButton);

        frame.setVisible(true);
    }
}
