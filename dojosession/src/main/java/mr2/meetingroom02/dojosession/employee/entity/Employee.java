package mr2.meetingroom02.dojosession.employee.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import mr2.meetingroom02.dojosession.assignment.entity.Assignment;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.department.entity.Department;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee extends BaseEntity {
    private LocalDate dateOfBirth;
    private String firstName;
    private String gender;
    private String lastName;
    private String middleName;
    private int salary;

    @ManyToOne
    @JoinColumn(name="deptid")
    private Department department;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<Assignment> assignmentList;


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    public void setAssignmentList(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }
}
