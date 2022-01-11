package view.tm;

public class ManageStudentMarksTM {
    private String stRegistrationNo;
    private String stName;
    private String stGrade;
    private String subject;;
    private int mark;


    public ManageStudentMarksTM() {
    }

    public ManageStudentMarksTM(String stRegistrationNo, String stName, String stGrade, String subject, int mark) {
        this.setStRegistrationNo(stRegistrationNo);
        this.setStName(stName);
        this.setStGrade(stGrade);
        this.setSubject(subject);
        this.setMark(mark);
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

    public String getStGrade() {
        return stGrade;
    }

    public void setStGrade(String stGrade) {
        this.stGrade = stGrade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
