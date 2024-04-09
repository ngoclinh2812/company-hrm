package mr2.meetingroom02.dojosession.employee.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.department.entity.Department;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
public class Employee extends BaseEntity {

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Column(unique = true)
    @NonNull
    private String email;

    @Column(unique = true)
    private String phone;

    @Column
    private String firstName;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column
    private String lastName;

    @Column
    private String middleName;

    @Column
    @Min(0)
    private int salary;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="department_id")
    private Department department;

    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}$")
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private StatusEnum status;
}
