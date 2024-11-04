package seedu.address.model.util;

import java.util.*;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Information;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.Pastry;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Remark EMPTY_REMARK = new Remark("");

    private static Customer sampleCustomer() {
        Customer c = new Customer(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Information("I eat chocolate"),
                new Remark("I enjoy movies"), getTagSet("Customer"));
        return c;
    }
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Remark("I love cakes"), getTagSet("Customer")),
            sampleCustomer(),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Remark("I watch football"), getTagSet("Customer")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Remark("Pastries are cool"), getTagSet("Customer")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Remark("I do not like sweet stuff"), getTagSet("Customer")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Remark("I want to invest in your bakery business"), getTagSet("Supplier")),
            new Person(new Name("Joel Tio"), new Phone("98102923"), new Email("joel.tio@gmail.com"),
                    new Address("548 Bukit Timah Road"),
                    new Remark("I am lactose intolerant"), getTagSet("Customer", "Friend"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static CustomerOrderList getSampleCustomerOrderList() {
        return new CustomerOrderList();
    }

    public static SupplyOrderList getSampleSupplyOrderList() {
        return new SupplyOrderList();
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    // Static collections for ingredients and pastries to avoid multiple initializations
    private static final Map<Integer, Ingredient> defaultIngredients = new HashMap<>();
    private static final List<Pastry> defaultPastries = new ArrayList<>();

    static {
        // Initialize default ingredients
        Ingredient flour = new Ingredient(1, "Flour", 1.50);
        Ingredient sugar = new Ingredient(2, "Sugar", 0.80);
        Ingredient strawberry = new Ingredient(3, "Strawberry", 3.00);
        Ingredient chocolate = new Ingredient(4, "Chocolate", 2.50);
        Ingredient cheese = new Ingredient(5, "Cheese", 4.00);
        Ingredient cream = new Ingredient(6, "Cream", 2.00);

        // Add ingredients to map with unique IDs
        defaultIngredients.put(flour.getProductId(), flour);
        defaultIngredients.put(sugar.getProductId(), sugar);
        defaultIngredients.put(strawberry.getProductId(), strawberry);
        defaultIngredients.put(chocolate.getProductId(), chocolate);
        defaultIngredients.put(cheese.getProductId(), cheese);
        defaultIngredients.put(cream.getProductId(), cream);

        // Initialize default pastries using the ingredients
        defaultPastries.add(new Pastry(1, "Strawberry Waffle", 5.50, new ArrayList<>(List.of(strawberry, flour, sugar))));
        defaultPastries.add(new Pastry(2, "Chocolate Donut", 4.00, new ArrayList<>(List.of(chocolate, flour, sugar))));
        defaultPastries.add(new Pastry(3, "Cheesecake", 6.50, new ArrayList<>(List.of(cheese, cream, sugar))));
    }

    // Method to retrieve default ingredients
    public static Map<Integer, Ingredient> getDefaultIngredients() {
        return new HashMap<>(defaultIngredients);  // Defensive copy to prevent modification
    }

    // Method to retrieve default pastries
    public static List<Pastry> getDefaultPastries() {
        return new ArrayList<>(defaultPastries);  // Defensive copy to prevent modification
    }

}
