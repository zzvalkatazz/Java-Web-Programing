package model.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.model.Hall;
import model.model.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-21T20:59:33")
@StaticMetamodel(Reservation.class)
public class Reservation_ { 

    public static volatile SingularAttribute<Reservation, LocalDateTime> createdAt;
    public static volatile SingularAttribute<Reservation, Hall> hall;
    public static volatile SingularAttribute<Reservation, LocalDateTime> startTime;
    public static volatile SingularAttribute<Reservation, Long> id;
    public static volatile SingularAttribute<Reservation, LocalDateTime> endTime;
    public static volatile SingularAttribute<Reservation, String> eventType;
    public static volatile SingularAttribute<Reservation, User> user;
    public static volatile SingularAttribute<Reservation, String> status;

}