package com.example.Flower.services;

import com.example.Flower.models.Image;
import com.example.Flower.models.Product;
import com.example.Flower.models.User;
import com.example.Flower.repositories.ProductRepository;
import com.example.Flower.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

   public List<Product> listProducts(String title){
      if(title != null) return productRepository.findByTitle(title);
      return productRepository.findAll();
   }

   public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
       product.setUser(getUserByPricipal(principal));
       Image image1;
       Image image2;
       Image image3;
       if(file1.getSize() != 0){
           image1 = toImageEntity(file1);
           image1.setPreviewImage(true);
           product.addImageToProduct(image1);
       }
       if(file2.getSize() != 0){
           image2 = toImageEntity(file2);
           product.addImageToProduct(image2);
       }
       if(file3.getSize() != 0){
           image3 = toImageEntity(file3);
           product.addImageToProduct(image3);
       }
       log.info("Save new Product. Title: {}", product.getTitle());
       Product productFromDb = productRepository.save(product);
       productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
       productRepository.save(product);
   }

    public User getUserByPricipal(Principal principal) {
       if(principal==null) return new User();
       return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file)throws IOException {
       Image image = new Image();
       image.setName(file.getName());
       image.setOriginalFileName(file.getOriginalFilename());
       image.setContentType(file.getContentType());
       image.setSize(file.getSize());
       image.setBytes(file.getBytes());
       return image;
    }

    public void deleteProduct(Long id){
       productRepository.deleteById(id);

   }


    public Product getProductById(Long id)
    {
      return productRepository.findById(id).orElse(null);
    }
}
