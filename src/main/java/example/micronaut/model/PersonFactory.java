package example.micronaut.model;

import example.micronaut.model.Person;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Prototype;
import java.util.HashSet;

@Factory
public class PersonFactory {


    @Prototype
    private HashSet<Person> personsRepository(){

        return new HashSet<Person>();
    }
}
