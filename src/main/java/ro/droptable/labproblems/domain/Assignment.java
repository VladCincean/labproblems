package ro.droptable.labproblems.domain;

import java.util.Objects;

/**
 * Created by vlad on 05.03.2017.
 *
 * Class that represents a 'real-world' {@code Assignment}
 */
public class Assignment extends BaseEntity<Long> {
    private long studentId;
    private long problemId;
    private double grade;
    private static long currentId = 1;

    public Assignment() {
    }

    public Assignment(long studentId, long problemId) {
        this.studentId = studentId;
        this.problemId = problemId;
        this.grade = 0;
        this.setId(currentId++);
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        currentId = id > currentId ? id : currentId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getProblemId() {
        return problemId;
    }

    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) &&
                Objects.equals(this.studentId, ((Assignment)o).studentId) &&
                Objects.equals(this.problemId, ((Assignment)o).problemId);
    }

    @Override
    public int hashCode() {
        int result = (int)(studentId ^ (studentId >>> 32));
        result = 31 * result + (int)(problemId ^ (problemId >>> 32));
        result = 31 * result + (int)(Double.doubleToLongBits(grade) ^ Double.doubleToLongBits(grade) >>> 32);
        return result;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "studentId=" + studentId +
                ", problemId=" + problemId +
                ", grade=" + grade +
                '}' + super.toString();
    }
}
