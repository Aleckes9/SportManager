/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.projetsportmanager.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.dozer.Mapper;

public class DozerListsMapper<E extends Serializable, B extends Serializable> {

	public List<B> mapEntityIterableToBeanList(Mapper dozerBeanMapper, Iterable<E> entities, Class<B> targetClass){
		
		List<B> beansList = new ArrayList<B>();
		for(E entity : entities){
			beansList.add(dozerBeanMapper.map(entity, targetClass));
		}
		return beansList;
	}
	
	public List<E> mapBeanIterableToEntityList(Mapper dozerBeanMapper, Iterable<B> beans, Class<E> targetClass){
		
		List<E> entitySet = new ArrayList<E>();
		for(B bean : beans){
			entitySet.add(dozerBeanMapper.map(bean,targetClass));
		}
		return entitySet;
	}
	
	public Set<E> mapBeanIterableToEntitySet(Mapper dozerBeanMapper, Iterable<B> beans, Class<E> targetClass){
		
		Set<E> entitySet = new HashSet<E>();
		for(B bean : beans){
			entitySet.add(dozerBeanMapper.map(bean,targetClass));
		}
		return entitySet;
	}

	public List<B> mapEntitySetToBeanList(Mapper dozerBeanMapper, Set<ConstraintViolation<?>> entities, Class<B> targetClass){
		
		List<B> beansList = new ArrayList<B>();
		for(ConstraintViolation<?> entity : entities){
			beansList.add(dozerBeanMapper.map(entity, targetClass));
		}
		return beansList;
	}

	
}
