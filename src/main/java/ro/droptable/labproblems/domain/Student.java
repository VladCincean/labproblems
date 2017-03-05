package ro.droptable.labproblems.domain;

import java.util.Objects;

/**
 * Created by vlad on 04.03.2017.
 */
public class Student extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private int group;

    public Student() {
    }

    public Student(String serialNumber, String name, int group) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    // TODO: rewrite this without if-s
    @Override
    public boolean equals(Object o) {
        // Version 1
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Student student = (Student) o;
//
//        if (group != student.group) return false;
//        if (!serialNumber.equals(student.serialNumber)) return false;
//        return name.equals(student.name);

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

//        Student student = (Student) o;

        return
                Objects.equals(this.group, ((Student)o).group) &&
                Objects.equals(this.serialNumber, ((Student)o).serialNumber) &&
                Objects.equals(this.name, ((Student)o).name);
    }

    @Override
    public int hashCode() {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + group;
        return result;
    }

}