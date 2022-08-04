package xyz.abc.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.abc.api.core.product.Product;
import xyz.abc.api.core.product.ProductService;
import xyz.abc.api.exceptions.InvalidInputException;
import xyz.abc.api.exceptions.NotFoundException;
import xyz.abc.util.http.ServiceUtil;

// @RestController
// public class ProductServiceImpl implements ProductService {

//     private final ServiceUtil serviceUtil;

//     @Autowired
//     public ProductServiceImpl(ServiceUtil serviceUtil) {
//         this.serviceUtil = serviceUtil;
//     }

//     @Override
//     public Product getProduct(int productId) {
//         // return new Product(productId, "name-" + productId, 123,
//                 // serviceUtil.getServiceAddress());
//         return new Product(productId, "name-" + productId, 123, "
//         serviceUtil.getServiceAddress()");
//     }

// }


// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.RestController;
// import se.magnus.api.core.product.Product;
// import se.magnus.api.core.product.ProductService;
// import se.magnus.api.exceptions.InvalidInputException;
// import se.magnus.api.exceptions.NotFoundException;
// import se.magnus.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ServiceUtil serviceUtil;

  @Autowired
  public ProductServiceImpl(ServiceUtil serviceUtil) {
    this.serviceUtil = serviceUtil;
  }

  @Override
  public Product getProduct(int productId) {
    LOG.debug("/product return the found product for productId={}", productId);

    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }

    if (productId == 13) {
      throw new NotFoundException("No product found for productId: " + productId);
    }

    return new Product(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
  }
}

