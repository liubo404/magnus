package xyz.abc.api.core.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductService {
    @GetMapping(value = "/product/{productId}", produces = "application/json")
    Product getProduct(@PathVariable int productId);

    @PostMapping(value= "/product",
    consumes = "application/json",
    produces = "application/json")
Product createProduct(@RequestBody Product body);

@DeleteMapping(value = "/product/{productId}")
void deleteProduct(@PathVariable int productId);

}
