package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;

/**
 * Adds a new ingredient to the bakery's ingredient catalogue.
 */
public class AddIngredientCommand extends Command {
    public static final String COMMAND_WORD = "addIngredient";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new ingredient to the bakery's catalogue. "
            + "Parameters: NAME COST\n"
            + "Example: " + COMMAND_WORD + " Flour 1.50";

    public static final String MESSAGE_ADD_INGREDIENT_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the catalogue.";

    private final String name;
    private final double cost;

    /**
     * Creates an AddIngredientCommand to add the specified {@code Ingredient}.
     */
    public AddIngredientCommand(String name, double cost) {
        requireAllNonNull(name);
        this.name = name;
        this.cost = cost;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Use the existing ingredient catalogue from the model
        IngredientCatalogue ingredientCatalogue = model.getIngredientCatalogue();
        int nextProductId = ingredientCatalogue.getNextProductId();

        // Create a new ingredient with the next available product ID
        Ingredient newIngredient = new Ingredient(nextProductId, name, cost);

        // Check if the ingredient already exists in the catalogue
        if (isDuplicateIngredient(ingredientCatalogue, newIngredient)) {
            throw new CommandException(MESSAGE_DUPLICATE_INGREDIENT);
        }

        // Add the new ingredient to the catalogue
        ingredientCatalogue.addIngredient(newIngredient);

        // Return a success message
        return new CommandResult(String.format(MESSAGE_ADD_INGREDIENT_SUCCESS, newIngredient));
    }

    /**
     * Checks if an ingredient with the same name already exists in the catalogue.
     */
    private boolean isDuplicateIngredient(IngredientCatalogue catalogue, Ingredient newIngredient) {
        return catalogue.getCatalogue().values().stream()
                .anyMatch(existingIngredient ->
                        existingIngredient.getName().equalsIgnoreCase(newIngredient.getName()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddIngredientCommand)) {
            return false;
        }

        AddIngredientCommand otherCommand = (AddIngredientCommand) other;
        return name.equals(otherCommand.name) && cost == otherCommand.cost;
    }
}