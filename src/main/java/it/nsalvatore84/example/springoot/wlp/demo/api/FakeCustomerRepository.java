package ch.corner.example.springoot.wlp.demo.api;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeCustomerRepository implements CustomerRepository{

    private final String val;

    public FakeCustomerRepository(@Value("${mysecret}") String val) {
        this.val = val;
    }

    @Override
    public List<Customer> findAll() {
        LoggerFactory.getLogger(getClass()).info("find all");
        return Arrays.asList(new Customer(10L, "Salvatore Napoli 2"));
    }
}
