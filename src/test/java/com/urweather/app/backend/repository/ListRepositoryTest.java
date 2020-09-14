package com.urweather.app.backend.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ListRepositoryTest {

    List<Integer> listOfIntegers = Arrays.asList(1,51,52,3);

    @Test
    public void testSaveAllFunctionality() {
        ListRepository<Integer> listRepository = new ListRepository<>();
        int originalCount = listOfIntegers.size();
        listRepository.saveAll(listOfIntegers);
        int returnedCount = listRepository.count();
        Assert.assertEquals(originalCount, returnedCount);
    }

    @Test
    public void testDeleteAllFunctionality() {
        ListRepository<Integer> listRepository = new ListRepository<>();
        listRepository.saveAll(listOfIntegers);
        listRepository.deleteAll();
        int returnedCount = listRepository.count();
        Assert.assertEquals(0, returnedCount);
    }

    @Test
    public void testFindAllFunctionality() {
        ListRepository<Integer> listRepository = new ListRepository<>();
        listRepository.saveAll(listOfIntegers);
        List<Integer> returnedList = listRepository.findAll();
        Assert.assertEquals(listOfIntegers, returnedList);
    }

    @Test
    public void testFindFunctionality() {
        ListRepository<Integer> listRepository = new ListRepository<>();
        listRepository.saveAll(listOfIntegers);
        Integer returnedIntAtZero = listRepository.find(0);
        Integer returnedIntAtTwo = listRepository.find(2);
        Assert.assertEquals(listOfIntegers.get(0) , returnedIntAtZero);
        Assert.assertEquals(listOfIntegers.get(2), returnedIntAtTwo);
    }

    @Test
    public void testCountFunctionality() {
        ListRepository<Integer> listRepository = new ListRepository<>();
        listRepository.saveAll(listOfIntegers);
        int returnedCount = listRepository.count();
        Assert.assertEquals(listOfIntegers.size(), returnedCount);
    }
}