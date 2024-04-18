package mr2.meetingroom02.dojosession.protein;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Protein extends BaseEntity {

    @Column(name = "protein_type", unique = true)
    private String proteinType;

    @Column(name = "is_vegan")
    private Boolean isVegan;

}
