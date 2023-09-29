package sem1_Tasks;

import javax.swing.*;
import java.awt.*;

/*
Задача: На лекции был написан фрейм, содержащий одну кнопку – начать игру и расположением самого окна настроек автоматически,
относительно игрового окна.
Добавить на экран компоновщик-сетку с одним столбцом и добавить над существующей кнопкой следующие компоненты в заданном порядке:
JLabel с заголовком «Выберите режим игры»,
сгруппированные в ButtonGroup переключаемые JRadioButton с указанием режимов «Человек против компьютера» и «Человек против человека»,
JLabel с заголовком «Выберите размеры поля»,
JLabel с заголовком «Установленный размер поля:»,
JSlider со значениями 3..10,
JLabel с заголовком «Выберите длину для победы»,
JLabel с заголовком «Установленная длина:»,
JSlider со значениями 3..10.
 */

/*
Задача: Добавить компонентам интерактивности, _x000b_а именно,
при перемещении ползунка слайдера _x000b_в соответствующих лейблах должны появляться текущие значения слайдеров.
Для этого необходимо добавить _x000b_к слайдеру слушателя изменений (как это было сделано для действия кнопки).
 */
public class SettingWindow extends JFrame {
    public static final String BTN_START = "Start new game";
    public static final String BTN_HUMAN_VS_AI = "Human vs AI";
    public static final String BTN_HUMAN_VS_HUMAN = "Human vs Human";
    public static final String LABEL_CHOICE_MODE = "Choice game type";
    public static final String LABEL_CHOICE_SIZE = "Choice field size: ";
    public static final String LABEL_CHOICE_LENGTH = "Choice win length: ";
    public static final String SIZE_PREFIX = "Field size is: ";
    public static final String WIN_LENGTH_PREFIX = "Length is: ";

    private static final int MODE_H_VS_AI = 0;
    private static final int MODE_H_VS_H = 1;

    private static final int MIN_SIZE = 3;
    private static final int MAX_SIZE = 10;

    private static final int WIDTH = 230;
    private static final int HEIGHT = 350;
    private GameWindow gameWindow;
    private JButton btnStart;
    private JPanel mainPanel;
    private JRadioButton ai, human;
    private JLabel fieldSizeLabel, winLengthLabel;
    private JSlider sliderFieldSize, sliderWinLength;

    SettingWindow(GameWindow gameWindow) {
        this.gameWindow=gameWindow;
        btnStart = new JButton(BTN_START);
        setLocationRelativeTo(gameWindow);
        setSize(WIDTH, HEIGHT);
        btnStart.addActionListener(e -> {
            setVisible(false);
            startGame();
        });
        mainPanel = new JPanel(new GridLayout(3, 1));
        add(mainPanel);
        add(btnStart, BorderLayout.SOUTH);
        mainPanel.add(choiceGameType());
        mainPanel.add(choiceFieldSize());
        mainPanel.add(choiceWinLength());
    }

    private void  startGame(){
        int type;
        if(ai.isSelected()){
            type = MODE_H_VS_AI;
        }else if (human.isSelected()){
            type = MODE_H_VS_H;
        } else {
            throw new RuntimeException("Something wrong");
        }
        int size = sliderFieldSize.getValue();
        int winLength = sliderWinLength.getValue();
        gameWindow.startNewGame(type, size, size, winLength);
    }

    private Component choiceGameType() {
        JPanel gameType = new JPanel(new GridLayout(3, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        ai = new JRadioButton(BTN_HUMAN_VS_AI);
        human = new JRadioButton(BTN_HUMAN_VS_HUMAN);
        JLabel label = new JLabel(LABEL_CHOICE_MODE);
        ai.setSelected(true);
        buttonGroup.add(ai);
        buttonGroup.add(human);
        gameType.add(label);
        gameType.add(ai);
        gameType.add(human);
        return gameType;
    }

    private Component choiceFieldSize() {
        JPanel fieldSize = new JPanel(new GridLayout(3, 1));
        JLabel choiceFieldSize = new JLabel(LABEL_CHOICE_SIZE);
        fieldSizeLabel = new JLabel(SIZE_PREFIX + MIN_SIZE);
        sliderFieldSize = new JSlider(MIN_SIZE, MAX_SIZE, MIN_SIZE);
        sliderFieldSize.addChangeListener(e -> {
            int maxSize = sliderFieldSize.getValue();
            fieldSizeLabel.setText(SIZE_PREFIX + maxSize);
            sliderWinLength.setMaximum(maxSize);
        });
        fieldSize.add(choiceFieldSize);
        fieldSize.add(sliderFieldSize);
        fieldSize.add(fieldSizeLabel);

        return fieldSize;
    }

    private Component choiceWinLength() {
        JPanel winLength = new JPanel(new GridLayout(3, 1));
        JLabel choiceWinLength = new JLabel(LABEL_CHOICE_LENGTH);
        winLengthLabel = new JLabel(WIN_LENGTH_PREFIX + MIN_SIZE);
        sliderWinLength = new JSlider(MIN_SIZE, MIN_SIZE, MIN_SIZE);
        sliderWinLength.addChangeListener(e -> winLengthLabel.setText(WIN_LENGTH_PREFIX + sliderWinLength.getValue()));
        winLength.add(choiceWinLength);
        winLength.add(sliderWinLength);
        winLength.add(winLengthLabel);
        return winLength;
    }
}

