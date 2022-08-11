package xyz.abc.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.abc.api.core.product.Product;
import xyz.abc.api.core.product.ProductService;
import xyz.abc.api.exceptions.InvalidInputException;
import xyz.abc.api.exceptions.NotFoundException;
import xyz.abc.product.persistence.ProductEntity;
import xyz.abc.product.persistence.ProductRepository;
import xyz.abc.util.http.ServiceUtil;

@RestController
public class ProductServiceImpl implements ProductService {

  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

  private final ServiceUtil serviceUtil;
  private final ProductRepository repository;
  private final ProductMapper mapper;

  // @Autowired
  // public ProductServiceImpl(ServiceUtil serviceUtil) {
  // this.serviceUtil = serviceUtil;
  // }

  @Autowired
  public ProductServiceImpl(ProductRepository repository, ProductMapper mapper, ServiceUtil serviceUtil) {
    this.repository = repository;
    this.mapper = mapper;
    this.serviceUtil = serviceUtil;
  }

  // @Override
  // public Product getProduct(int productId) {
  // LOG.debug("/product return the found product for productId={}", productId);

  // if (productId < 1) {
  // throw new InvalidInputException("Invalid productId: " + productId);
  // }

  // if (productId == 13) {
  // throw new NotFoundException("No product found for productId: " + productId);
  // }

  // return new Product(productId, "name-" + productId, 123,
  // serviceUtil.getServiceAddress());
  // }

  @Override
  public Product getProduct(int productId) {
    LOG.debug("/product return the found product for productId={}", productId);

    if (productId < 1)
      throw new InvalidInputException("InvalidproductId: " + productId);

    ProductEntity entity = repository.findByProductId(productId)
        .orElseThrow(() -> new NotFoundException("No product found forproductId: " + productId));

    Product response = mapper.entityToApi(entity);
    response.setServiceAddress(serviceUtil.getServiceAddress());
    return response;
  }

  @Override
  public Product createProduct(Product body) {
    try {
      ProductEntity entity = mapper.apiToEntity(body);
      ProductEntity newEntity = repository.save(entity);
      return mapper.entityToApi(newEntity);
    } catch (DuplicateKeyException dke) {
      throw new InvalidInputException("Duplicate key, Product Id: " +
          body.getProductId());
    }
  }

  @Override
  public void deleteProduct(int productId) {
    repository.findByProductId(productId).ifPresent(e -> repository.delete(e));
  }
}
