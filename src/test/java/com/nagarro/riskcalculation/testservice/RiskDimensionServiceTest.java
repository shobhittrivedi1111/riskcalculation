package com.nagarro.riskcalculation.testservice;
import com.nagarro.riskcalculation.advice.RiskDimensionException;
import com.nagarro.riskcalculation.model.RiskDimension;
import com.nagarro.riskcalculation.repository.RiskDimensionRepository;
import com.nagarro.riskcalculation.service.RiskDimensionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RiskDimensionServiceTest {

    @Mock
    private RiskDimensionRepository riskDimensionRepository;

    @InjectMocks
    private RiskDimensionService riskDimensionService;

    @Test
    public void testGetRiskDimension_ExistingDimension() {
        // Test case for getting an existing risk dimension by dimension
        String dimension = "Dimension1";
        RiskDimension riskDimension = new RiskDimension();
        riskDimension.setDimension(dimension);

        when(riskDimensionRepository.findById(dimension)).thenReturn(Optional.of(riskDimension));

        RiskDimension result = riskDimensionService.getRiskDimension(dimension);

        assertEquals(dimension, result.getDimension());
        verify(riskDimensionRepository, times(1)).findById(dimension);
    }

    @Test
    public void testGetRiskDimension_NonExistingDimension() {
        // Test case for getting a non-existing risk dimension by dimension
        String dimension = "NonExistingDimension";

        when(riskDimensionRepository.findById(dimension)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> riskDimensionService.getRiskDimension(dimension));
        verify(riskDimensionRepository, times(1)).findById(dimension);
    }

    @Test
    public void testGetAllRiskDimensions() {
        // Test case for getting all risk dimensions
        List<RiskDimension> mockRiskDimensions = Arrays.asList(
                new RiskDimension("Dimension1", 0.25),
                new RiskDimension("Dimension2", 0.35)
        );

        when(riskDimensionRepository.findAll()).thenReturn(mockRiskDimensions);

        List<RiskDimension> result = riskDimensionService.getAllRiskDimensions();

        assertEquals(mockRiskDimensions, result);
        verify(riskDimensionRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteRiskDimension_ExistingDimension() {
        // Test case for deleting an existing risk dimension by dimension
        String dimension = "Dimension1";
        RiskDimension riskDimension = new RiskDimension();
        riskDimension.setDimension(dimension);

        when(riskDimensionRepository.findById(dimension)).thenReturn(Optional.of(riskDimension));

        boolean isDeleted = riskDimensionService.deleteRiskDimension(dimension);

        assertTrue(isDeleted);
        verify(riskDimensionRepository, times(1)).findById(dimension);
        verify(riskDimensionRepository, times(1)).deleteById(dimension);
    }

    @Test
    public void testDeleteRiskDimension_NonExistingDimension() {
        // Test case for deleting a non-existing risk dimension by dimension
        String dimension = "NonExistingDimension";

        when(riskDimensionRepository.findById(dimension)).thenReturn(Optional.empty());

        assertThrows(RiskDimensionException.class, () -> riskDimensionService.deleteRiskDimension(dimension));
        verify(riskDimensionRepository, times(1)).findById(dimension);
        verify(riskDimensionRepository, never()).deleteById(dimension);
    }


        @Test
        void testAddOrUpdateDimensionWeight_EmptyList_ThrowsIllegalArgumentException() {
            // Arrange
            List<RiskDimension> emptyList = List.of();

            // Act and Assert
            assertThrows(IllegalArgumentException.class, () -> riskDimensionService.addOrUpdateDimensionWeight(emptyList));
            verifyNoInteractions(riskDimensionRepository);
        }

    @Test
    void testAddOrUpdateDimensionWeight_AddNewDimensionsWithValidWeight_Success() {
        // Arrange
        List<RiskDimension> mockExistingDimensions = List.of(
                new RiskDimension("Dimension1", 0.50),
                new RiskDimension("Dimension2", 0.50)
        );

        List<RiskDimension> mockUpdatedDimensions = List.of(
                new RiskDimension("Dimension3", 30.0),
                new RiskDimension("Dimension4", 70.0)
        );

        // Create a mutable copy of mockExistingDimensions
        List<RiskDimension> mutableMockExistingDimensions = new ArrayList<>(mockExistingDimensions);

        // Mock the findAll method to return the mutable copy
        when(riskDimensionRepository.findAll()).thenReturn(mutableMockExistingDimensions);
        when(riskDimensionRepository.saveAll(mockUpdatedDimensions)).thenReturn(mockUpdatedDimensions);

        // Act
        List<RiskDimension> result = riskDimensionService.addOrUpdateDimensionWeight(mockUpdatedDimensions);

        // Assert
        assertEquals(mockUpdatedDimensions.size(), result.size());
        assertTrue(result.containsAll(mockUpdatedDimensions));
        verify(riskDimensionRepository).findAll();
        verify(riskDimensionRepository).saveAll(mockUpdatedDimensions);
    }


    @Test
    void testAddOrUpdateDimensionWeight_UpdateExistingDimensionsWithValidWeight_Success() {
        // Arrange
        List<RiskDimension> mockExistingDimensions = List.of(
                new RiskDimension("Dimension1", 0.65),
                new RiskDimension("Dimension2", 0.35)
        );

        List<RiskDimension> mockUpdatedDimensions = List.of(
                new RiskDimension("Dimension1", 60.0),
                new RiskDimension("Dimension2", 40.0)
        );

        // Create a mutable copy of mockExistingDimensions
        List<RiskDimension> mutableMockExistingDimensions = new ArrayList<>(mockExistingDimensions);

        // Mock the findAll method to return the mutable copy
        when(riskDimensionRepository.findAll()).thenReturn(mutableMockExistingDimensions);
        when(riskDimensionRepository.saveAll(mockUpdatedDimensions)).thenReturn(mockUpdatedDimensions);

        // Act
        List<RiskDimension> result = riskDimensionService.addOrUpdateDimensionWeight(mockUpdatedDimensions);

        // Assert
        assertEquals(mockUpdatedDimensions.size(), result.size());
        assertTrue(result.containsAll(mockUpdatedDimensions));
        verify(riskDimensionRepository).findAll();
        verify(riskDimensionRepository).saveAll(mockUpdatedDimensions);
    }


    @Test
    void testAddOrUpdateDimensionWeight_DuplicateDimensionName_ThrowsEntityExistsException() {
        // Arrange
        List<RiskDimension> mockExistingDimensions = new ArrayList<>(List.of(
                new RiskDimension("Dimension1", 0.65),
                new RiskDimension("Dimension2", 0.35)
        ));

        List<RiskDimension> mockUpdatedDimensions = List.of(
                new RiskDimension("Dimension2", 30.0),
                new RiskDimension("Dimension4", 70.0)
        );
        when(riskDimensionRepository.findAll()).thenReturn(mockExistingDimensions);
        when(riskDimensionRepository.saveAll(any())).thenThrow(EntityExistsException.class);
        assertThrows(EntityExistsException.class, () -> riskDimensionService.addOrUpdateDimensionWeight(mockUpdatedDimensions));
        verify(riskDimensionRepository, times(1)).findAll();
        verify(riskDimensionRepository, times(1)).saveAll(eq(mockUpdatedDimensions));
    }

    @Test
    void testAddOrUpdateDimensionWeight_SumOfWeightValuesNotEqualTo100_ThrowsIllegalArgumentException() {
        // Arrange
        List<RiskDimension> mockUpdatedDimensions = List.of(
                new RiskDimension("Dimension1", .30),
                new RiskDimension("Dimension2", .70)
        );
        when(riskDimensionRepository.findAll()).thenReturn(List.of());
        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> riskDimensionService.addOrUpdateDimensionWeight(mockUpdatedDimensions));
        verify(riskDimensionRepository).findAll();
        verifyNoMoreInteractions(riskDimensionRepository);
    }



    @Test
    void testAddOrUpdateDimensionWeight_AddAndUpdateTogether_SuccessAndThrowsEntityExistsException() {
        // Arrange
        List<RiskDimension> mockExistingDimensions = List.of(
                new RiskDimension("Dimension1", 0.65),
                new RiskDimension("Dimension2", 0.35)
        );

        List<RiskDimension> mockUpdatedDimensions = List.of(
                new RiskDimension("Dimension3", 30.00),
                new RiskDimension("Dimension2", 70.00)
        );

        // Instead of directly using List.of, use new ArrayList to create mutable lists
        List<RiskDimension> mutableMockExistingDimensions = new ArrayList<>(mockExistingDimensions);
        List<RiskDimension> mutableMockUpdatedDimensions = new ArrayList<>(mockUpdatedDimensions);

        when(riskDimensionRepository.findAll()).thenReturn(mutableMockExistingDimensions);
        when(riskDimensionRepository.saveAll(anyList())).thenAnswer(invocation -> {
            // The modified elements in the mockUpdatedDimensions list are the ones that should be saved.
            // So, we update the mockExistingDimensions with the mockUpdatedDimensions.
            mutableMockExistingDimensions.clear();
            mutableMockExistingDimensions.addAll(mutableMockUpdatedDimensions);
            return mutableMockUpdatedDimensions;
        });

        // Act
        List<RiskDimension> result = riskDimensionService.addOrUpdateDimensionWeight(mutableMockUpdatedDimensions);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.containsAll(mutableMockUpdatedDimensions));
        verify(riskDimensionRepository).findAll();
        verify(riskDimensionRepository).saveAll(anyList());
    }


    @Test
    public void testAddOrUpdateDimensionWeight_EmptyInputList() {
        // Test case for adding or updating dimension weight with empty input list
        List<RiskDimension> emptyList = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> riskDimensionService.addOrUpdateDimensionWeight(emptyList));
        verify(riskDimensionRepository, never()).findAll();
        verify(riskDimensionRepository, never()).deleteAll(any());
        verify(riskDimensionRepository, never()).saveAll(any());
    }


    @Test
    public void testAddOrUpdateDimensionWeight_DuplicateDimensionName() {
        // Test case for adding or updating dimension weight with duplicate dimension name
        List<RiskDimension> mockExistingDimensions = Arrays.asList(
                new RiskDimension("Dimension1", 0.65),
                new RiskDimension("Dimension2", 0.35)
        );

        List<RiskDimension> mockUpdatedDimensions = Arrays.asList(
                new RiskDimension("Dimension1", 0.30),
                new RiskDimension("Dimension2", 0.70)
        );

        when(riskDimensionRepository.findAll()).thenReturn(mockExistingDimensions);
        when(riskDimensionRepository.saveAll(any())).thenThrow(DataIntegrityViolationException.class);

        // Assert that EntityExistsException is thrown
        assertThrows(EntityExistsException.class, () -> riskDimensionService.addOrUpdateDimensionWeight(mockUpdatedDimensions));

        // Verify that the findAll method is called once
        verify(riskDimensionRepository, times(1)).findAll();

        // Verify that the saveAll method is called with the updated dimensions
        verify(riskDimensionRepository, times(1)).saveAll(eq(mockUpdatedDimensions));
    }



    @Test
    public void testAddOrUpdateDimensionWeight_InvalidSumOfWeights() {
        // Test case for adding or updating dimension weight with invalid sum of weights
        List<RiskDimension> mockUpdatedDimensions = Arrays.asList(
                new RiskDimension("Dimension1", 30.0),
                new RiskDimension("Dimension2", 50.0)
        );

        when(riskDimensionRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(IllegalArgumentException.class, () -> riskDimensionService.addOrUpdateDimensionWeight(mockUpdatedDimensions));
        verify(riskDimensionRepository, times(1)).findAll();
        verify(riskDimensionRepository, never()).deleteAll(any());
        verify(riskDimensionRepository, never()).saveAll(any());
    }

    @Test
    public void testGetAllDimensions() {
        // Test case for getting all dimension names
        List<RiskDimension> mockRiskDimensions = Arrays.asList(
                new RiskDimension("Dimension1", 0.25),
                new RiskDimension("Dimension2", 0.35)
        );

        when(riskDimensionRepository.findAll()).thenReturn(mockRiskDimensions);

        List<String> result = riskDimensionService.getAllDimensions();

        List<String> expectedDimensions = Arrays.asList("Dimension1", "Dimension2");
        assertEquals(expectedDimensions, result);
        verify(riskDimensionRepository, times(1)).findAll();
    }
}


