package novi.nl.wildplukrecepten.services;

import novi.nl.wildplukrecepten.models.Instruction;
import novi.nl.wildplukrecepten.models.Recipe;
import novi.nl.wildplukrecepten.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {
    @Mock
    RecipeRepository recipeRepository;
    @Mock
    IngredientRepository ingredientRepository;
    @Mock
    InstructionRepository instructionRepository;
    @Mock
    UtensilRepository utensilRepository;
    @Mock
    FileUploadRepository uploadRepository;

    @InjectMocks
    RecipeService recipeService;

    @Captor
    ArgumentCaptor<Recipe> captor;

    @BeforeEach
    void setUp() {

//        Recipe recipe1 = new Recipe("Bramenjam", "Voor ca. 600 ml bramenjam", 4, "https://www.laurasbakery.nl/zelf-bramenjam-maken/",  "Dit is een heel makkelijk recept en bramen zijn overal te vinden. Ook juist op de ruige plekken.", "2 minuten", "10 minuten", ingredient1, instruction1, utensil1, "raspberry.png")
//        Instruction instruction1 = new Instruction(1L,"Doe 1", recipe1);
//        Instruction instruction2 = new Instruction(2L, "Doe 2", recipe1);

    }

    @AfterEach
    void tearDown() {
    }


    //testen....................................................................
    @Test
    void getAllRecipes() {
    //Arrange

    //Act
    //Assert
    }

    @Test
    void getRecipe() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void createRecipe() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void putRecipe() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void patchRecipe() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void deleteById() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void assignIngredientToRecipe() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void assignPhotoToRecipe() {
    }

    @Test
    void transferToRecipe() {
    }

    @Test
    void addUtensilToRecipe() {
    }

    @Test
    void addIngredientToRecipe() {
    }

    @Test
    void addInstructionToRecipe() {
    }
}