package my.vaadin.vaadin_demo;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SuppressWarnings("serial")
public class MyUI extends UI {
	private CustomerService service = CustomerService.getInstance();
	private Grid<Customer> grid = new Grid<>(Customer.class);
	private TextField filterText = new TextField();
	private Button btnClear = new Button();
	
	private CustomerForm form = new CustomerForm(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
//        layout.setSizeFull();
        
//        grid.setSizeFull();
        grid.setColumns("id", "firstName", "lastName", "email", "dob");
        grid.sort(grid.getColumn("id"), SortDirection.ASCENDING);
        
        
        
        btnClear.setIcon(VaadinIcons.CLOSE);
        btnClear.setDescription("Clear filter contents");
        
        CssLayout filtering = new CssLayout();
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filtering.addComponents(filterText, btnClear);
//        filtering.setSizeUndefined();
        
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);
        
        Button btnAddCustomer = new Button("Add new customer");
        btnAddCustomer.addClickListener(e-> {
        	grid.asSingleSelect().clear();
        	form.setCustomer(new Customer());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filtering, btnAddCustomer);
        layout.addComponents(toolbar, main);
        updateList();
        
        setContent(layout);
        setUpListeners();
    }

    public void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }
    
    private void setUpListeners() {
    	filterText.addValueChangeListener(e-> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);    
        btnClear.addClickListener(e-> filterText.clear());
        
        grid.asSingleSelect().addValueChangeListener(e-> {
        	if (e.getValue()==null)
        		form.setVisible(false);
        	else
        		form.setCustomer(e.getValue());
        });
    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
