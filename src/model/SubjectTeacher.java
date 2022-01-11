package model;

public class SubjectTeacher {
    private String teacherId;
    private String subjectId;

    public SubjectTeacher() {
    }

    public SubjectTeacher(String teacherId, String subjectId) {
        this.setTeacherId(teacherId);
        this.setSubjectId(subjectId);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}
