package MephiPackage.core;

import MephiPackage.objects.Mission;
import MephiPackage.report.*;
import MephiPackage.state.MissionToolState;
import MephiPackage.state.SingleMissionState;
import MephiPackage.state.CollectionState;
import MephiPackage.collection.MissionCollection;
import MephiPackage.utils.FileChooser;
import MephiPackage.core.MissionReader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MissionToolGUI extends JFrame {

    private MissionToolState state;
    private ReportConfig reportConfig;
    private JTextArea textArea;
    private JLabel modeLabel;
    private JComboBox<String> reportTypeCombo;
    private JButton configButton;

    public MissionToolGUI(Mission mission) {
        this(mission, new ReportConfig());
    }

    public MissionToolGUI(Mission mission, ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
        this.state = new SingleMissionState(this, mission);

        setTitle("Анализатор миссий");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        updateUI();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Верхняя панель
        JPanel topPanel = new JPanel(new BorderLayout());

        // Левая часть: выбор типа отчёта
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JLabel("Тип отчёта:"));
        reportTypeCombo = new JComboBox<>(new String[]{"summary", "detailed", "risk", "customizable"});
        reportTypeCombo.addActionListener(e -> generateReport());
        leftPanel.add(reportTypeCombo);

        configButton = new JButton("Настройки");
        configButton.addActionListener(e -> showConfigDialog());
        leftPanel.add(configButton);

        topPanel.add(leftPanel, BorderLayout.WEST);

        // Правая часть: режим работы
        modeLabel = new JLabel();
        modeLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        modeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        topPanel.add(modeLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Меню
        initMenu();

        // Центральная область
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");

        JMenuItem addMissionItem = new JMenuItem("Добавить миссию");
        addMissionItem.addActionListener(e -> addMission());
        fileMenu.add(addMissionItem);

        JMenuItem addFolderItem = new JMenuItem("Добавить папку с миссиями");
        addFolderItem.addActionListener(e -> addFolder());
        fileMenu.add(addFolderItem);

        fileMenu.addSeparator();

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(e -> dispose());
        fileMenu.add(exitItem);

        JMenu viewMenu = new JMenu("Вид");

        JMenuItem showCurrentItem = new JMenuItem("Показать текущую миссию");
        showCurrentItem.addActionListener(e -> state.showCurrentMission());
        viewMenu.add(showCurrentItem);

        JMenuItem showStatsItem = new JMenuItem("Показать общую статистику");
        showStatsItem.addActionListener(e -> state.showStatistics());
        viewMenu.add(showStatsItem);

        JMenuItem showListItem = new JMenuItem("Показать список миссий");
        showListItem.addActionListener(e -> state.showMissionList());
        viewMenu.add(showListItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);
    }

    private void addMission() {
        File file = FileChooser.selectFile();
        if (file == null) return;

        MissionReader reader = new MissionReader();
        try {
            Mission newMission = reader.readMission(file);
            state.addMission(newMission);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка чтения файла: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addFolder() {
        File folder = FileChooser.selectFolder();
        if (folder == null) return;
        state.addFolder(folder);
    }

    private void generateReport() {
        String selectedType = (String) reportTypeCombo.getSelectedItem();
        ReportGenerator generator;

        if ("customizable".equals(selectedType)) {
            generator = new CustomizableReportGenerator(reportConfig);
        } else {
            generator = ReportFactory.getGenerator(selectedType);
        }

        // В зависимости от состояния берём нужную миссию или коллекцию
        if (state.getMissionCount() == 1) {
            String report = generator.generate(state.getCurrentMission());
            textArea.setText(report);
        } else {
            // Для коллекции показываем статистику, а не отчёт
            state.showStatistics();
            return;
        }

        textArea.setCaretPosition(0);
        updateModeLabel(state.getModeName());
    }

    private void showConfigDialog() {
        JDialog dialog = new JDialog(this, "Настройки отчёта", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 500);
        dialog.setLocationRelativeTo(this);

        ReportConfigPanel panel = new ReportConfigPanel(reportConfig);
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> {
            reportConfig = panel.getUpdatedConfig();
            dialog.dispose();
            generateReport();
        });

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(saveButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }


    public void setState(MissionToolState state) {
        this.state = state;
        updateUI();
    }

    public void setTextArea(String text) {
        textArea.setText(text);
        textArea.setCaretPosition(0);
    }

    public void updateModeLabel(String modeName) {
        modeLabel.setText(modeName);
    }

    public void updateUI() {
        updateModeLabel(state.getModeName());
        state.display();
    }

    public MissionToolState getCurrentState() {
        return state;
    }
}