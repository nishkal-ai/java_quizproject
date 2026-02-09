import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Question {
    String question, option1, option2, option3, option4, answer;

    Question(String q, String o1, String o2, String o3, String o4, String ans) {
        question = q;
        option1 = o1;
        option2 = o2;
        option3 = o3;
        option4 = o4;
        answer = ans;
    }
}

public class AdvancedQuizGUI extends JFrame implements ActionListener {

    JLabel questionLabel, timerLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup group;
    JButton nextBtn;

    ArrayList<Question> questions = new ArrayList<>();
    int index = 0, score = 0, time = 15;
    Timer timer;

    public AdvancedQuizGUI() {

        setTitle("MCQ Exam Simulator");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        timerLabel = new JLabel("Time: 15 sec");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);
        topPanel.add(timerLabel, BorderLayout.EAST);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topPanel.add(questionLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Options
        opt1 = new JRadioButton();
        opt2 = new JRadioButton();
        opt3 = new JRadioButton();
        opt4 = new JRadioButton();

        group = new ButtonGroup();
        group.add(opt1);
        group.add(opt2);
        group.add(opt3);
        group.add(opt4);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        optionsPanel.add(opt1);
        optionsPanel.add(opt2);
        optionsPanel.add(opt3);
        optionsPanel.add(opt4);

        add(optionsPanel, BorderLayout.CENTER);

        // Button
        nextBtn = new JButton("Next");
        nextBtn.addActionListener(this);
        add(nextBtn, BorderLayout.SOUTH);

        loadQuestions();
        loadQuestion();
        startTimer();

        setVisible(true);
    }

    void loadQuestions() {
        questions.add(new Question(
                "Which language is used for Android?",
                "Python", "Java", "C++", "Swift", "Java"));

        questions.add(new Question(
                "Which keyword creates an object?",
                "class", "object", "new", "create", "new"));

        questions.add(new Question(
                "Main method signature?",
                "public static void main(String[] args)",
                "void main()",
                "static main()",
                "main(String args)", 
                "public static void main(String[] args)"));

        questions.add(new Question(
                "Which is not OOP concept?",
                "Encapsulation", "Inheritance", "Compilation", "Polymorphism", "Compilation"));
    }

    void loadQuestion() {
        group.clearSelection();
        time = 15;

        Question q = questions.get(index);
        questionLabel.setText("Q" + (index + 1) + ". " + q.question);

        opt1.setText(q.option1);
        opt2.setText(q.option2);
        opt3.setText(q.option3);
        opt4.setText(q.option4);
    }

    void startTimer() {
        timer = new Timer(1000, e -> {
            timerLabel.setText("Time: " + time + " sec");
            time--;

            if (time < 0) {
                timer.stop();
                checkAnswer();
                moveNext();
            }
        });
        timer.start();
    }

    void checkAnswer() {
        String selected = "";
        if (opt1.isSelected()) selected = opt1.getText();
        if (opt2.isSelected()) selected = opt2.getText();
        if (opt3.isSelected()) selected = opt3.getText();
        if (opt4.isSelected()) selected = opt4.getText();

        if (selected.equals(questions.get(index).answer)) {
            score++;
        }
    }

    void moveNext() {
        index++;
        if (index < questions.size()) {
            loadQuestion();
            timer.restart();
        } else {
            showResult();
        }
    }

    void showResult() {
        double percentage = (score * 100.0) / questions.size();
        String grade;

        if (percentage >= 80) grade = "Excellent";
        else if (percentage >= 60) grade = "Good";
        else grade = "Needs Improvement";

        JOptionPane.showMessageDialog(this,
                "Exam Finished!\n\n" +
                        "Score: " + score + "/" + questions.size() +
                        "\nPercentage: " + percentage + "%" +
                        "\nGrade: " + grade);

        System.exit(0);
    }

    public void actionPerformed(ActionEvent e) {
        timer.stop();
        checkAnswer();
        moveNext();
    }

    public static void main(String[] args) {
        new AdvancedQuizGUI();
    }
}