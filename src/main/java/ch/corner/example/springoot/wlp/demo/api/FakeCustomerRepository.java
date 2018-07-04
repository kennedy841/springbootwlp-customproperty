package ch.corner.example.springoot.wlp.demo.api;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeCustomerRepository implements CustomerRepository{
    @Override
    public List<Customer> findAll() {
        return Arrays.asList(new Customer(10L, "Salvatore Napoli"));
    }
}
