package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.lunch.entity.Protein;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProteinDAO {

    @PersistenceContext
    EntityManager entityManager;


    public List<Protein> getProteinsFromPreviousDays(int days) {
//        select * from dish d
//        left join protein p on p.id = d.protein_id
//        join menu_dish md on d.id = md.dish_id
//        join menu m on m.id = md.menu_id
//        where m.menu_date < '2024-07-15'
//        order by m.menu_date desc
//        limit 2;
    return null;
    }
}
