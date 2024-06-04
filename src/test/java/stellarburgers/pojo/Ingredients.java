package stellarburgers.pojo;

import java.util.List;

public class Ingredients {
    List<String> ingredients;

    public Ingredients() {
    }

    public Ingredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
