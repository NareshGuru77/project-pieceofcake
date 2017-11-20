package maas.agents;

import java.util.Collections;
import java.util.List;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;
import maas.objects.Order;
import maas.utils.OrderDateComparator;

@SuppressWarnings("serial")
public class CustomerAgent extends Agent {

	private String guiId;
	private String type;
	private int locationX;
	private int locationY;
	private List<Order> orders;
	
	public CustomerAgent(String guiId, String type, int locationX, int locationY, List<Order> orders) {
		this.guiId = guiId;
		this.type = type;
		this.locationX = locationX;
		this.locationY = locationY;
		
		Collections.sort(orders, new OrderDateComparator());
		this.orders = orders;
		
	}

	protected void setup() {

		// Printout a welcome message
		System.out.println("Created the customer " + getAID().getLocalName() + " of type " + this.type + " at location ("
				+ this.locationX + ", " + this.locationY + ")");


		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
		addBehaviour(new PlaceOrder(orders));
		// addBehaviour(new GetResponseService());
		// addBehaviour(new shutdown());

	}

	protected void takeDown() {
		System.out.println(getAID().getLocalName() + ": Terminating.");
	}

	// Taken from
	// http://www.rickyvanrijn.nl/2017/08/29/how-to-shutdown-jade-agent-platform-programmatically/
	private class shutdown extends OneShotBehaviour {

		public void action() {
			ACLMessage shutdownMessage = new ACLMessage(ACLMessage.REQUEST);
			Codec codec = new SLCodec();
			myAgent.getContentManager().registerLanguage(codec);
			myAgent.getContentManager().registerOntology(JADEManagementOntology.getInstance());
			shutdownMessage.addReceiver(myAgent.getAMS());
			shutdownMessage.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
			shutdownMessage.setOntology(JADEManagementOntology.getInstance().getName());
			try {
				myAgent.getContentManager().fillContent(shutdownMessage,
						new Action(myAgent.getAID(), new ShutdownPlatform()));
				myAgent.send(shutdownMessage);
			} catch (Exception e) {
				// LOGGER.error(e);
			}

		}
	}

	// Behavior for placing orders
	private class PlaceOrder extends Behaviour {

		// Indicates at what step of the process we are
		private int step = 0;
		private List<Order> orders;
		
		public PlaceOrder(List<Order> orders) {
			this.orders = orders;
		}

		public void action() {
			if (step == 0) {
				// Step 0, place the order
				ACLMessage msg = new ACLMessage(ACLMessage.CFP);
				msg.addReceiver(new AID("baker", AID.ISLOCALNAME));
				msg.setLanguage("English");
				msg.setOntology("Bakery-order-ontology");
				
				String content = orders.get(0).toJSONString();
				msg.setContent(content);
				send(msg);
				System.out.println("order successfully placed");
				// Go to the next step
				step = 1;

			} else if (step == 1) {
				// Step 1, wait for reply
				ACLMessage answer = myAgent.receive();
				if (answer != null) {
					System.out.println("Checking for msg");
					String answerContent = answer.getContent();
					System.out.println(answerContent);
					// Reply received, terminate behavior
					step = 2;
				}
			} else {
				block();
			}
		}

		public boolean done() {
			return step == 2;
		}
	}
}