package model;

public class Subject {
    private String subjectId;
    private String subject;

    public Subject() {
    }

    public Subject(String subjectId, String subject) {
        this.setSubjectId(subjectId);
        this.setSubject(subject);
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
