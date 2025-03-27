package com.Game.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.Game.model.Territory;


public class MapLoaderTest {
    
    @TempDir
    Path tempDir;
    
    private String sampleMapContent = 
        "[continents]\n" +
        "Asia 7\n" +
        "\n" +
        "[countries]\n" +
        "1 India 1\n" +
        "2 China 1\n" +
        "\n" +
        "[borders]\n" +
        "1 2\n" +
        "2 1\n";
        
    @Test
    public void testReadAndValidateMap() throws IOException {
        Path mapFile = tempDir.resolve("sample.map");
        Files.writeString(mapFile, sampleMapContent);
        
        MapLoader loader = new MapLoader();
        loader.read(mapFile.toString());
        
        com.Game.model.Map loadedMap = loader.getLoadedMap();
        assertNotNull(loadedMap);
        
        // Check continents set in map
        java.util.Map<String, Integer> continents = loadedMap.getContinents();

        assertTrue(continents.containsKey("Asia"));
        assertEquals(7, continents.get("Asia").intValue());
        
        // Check territories
        List<Territory> territories = loadedMap.getTerritoryList();
        assertEquals(2, territories.size());
        assertNotNull(loadedMap.getTerritoryByName("India"));
        assertNotNull(loadedMap.getTerritoryByName("China"));
        
        // Check borders: India should have China as neighbor
        Territory india = loadedMap.getTerritoryByName("India");
        Territory china = loadedMap.getTerritoryByName("China");
        assertTrue(india.getNeighborList().contains(china));
    }
    
    @Test
    public void testIsValid() throws IOException {
        Path mapFile = tempDir.resolve("sample.map");
        Files.writeString(mapFile, sampleMapContent);
        
        MapLoader loader = new MapLoader();
        boolean valid = loader.isValid(mapFile.toString());
        assertTrue(valid);
    }
    
    @Test
    public void testResetLoadedMap() {
        MapLoader loader = new MapLoader();
        loader.setLoadedMap(new com.Game.model.Map());
        loader.resetLoadedMap();
        assertNotNull(loader.getLoadedMap());
        assertTrue(loader.getLoadedMap().getTerritoryList().isEmpty());
    }
}
