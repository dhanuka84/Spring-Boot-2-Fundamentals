package com.packt.springboot.blogmania.category;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InMemoryCategoryRepository implements CategoryRepository {
    private List<Category> categories;

    @PostConstruct
    public void init() {
        categories = new ArrayList<>();

        Category category1 = new Category("general", "General");
        Category category2 = new Category("spring", "Spring Framework");
        categories.add(category1);
        categories.add(category2);
    }

    @Override
    public Category save(Category category) {
        categories.add(category);
        return category;
    }

    @Override
    public Optional<Category> findCategoryBySlug(String slug) {
        Objects.requireNonNull(slug, "Slug required");

        return categories.stream()
                .filter(category -> slug.equals(category.getSlug()))
                .findFirst();
    }

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories);
    }
}