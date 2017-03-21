package ro.droptable.labproblems.domain;

import java.util.Objects;

/**
 * Created by vlad on 04.03.2017.
 *
 * Class that represents a 'real-world' {@code Student}
 */
public class Student extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private int group;
    private static long currentId = 1;

    public Student() {
    }

    public Student(String serialNumber, String name, int group) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
        this.setId(currentId++);
    }

    public Student(Long id, String serialNumber, String name, int group) {
        this.setId(id);
        this.serialNumber = serialNumber;
        this.name = name;
        this.group = group;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        currentId = id > currentId ? id + 1 : currentId;
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

        return this == o || !(o == null || getClass() != o.getClass()) &&
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

    @Override
    public String toString() {
        return "Student{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                '}' + super.toString();
    }
}