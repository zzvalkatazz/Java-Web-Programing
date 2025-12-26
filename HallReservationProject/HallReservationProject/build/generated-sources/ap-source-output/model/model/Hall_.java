package model.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.model.Reservation;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-12-26T14:06:58")
@StaticMetamodel(Hall.class)
public class Hall_ { 

    public static volatile ListAttribute<Hall, Reservation> reservations;
    public static volatile SingularAttribute<Hall, String> name;
    public static volatile SingularAttribute<Hall, String> location;
    public static volatile SingularAttribute<Hall, Long> id;
    public static volatile SingularAttribute<Hall, String> type;
    public static volatile SingularAttribute<Hall, Integer> capacity;

}