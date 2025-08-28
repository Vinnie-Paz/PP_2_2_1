package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User Anton = new User("Anton", "Var", "ant@mail.ru");
        User Andrey = new User("Andrey", "Var", "and@mail.ru");
        User Alexey = new User("Alexey", "Var", "alx@mail.ru");
        User Anastasia = new User("Anastasia", "Var", "anast@mail.ru");

        Car Alfa = new Car("Alfa", 70);
        Car Beta = new Car("Beta", 3);
        Car Gamma = new Car("Gamma", 911);
        Car Omega = new Car("Omega", 13);

        userService.add(Anton.setCar(Alfa).setUser(Anton));
        userService.add(Andrey.setCar(Beta).setUser(Andrey));
        userService.add(Alexey.setCar(Gamma).setUser(Alexey));
        userService.add(Anastasia.setCar(Omega).setUser(Anastasia));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        User user = userService.getUserByCar("Alfa", 70);
        System.out.println(user.toString());

        try {
            User notFoundUser = userService.getUserByCar("Lada", 9);
        } catch (NoResultException e) {
        }
        context.close();
    }
}
