package my.vaadin.vaadin_demo;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

public class CustomerForm extends FormLayout {
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private TextField email = new TextField("Email");
	private NativeSelect<CustomerStatus> status = new NativeSelect<>("Status");
	private DateField dob = new DateField("DOB");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	
	private CustomerService service = CustomerService.getInstance();
	private Customer customer;
	private MyUI ui;
	private Binder<Customer> binder = new Binder<>(Customer.class);
	
	public CustomerForm(MyUI ui, Customer customer) {
		setUi(ui);
		if (customer == null)
			setCustomer(new Customer());
		
		setCustomer(customer);
		
		setSizeUndefined();
		HorizontalLayout buttonsLayout = new HorizontalLayout(save, delete);
		
		status.setItems(CustomerStatus.values());
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(KeyCode.ENTER);
		
		addComponents(firstName, lastName, email, status, dob, buttonsLayout);
		
		addEventListeners();
		binder.bindInstanceFields(this);
	}
	
	public CustomerForm(MyUI ui) {
		this(ui, new Customer());
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
		binder.setBean(customer);
		
		delete.setEnabled(customer.isPersisted());
		setVisible(true);
		firstName.selectAll();
	}
	public MyUI getUi() {
		return ui;
	}
	public void setUi(MyUI ui) {
		this.ui = ui;
	}
	
	private void addEventListeners() {
		save.addClickListener(e-> {
			service.save(customer);
			ui.refresh();
			setVisible(false);
		});
		delete.addClickListener(e-> {
			service.delete(customer);
			ui.refresh();
			setVisible(false);
		});
	}
}
