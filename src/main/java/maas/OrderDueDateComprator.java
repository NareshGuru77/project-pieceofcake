package maas;

import java.util.Comparator;

public class OrderDueDateComprator implements Comparator<Order>{

	@Override
	public int compare(Order order1, Order order2) {
		int dueDate1 = order1.getDueDate();
		int dueDate2 = order2.getDueDate();
		
		return dueDate1 - dueDate2;
	}
	

}
