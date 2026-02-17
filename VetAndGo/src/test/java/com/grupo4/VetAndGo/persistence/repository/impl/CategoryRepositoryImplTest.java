package com.grupo4.VetAndGo.persistence.repository.impl;

import com.grupo4.VetAndGo.persistence.dao.jpa.CategoryJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.entity.CategoryJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryImplTest {

    @Mock
    private CategoryJpaDao categoryJpaDao;

    @InjectMocks
    private CategoryRepositoryImpl categoryRepository;

    @Test
    void findAll_ShouldDelegatetoDao() {
        when(categoryJpaDao.findAll()).thenReturn(List.of(new CategoryJpaEntity()));
        
        categoryRepository.findAll();
        
        verify(categoryJpaDao).findAll();
    }

    @Test
    void findById_ShouldDelegateToDao() {
        when(categoryJpaDao.findById(1L)).thenReturn(Optional.of(new CategoryJpaEntity()));
        
        categoryRepository.findById(1L);
        
        verify(categoryJpaDao).findById(1L);
    }

    @Test
    void save_ShouldDelegateToDao_Insert_WhenIdIsNull() {
        CategoryJpaEntity entity = new CategoryJpaEntity(null, "name", "desc");
        when(categoryJpaDao.insert(entity)).thenReturn(entity);
        
        categoryRepository.save(entity);
        
        verify(categoryJpaDao).insert(entity);
        verify(categoryJpaDao, never()).update(any());
    }

    @Test
    void save_ShouldDelegateToDao_Update_WhenIdIsNotNull() {
        CategoryJpaEntity entity = new CategoryJpaEntity(1L, "name", "desc");
        when(categoryJpaDao.update(entity)).thenReturn(entity);
        
        categoryRepository.save(entity);
        
        verify(categoryJpaDao).update(entity);
        verify(categoryJpaDao, never()).insert(any());
    }

    @Test
    void deleteById_ShouldDelegateToDao() {
        categoryRepository.deleteById(1L);
        verify(categoryJpaDao).deleteById(1L);
    }

    @Test
    void findByName_ShouldDelegateToDao() {
        categoryRepository.findByName("name");
        verify(categoryJpaDao).findByName("name");
    }
}
