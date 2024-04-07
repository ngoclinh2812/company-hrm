package mr2.meetingroom02.dojosession.lunch.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Protein extends BaseEntity {

    @Column(name = "type", unique = true)
    private String proteinName;

    @Column(name = "is_vegan")
    private Boolean isVegan;

}
