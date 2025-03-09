package my.vb.sportbook.dataretriever.controller;

import my.vb.sportbook.dataretriever.config.CacheInspector;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CacheController.class)
class CacheControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CacheInspector cacheInspector;

    @Test
    void testGetCacheStatus_ReturnsOkAndStatusMessage() throws Exception {
        String mockCacheStatus = "Cache is active and healthy.";

        when(cacheInspector.getEntityCacheStatus()).thenReturn(mockCacheStatus);

        mockMvc.perform(get("/entitiesCache/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mockCacheStatus));
    }

    @Test
    void testGetCollectionEntityCacheStatus_ReturnsOkAndStatusMessage() throws Exception {
        String mockCollectionCacheStatus = "Collection cache is active and healthy.";

        when(cacheInspector.getCollectionEntityCacheStatus()).thenReturn(mockCollectionCacheStatus);

        mockMvc.perform(get("/collectionEntitiesCache/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(mockCollectionCacheStatus));
    }
}