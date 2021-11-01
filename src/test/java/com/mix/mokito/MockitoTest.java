package com.mix.mokito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @Mock
    List<String> mockList;

    @BeforeEach
    public void beforeEach(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void verify_SampleInvocationOnMock(){
        mockList.size();
        Mockito.verify(mockList).size();
    }
}
