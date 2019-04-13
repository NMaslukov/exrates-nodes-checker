package me.exrates.checker.service.impl;

import me.exrates.checker.service.NodeApi;
import me.exrates.checker.service.NodesChecker;
import me.exrates.checker.service.TelegramBot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.core.Response;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NodesCheckerImplTest {

    @MockBean
    private TelegramBot telegramBot;
    @MockBean
    private NodeApi nodeApi;
    @Autowired
    private NodesChecker nodesChecker;

    @Test
    public void sendNodeStatusReport() throws Exception {
        Response mock = Mockito.mock(Response.class);
        Mockito.when(mock.readEntity(String.class)).thenReturn("1");
        Mockito.when(nodeApi.getBlocksCount(anyString())).thenReturn(mock);
        Mockito.verify(telegramBot, Mockito.times(1)).sendMessage(anyLong(), anyString());
    }
}