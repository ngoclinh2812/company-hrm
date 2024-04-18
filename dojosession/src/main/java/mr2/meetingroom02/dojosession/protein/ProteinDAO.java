package mr2.meetingroom02.dojosession.protein;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class ProteinDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<Protein> getProteinsFromPreviousDays(int days) {
        TypedQuery<Protein> query = entityManager.createQuery(
                        "SELECT p FROM Protein p " +
                                "JOIN Dish d ON p.id = d.protein.id " +
                                "JOIN MenuDish md ON d.id = md.dish.id " +
                                "JOIN Menu m ON m.id = md.menu.id " +
                                "WHERE m.menuDate < :currentDate " +
                                "ORDER BY m.menuDate DESC", Protein.class
                )
                .setParameter("currentDate", LocalDate.now())
                .setMaxResults(days);

        return query.getResultList();
    }
}
