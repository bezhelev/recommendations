package com.bezhaleu.recommendations.listeners;

import com.bezhaleu.recommendations.repositories.CsvRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.event.ContextRefreshedEvent;

@ExtendWith(MockitoExtension.class)
class ContextRefreshEventListenerTest {

    @Mock
    private CsvRepository baseCsvRepositories;

    @InjectMocks
    private ContextRefreshEventListener contextRefreshEventListener;

    @Test
    void testHandleContextRefreshEvent() {
        contextRefreshEventListener.handleContextRefreshEvent(mock(ContextRefreshedEvent.class));

        verify(baseCsvRepositories, times(1)).fillCryptos();
    }
}