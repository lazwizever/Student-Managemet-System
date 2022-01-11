package model;

public class FinalExamMark {
    private String stRegistrationNo;
    private String stGrade;
    private String SubjectId;
    private int mark;

    public FinalExamMark() {
    }

    public FinalExamMark(String stRegistrationNo, String stGrade, String subjectId, int mark) {
        this.setStRegistrationNo(stRegistrationNo);
        this.setStGrade(stGrade);
        setSubjectId(subjectId);
        this.setMark(mark);
    }

    public String getStRegistrationNo() {
        return stRegistrationNo;
    }

    public void setStRegistrationNo(String stRegistrationNo) {
        this.stRegistrationNo = stRegistrationNo;
    }

    public String getStGrade() {
        return stGrade;
    }

    public void setStGrade(String stGrade) {
        this.stGrade = stGrade;
    }

    public String getSubjectId() {
        return SubjectId;
    }

    public void setSubjectId(String subjectId) {
        SubjectId = subjectId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}
