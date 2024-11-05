package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.CustomerOrder;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.product.Product;
import seedu.address.model.util.Remark;

/**
 * Command to add a new customer order to the bakery's order list.
 */
public class AddCustomerOrderCommand extends Command {
    public static final String COMMAND_WORD = "addCustomerOrder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new customer order to the bakery's order list. "
            + "Parameters: PHONE_NUMBER PRODUCT_ID\n"
            + "Example: " + COMMAND_WORD + " 87654321 1";

    public static final String MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS = "New customer order added: \n%1$s";

    private final Name name;
    private final Phone phone;
    private final ArrayList<Integer> idList;
    private final Remark remark;

    /**
     * Constructs an {@code AddCustomerOrderCommand} with the specified customer's name, phone number, and product IDs.
     *
     * @param name   the name of the customer.
     * @param phone  the phone number of the customer (must not be null).
     * @param idList a list of product IDs for the order (must not be null).
     */
    public AddCustomerOrderCommand(Name name, Phone phone, ArrayList<Integer> idList, Remark remark) {
        requireAllNonNull(phone);
        this.name = name;
        this.phone = phone;
        this.idList = idList;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        PastryCatalogue pastryCatalogue = model.getPastryCatalogue();

        List<Product> productList = idList.stream()
                                        .map(pastryCatalogue::getProductById)
                                        .filter(Objects::nonNull)
                                        .toList();

        List<Person> personList = model.getFilteredPersonList();
        Person person = null;

        for (Person p : personList) {
            if (p.getPhone().equals(phone)) {
                person = p;
            }
        }
        if (person == null) {
            person = Person.getGuest(name, phone);
            model.addPerson(person);
        }

        CustomerOrder customerOrder = new CustomerOrder(person, productList, OrderStatus.PENDING, remark);

        person.addOrder(customerOrder);

        model.addCustomerOrder(customerOrder);

        return new CommandResult(String.format(MESSAGE_ADD_CUSTOMER_ORDER_SUCCESS, customerOrder.viewOrder()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCustomerOrderCommand)) {
            return false;
        }

        AddCustomerOrderCommand otherCommand = (AddCustomerOrderCommand) other;
        return phone.equals(otherCommand.phone) && idList.equals(otherCommand.idList);
    }
}
