package com.application.catalogue.Service;

import com.application.catalogue.Product.Brand;
import com.application.catalogue.Repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;


    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(Long id, Brand brandDetails) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        brand.setName(brandDetails.getName());
        brand.setImagePath(brandDetails.getImagePath());
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
        // Delete the image from the Google Cloud Storage bucket
        googleCloudStorageService.deleteFile(brand.getImagePath());
        brandRepository.deleteById(id);
    }
}