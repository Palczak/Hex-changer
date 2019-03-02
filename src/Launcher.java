public class Launcher {

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View();
        view.setVisible(true);
        Controller controller = new Controller(model, view);

    }
}
