package model;

public class FinalExam {
    private String examId;
    private String subjectId;

    public FinalExam() {
    }

    public FinalExam(String examId, String subjectId) {
        this.setExamId(examId);
        this.setSubjectId(subjectId);
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
