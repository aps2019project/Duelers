import controllers.Organizer;
import view.request.InputException;

public class Main {
	public static void main(String[] args) {
		Organizer organizer = new Organizer();
		organizer.preProcess();
		organizer.main();
	}
}