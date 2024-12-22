package Model.Strategy;

import Model.Ticket;
import Model.Person;

import java.util.Map;

public interface PayBehaviour {
    Map<Person, Float> pay();
    @Override
    String toString();
}
