package view.tm;

import com.jfoenix.controls.JFXCheckBox;

public class StudentPaymentsTM {
    private String stRegistrationNo;
    private String stName;
    private JFXCheckBox jfxCheckBox;

    public StudentPaymentsTM() {
    }

    public StudentPaymentsTM(String stRegistrationNo, String stName, JFXCheckBox jfxCheckBox) {
        this.setStRegistrationNo(stRegistrationNo);
        this.setStName(stName);
        this.setJfxCheckBox(jfxCheckBox);
    }

    public String getStRegistrationNo() {
        return stRegistrationNo;
    }

    public void setStRegistrationNo(String stRegistrationNo) {
        this.stRegistrationNo = stRegistrationNo;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public JFXCheckBox getJfxCheckBox() {
        return jfxCheckBox;
    }

    public void setJfxCheckBox(JFXCheckBox jfxCheckBox) {
        this.jfxCheckBox = jfxCheckBox;
    }
}
