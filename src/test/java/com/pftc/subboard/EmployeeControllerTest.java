package com.pftc.subboard;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testOne() {
        // Employee à récupérer par la méthode find by
        Optional<Employee> returnedEmployee = Optional.of(new Employee());

        // Configure mock
        doReturn(returnedEmployee).when(employeeRepositoryMock).findById(anyLong());

        // Perform thes test
        Employee actualEmpoloyee = employeeController.one(5L);

        // Junit asserts
        assertEquals(returnedEmployee.get(), actualEmpoloyee);
        
    }

    @Test
    public void testOneFail() {
        // Configure mock
        doReturn(Optional.empty()).when(employeeRepositoryMock).findById(anyLong());

        // Perform thes test
        assertThrows(NullPointerException.class, () -> employeeController.one(5L));        
    }

}
