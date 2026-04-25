package MephiPackage.report;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ReportConfigPanel extends JPanel {

    private Map<ReportConfig.Section, JCheckBox> checkBoxes = new HashMap<>();
    private ReportConfig config;

    public ReportConfigPanel(ReportConfig config) {
        this.config = config;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Выберите разделы для отображения"));
        initCheckBoxes();
        loadConfig();
    }

    private void initCheckBoxes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        for (ReportConfig.Section section : ReportConfig.Section.values()) {
            JCheckBox checkBox = new JCheckBox(section.getDisplayName());
            checkBoxes.put(section, checkBox);
            add(checkBox, gbc);
            gbc.gridy++;
        }
    }

    private void loadConfig() {
        for (Map.Entry<ReportConfig.Section, JCheckBox> entry : checkBoxes.entrySet()) {
            entry.getValue().setSelected(config.isEnabled(entry.getKey()));
        }
    }

    public void saveToConfig() {
        for (Map.Entry<ReportConfig.Section, JCheckBox> entry : checkBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                config.enableSection(entry.getKey());
            } else {
                config.disableSection(entry.getKey());
            }
        }
    }

    public ReportConfig getUpdatedConfig() {
        saveToConfig();
        return config;
    }

    public void selectAll() {
        for (JCheckBox checkBox : checkBoxes.values()) {
            checkBox.setSelected(true);
        }
    }

    public void deselectAll() {
        for (JCheckBox checkBox : checkBoxes.values()) {
            checkBox.setSelected(false);
        }
    }

    public void selectDefault() {
        for (Map.Entry<ReportConfig.Section, JCheckBox> entry : checkBoxes.entrySet()) {
            ReportConfig.Section section = entry.getKey();
            boolean isDefault = (section == ReportConfig.Section.BASIC_INFO ||
                    section == ReportConfig.Section.CURSES ||
                    section == ReportConfig.Section.SORCERERS ||
                    section == ReportConfig.Section.TECHNIQUES);
            entry.getValue().setSelected(isDefault);
        }
    }
}
