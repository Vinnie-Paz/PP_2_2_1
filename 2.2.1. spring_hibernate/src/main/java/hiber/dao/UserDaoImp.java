package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        String hql = "FROM Car WHERE model = :model AND series = :series";
        TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery(hql, Car.class);
        query.setParameter("model", model);
        query.setParameter("series", series);
        return query.getResultStream()
                .findFirst()
                .map(Car::getUser)
                .orElse(null);
    }
}
