package ch.corner.example.springoot.wlp.demo.api;

import java.util.List;

public interface CustomerRepository {
    List<Customer> findAll();

    class Customer {
        private final long id;
        private final String name;

        public Customer(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
